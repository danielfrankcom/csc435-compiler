grammar csc435;

// todo: might want to disambiguate instead.
options {
    backtrack = true;
}

@parser::header {
    package om.frankc.csc435.compiler.generated;
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

@lexer::members
{
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


program : function+ ;

function    : functionDecl functionBody ;

functionDecl    : type ID OPEN_PAREN formalParameters CLOSE_PAREN ;

formalParameters    : compoundType ID moreFormals*
                    | // epsilon.
                    ;

moreFormals : COMMA compoundType ID ;

functionBody    : OPEN_BRACE varDecl* statement* CLOSE_BRACE ;

varDecl : compoundType ID SEMICOLON ;

// todo: does this mean we can't use an expression in the brackets?
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

// todo: is an empty string valid?
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
