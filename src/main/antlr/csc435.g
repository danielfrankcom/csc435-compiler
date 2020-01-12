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


/*
 * This is a subset of the ulGrammar to show you how
 * to make new production rules.
 * You will need to:
 *  - change type to be compoundType and include appropriate productions
 *  - introduce optional formalParameters
 *  - change functionBody to include variable declarations and statements 
 */

program : function+ ;

function: functionDecl functionBody ;

functionDecl: type identifier OPEN_PAREN CLOSE_PAREN ;

functionBody: OPEN_BRACE CLOSE_BRACE ;

identifier : ID ;

type    : (INT | FLOAT | CHAR | STRING | BOOLEAN | VOID) ;


/* Lexer */

OPEN_PAREN : '(' ;

CLOSE_PAREN : ')' ;

OPEN_BRACE : '{' ;

CLOSE_BRACE : '}' ;

IF  : 'if' ;

INT : 'int' ;

FLOAT : 'float' ;

CHAR : 'char' ;

STRING : 'string' ;

BOOLEAN : 'boolean' ;

VOID : 'void' ;

ID	: ('a'..'z' | 'A'..'Z' | '_')('a'..'z' | 'A'..'Z' | '0'..'9' | '_')* ;



// These two lines match whitespace and comments to ignore them.
// They should be last in the file.
WS  : ( '\t' | ' ' | ('\r' | '\n') )+ { $channel = HIDDEN;} ;
COMMENT : '//' ~('\r' | '\n')* ('\r' | '\n') { $channel = HIDDEN;} ;
