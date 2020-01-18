grammar Csc435;

options {
    backtrack = true;
    language = Java;
}

@parser::header {
    package om.frankc.csc435.compiler.generated;
    
    import om.frankc.csc435.compiler.ast.*;

    import java.util.HashSet;
    import java.util.Set;
}

@lexer::header {
    package om.frankc.csc435.compiler.generated;
}
				
@parser::members {

    protected void mismatch (IntStream input, int ttype, BitSet follow)
            throws RecognitionException {
        throw new MismatchedTokenException(ttype, input);
    }

    public Object recoverFromMismatchedSet (IntStream input, RecognitionException e, BitSet follow)
            throws RecognitionException {
        reportError(e);
        throw e;
    }

    @Override
	public void reportError(RecognitionException e) {
		displayRecognitionError(this.getTokenNames(), e);
		throw new RuntimeException(e);
	}

}

@lexer::members {
    @Override
    public void recover(RecognitionException ex) {
        throw new RuntimeException(ex);
    }
}

@rulecatch {
    catch (RecognitionException ex) {
        reportError(ex);
        throw ex;
    }
}


program returns [Program program]
    @init {
        final Set<Function> functions = new HashSet<>();
    }
:   (f = function
        {
            final boolean success = functions.add(f);

            // Failure indicates a duplicate function.
            assert success;
        }
    )+
    EOF
{
    program = new Program(functions);
};

function returns [Function function]
: functionDecl functionBody
{
    return new Function();
};

functionDecl    : type ID OPEN_PAREN formalParameters CLOSE_PAREN ;

formalParameters    : compoundType ID moreFormals*
                    | // epsilon.
                    ;

moreFormals : COMMA compoundType ID ;

functionBody    : OPEN_BRACE varDecl* statement* CLOSE_BRACE ;

varDecl : compoundType ID SEMICOLON ;

compoundType    : type
                | type OPEN_BRACKET INT_CONSTANT CLOSE_BRACKET
                ;

type    : INT
        | FLOAT
        | CHAR
        | STRING
        | BOOLEAN
        | VOID
        ;

statement   : SEMICOLON
            | expr SEMICOLON
            | IF OPEN_PAREN expr CLOSE_PAREN block (ELSE block)?
            | WHILE OPEN_PAREN expr CLOSE_PAREN block
            | PRINT expr SEMICOLON
            | PRINTLN expr SEMICOLON
            | RETURN expr? SEMICOLON
            | ID EQUALS expr SEMICOLON
            | ID OPEN_BRACKET expr CLOSE_BRACKET EQUALS expr SEMICOLON
            ;

block : OPEN_BRACE statement* CLOSE_BRACE ;

expr : equality ;

equality : lessThan (EQUAL_OP lessThan)* ;

lessThan : sum (LESS_OP sum)* ;

sum : sub (ADD_OP sub)* ;

sub : mult (SUB_OP sub)* ;

mult : atom (MULT_OP atom)* ;

atom    : literal
        | ID
        | OPEN_PAREN expr CLOSE_PAREN
        | ID OPEN_BRACKET expr CLOSE_BRACKET
        | ID OPEN_PAREN exprList CLOSE_PAREN
        ;

literal : STRING_CONSTANT
        | INT_CONSTANT
        | FLOAT_CONSTANT
        | CHAR_CONSTANT
        | BOOLEAN_CONSTANT
        ;

exprList    : expr exprMore*
            | // epsilon.
            ;

exprMore : COMMA expr ;


/* Lexer */

OPEN_PAREN : '(' ;

CLOSE_PAREN : ')' ;

OPEN_BRACE : '{' ;

CLOSE_BRACE : '}' ;

OPEN_BRACKET : '[' ;

CLOSE_BRACKET : ']' ;

COMMA : ',' ;

SEMICOLON : ';' ;

EQUALS : '=' ;

MULT_OP : '*' ;

ADD_OP : '+' ;

SUB_OP : '-' ;

LESS_OP : '<' ;

EQUAL_OP : '==' ;

IF : 'if' ;

ELSE : 'else' ;

WHILE : 'while' ;

PRINT : 'print' ;

PRINTLN : 'println' ;

RETURN : 'return' ;

INT : 'int' ;

INT_CONSTANT : ('0'..'9')+ ;

FLOAT : 'float' ;

FLOAT_CONSTANT : INT_CONSTANT '.' INT_CONSTANT ;

CHAR : 'char' ;

CHAR_CONSTANT   :
                    '\''
                    (   'a'..'z'
                        | 'A'..'Z'
                        | '0'..'9'
                        | '!'
                        | ','
                        | '.'
                        | ':'
                        | '_'
                        | '{'
                        | '}'
                        | ' '
                    )
                    '\''
                ;

STRING : 'string' ;

STRING_CONSTANT :
                    '"'
                    (   'a'..'z'
                        | 'A'..'Z'
                        | '0'..'9'
                        | '!'
                        | ','
                        | '.'
                        | ':'
                        | '_'
                        | '{'
                        | '}'
                        | ' '
                    )*
                    '"'
                ;

BOOLEAN : 'boolean' ;

BOOLEAN_CONSTANT : ('true' | 'false') ;

VOID : 'void' ;

ID	:
        (
            'a'..'z'
            | 'A'..'Z'
            | '_'
        )
        (
            'a'..'z'
            | 'A'..'Z'
            | '0'..'9'
            | '_'
        )*
    ;



// These two lines match whitespace and comments to ignore them.
// They should be last in the file.
WS : ( '\t' | ' ' | ('\r' | '\n') )+ { $channel = HIDDEN;} ;
COMMENT : '//' ~('\r' | '\n')* ('\r' | '\n') { $channel = HIDDEN;} ;
