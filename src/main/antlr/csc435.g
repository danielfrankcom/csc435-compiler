grammar csc435;

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

functionDecl    : type ID OPEN_PAREN CLOSE_PAREN ;

functionBody    : OPEN_BRACE varDecl* statement* CLOSE_BRACE ;

// todo: update to be compound type
varDecl : type ID SEMICOLON ;

// todo: more definitions
statement   : SEMICOLON
            | expr SEMICOLON
            | if_statement
            | ID EQUALS expr
            ;

// This statement must be broken apart with epsilon due to LL(1) limitations.
if_statement    : IF OPEN_PAREN expr CLOSE_PAREN block else_statement ;
else_statement  : ELSE block
                | // epsilon.
                ;

block : OPEN_BRACE statement* CLOSE_BRACE ;

// todo: more definitions
expr    : ID
        | literal
        ;

literal : STRING_CONSTANT
        | INT_CONSTANT
        | FLOAT_CONSTANT
        | CHAR_CONSTANT
        | BOOLEAN_CONSTANT
        ;

type    : INT
        | FLOAT
        | CHAR
        | STRING
        | BOOLEAN
        | VOID
        ;


/* Lexer */

OPEN_PAREN : '(' ;

CLOSE_PAREN : ')' ;

OPEN_BRACE : '{' ;

CLOSE_BRACE : '}' ;

COMMA : ',' ;

SEMICOLON : ';' ;

EQUALS : '=';

IF : 'if' ;

ELSE : 'else' ;

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
