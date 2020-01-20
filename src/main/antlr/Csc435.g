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
    return new Program(f);
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
    params = Collections.emptyList();
    return new FormalParameterList(params);
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
    return t;
}
| t=type OPEN_BRACKET constant=intLiteral CLOSE_BRACKET
{
    return new ArrayType(t, constant);
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
    return e;
}
| i=ifElseStatement
{
    return i;
}
| w=whileStatement
{
    return w;
}
| p=printStatement
{
    return p;
}
| pl=printLineStatement
{
    return pl;
}
| r=returnStatement
{
    return r;
}
| assign=assignmentStatement
{
    return assign;
}
| arrayAssign=arrayAssignment
{
    return arrayAssign;
};

exprStatement returns [ExpressionStatement statement]
: e=expr SEMICOLON
{
    return new ExpressionStatement(e);
};

ifElseStatement returns [IfStatement statement]
: IF OPEN_PAREN e=expr CLOSE_PAREN bIf=block (ELSE bElse=block)?
{
    if (bElse == null) {
        return new IfStatement(e, bIf);
    } else {
        return new IfElseStatement(e, bIf, bElse);
    }
};

whileStatement returns [WhileStatement statement]
: WHILE OPEN_PAREN e=expr CLOSE_PAREN b=block
{
    return new WhileStatement(e, b);
};

printStatement returns [PrintStatement statement]
: PRINT e=expr SEMICOLON
{
    return new PrintStatement(e);
};

printLineStatement returns [PrintLineStatement statement]
: PRINTLN e=expr SEMICOLON
{
    return new PrintLineStatement(e);
};

returnStatement returns [ReturnStatement statement]
: RETURN (e=expr)? SEMICOLON
{
    final Optional<Expression> optional =
            (e == null) ? Optional.empty(): Optional.of(e);
    return new ReturnStatement(optional);
};

assignmentStatement returns [AssignmentStatement statement]
: i=id EQUALS e=expr SEMICOLON
{
    return new AssignmentStatement(i, e);
};

arrayAssignment returns [ArrayAssignment assignment]
: i=id OPEN_BRACKET index=expr CLOSE_BRACKET EQUALS assign=expr SEMICOLON
{
    return new ArrayAssignment(i, index, assign);
};

block returns [Block block]
: OPEN_BRACE s=statements CLOSE_BRACE
{
    return new Block(s);
};

expr returns [Expression expression]
: e=equality
{
    return e;
};

equality returns [Expression expression]
: e1=lessThan (EQUAL_OP e2=lessThan)*
{
    if (e2 == null) {
        return e1;
    } else {
        return new EqualityExpression(e1, e2);
    }
};

lessThan returns [Expression expression]
: e1=sum (LESS_OP e2=sum)*
{
    if (e2 == null) {
        return e1;
    } else {
        return new LessThanExpression(e1, e2);
    }
};

sum returns [Expression expression]
: e1=sub (ADD_OP e2=sub)*
{
    if (e2 == null) {
        return e1;
    } else {
        return new AddExpression(e1, e2);
    }
};

sub returns [Expression expression]
: e1=mult (SUB_OP e2=sub)*
{
    if (e2 == null) {
        return e1;
    } else {
        return new SubtractExpression(e1, e2);
    }
};

mult returns [Expression expression]
: e1=atom (MULT_OP e2=atom)*
{
    if (e2 == null) {
        return e1;
    } else {
        return new MultiplyExpression(e1, e2);
    }
};

atom returns [Expression expression]
: l=literal
{
    return l;
}
| i=id
{
    return i;
}
| p=parenExpr
{
    return p;
}
| a=arrayReference
{
    return a;
}
| f=functionCall
{
    return f;
};

literal returns [Literal literal]
: s=stringLiteral
{
    return s;
}
| i=intLiteral
{
    return i;
}
| f=floatLiteral
{
    return f;
}
| c=charLiteral
{
    return c;
}
| b=booleanLiteral
{
    return b;
};

stringLiteral returns [StringLiteral literal]
: constant=STRING_CONSTANT
{
    final String value = constant.getText();
    return new StringLiteral(value);
};

intLiteral returns [IntegerLiteral literal]
: constant=INT_CONSTANT
{
    final int value = Integer.parseInt(constant.getText());
    return new IntegerLiteral(value);
};

floatLiteral returns [FloatLiteral literal]
: constant=FLOAT_CONSTANT
{
    final float value = Float.parseFloat(constant.getText());
    return new FloatLiteral(value);
};

charLiteral returns [CharacterLiteral literal]
: constant=CHAR_CONSTANT
{
    final String text = constant.getText();
    assert text.length() == 3; // Account for quotes.
    final char value = text.charAt(1);
    return new CharacterLiteral(value);
};

booleanLiteral returns [BooleanLiteral literal]
: constant=BOOLEAN_CONSTANT
{
    final boolean value = Boolean.parseBoolean(constant.getText());
    return new BooleanLiteral(value);
};

id returns [Identifier id]
: i=ID
{
    final String text = i.getText();
    return new Identifier(text);
};

parenExpr returns [ParenExpression expression]
: OPEN_PAREN e=expr CLOSE_PAREN
{
    return new ParenExpression(e);
};

arrayReference returns [ArrayReference reference]
: i=id OPEN_BRACKET e=expr CLOSE_BRACKET
{
    return new ArrayReference(i, e);
};

functionCall returns [FunctionCall functionCall]
: i=id OPEN_PAREN e=exprList CLOSE_PAREN
{
    return new FunctionCall(i, e);
};

exprList returns [ExpressionList exprList]
: e=expr expressions=exprMore
{
    expressions.add(0, e);
    return new ExpressionList(expressions);
}
| // epsilon.
{
    expressions = Collections.emptyList();
    return new ExpressionList(expressions);
};

exprMore returns [List<Expression> expressions]
    @init {
        expressions = new LinkedList<>();
    }
:   (COMMA expression=expr
        {
            expressions.add(expression);
        }
    )*
{
    return expressions;
};


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
