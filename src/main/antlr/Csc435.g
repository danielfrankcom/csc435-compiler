grammar Csc435;

options {
    backtrack = true;
    language = Java;
}

@parser::header {
    package om.frankc.csc435.compiler.generated;

    import om.frankc.csc435.compiler.ast.*;

	import java.util.*;

    // This must be manually imported as it conflicts
    // with the java.util.BitSet class.
	import org.antlr.runtime.BitSet;
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
: f=functions EOF
{
    program = new Program(f);
};

functions returns [FunctionList fns]
    @init {
        final List<Function> functions = new LinkedList<>();
    }
:   (f=function
        {
            functions.add(f);
        }
    )+
{
    return new FunctionList(functions);
};

function returns [Function function]
: declaration=functionDecl body=functionBody
{
    return new Function(declaration, body);
};

functionDecl returns [FunctionDeclaration declaration]
: t=compoundType i=id OPEN_PAREN params=formalParameters CLOSE_PAREN
{
    return new FunctionDeclaration(t, i, params);
};

formalParameters returns [FormalParameterList paramList]
: t=compoundType i=id params=moreFormals
{
    final FormalParameter firstParam = new FormalParameter(t, i);
    params.add(0, firstParam);
    return new FormalParameterList(params);
}
| // epsilon.
{
    final List<FormalParameter> parameters = Collections.emptyList();
    return new FormalParameterList(parameters);
};

moreFormals returns [List<FormalParameter> parameters]
    @init {
        parameters = new LinkedList<>();
    }
:   (COMMA param=formalParameter
        {
            parameters.add(param);
        }
    )*
{
    return parameters;
};

formalParameter returns [FormalParameter parameter]
: t=compoundType i=id
{
    return new FormalParameter(t, i);
};

functionBody returns [FunctionBody body]
: OPEN_BRACE decls=varDecls stmts=statements CLOSE_BRACE
{
    return new FunctionBody(decls, stmts);
};

varDecls returns [VariableDeclarationList declList]
    @init {
        final List<VariableDeclaration> declarations = new LinkedList<>();
    }
:   (declaration=varDecl
        {
            declarations.add(declaration);
        }
    )*
{
    return new VariableDeclarationList(declarations);
};

// todo: check for nulls
statements returns [StatementList stmts]
    @init {
        final List<Statement> statements = new LinkedList<>();
    }
:   (s=statement
        {
            // Some statements such as a lone semicolon
            // are not worthy of being added to the tree.
            if (s != null) {
                statements.add(s);
            }
        }
    )*
{
    return new StatementList(statements);
};

compoundType returns [TypeNode type]
: t=type
{
    type = t;
}
| t=type OPEN_BRACKET constant=intLiteral CLOSE_BRACKET
{
    type = new ArrayType(t, constant);
};

type returns [Type type]
: INT
{
    return Type.INT;
}
| FLOAT
{
    return Type.FLOAT;
}
| CHAR
{
    return Type.CHAR;
}
| STRING
{
    return Type.STRING;
}
| BOOLEAN
{
    return Type.BOOLEAN;
}
| VOID
{
    return Type.VOID;
};

varDecl returns [VariableDeclaration declaration]
: t=compoundType i=id SEMICOLON
{
    return new VariableDeclaration(t, i);
};

statement returns [Statement statement]
: SEMICOLON // ignore.
| e=exprStatement
{
    statement = e;
}
| IF OPEN_PAREN expr CLOSE_PAREN block (ELSE block)?
| w=whileStatement
{
    statement = w;
}
| PRINT expr SEMICOLON
| PRINTLN expr SEMICOLON
| RETURN expr? SEMICOLON
| ID EQUALS expr SEMICOLON
| ID OPEN_BRACKET expr CLOSE_BRACKET EQUALS expr SEMICOLON
;

exprStatement returns [ExpressionStatement statement]
: e=expr SEMICOLON
{
    return new ExpressionStatement(e);
};

// todo: if statement

whileStatement returns [WhileStatement statement]
: WHILE OPEN_PAREN e=expr CLOSE_PAREN b=block
{
    return new WhileStatement(e, b);
};

// todo
block returns [Block block]
: OPEN_BRACE s=statements CLOSE_BRACE
{
    return new Block(s);
};

// todo
expr returns [Expression expression]
: equality ;

equality : lessThan (EQUAL_OP lessThan)* ;

lessThan : sum (LESS_OP sum)* ;

sum : sub (ADD_OP sub)* ;

sub : mult (SUB_OP sub)* ;

mult : atom (MULT_OP atom)* ;

atom    : literal
        | id
        | OPEN_PAREN expr CLOSE_PAREN
        | ID OPEN_BRACKET expr CLOSE_BRACKET
        | ID OPEN_PAREN exprList CLOSE_PAREN
        ;

literal : STRING_CONSTANT
        | intLiteral
        | FLOAT_CONSTANT
        | CHAR_CONSTANT
        | BOOLEAN_CONSTANT
        ;

intLiteral returns [IntegerLiteral literal]
: constant=INT_CONSTANT
{
    final int value = Integer.parseInt(constant.getText());
    literal = new IntegerLiteral(value);
};

id returns [Identifier id]
: i=ID
{
    final String text = i.getText();
    id = new Identifier(text);
};

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
