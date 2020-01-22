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
    fns = new FunctionList(functions);
};

function returns [Function function]
: declaration=functionDecl body=functionBody
{
    function = new Function(declaration, body);
};

functionDecl returns [FunctionDeclaration declaration]
: t=compoundType i=id OPEN_PAREN params=formalParameters CLOSE_PAREN
{
    declaration = new FunctionDeclaration(t, i, params);
};

formalParameters returns [FormalParameterList paramList]
: t=compoundType i=id params=moreFormals
{
    final FormalParameter firstParam = new FormalParameter(t, i);
    params.add(0, firstParam);
    paramList = new FormalParameterList(params);
}
| // epsilon.
{
    params = Collections.emptyList();
    paramList = new FormalParameterList(params);
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
;

formalParameter returns [FormalParameter parameter]
: t=compoundType i=id
{
    parameter = new FormalParameter(t, i);
};

functionBody returns [FunctionBody body]
: OPEN_BRACE decls=varDecls stmts=statements CLOSE_BRACE
{
    body = new FunctionBody(decls, stmts);
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
    declList = new VariableDeclarationList(declarations);
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
    stmts = new StatementList(statements);
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
    type = Type.INT;
}
| FLOAT
{
    type = Type.FLOAT;
}
| CHAR
{
    type = Type.CHAR;
}
| STRING
{
    type = Type.STRING;
}
| BOOLEAN
{
    type = Type.BOOLEAN;
}
| VOID
{
    type = Type.VOID;
};

varDecl returns [VariableDeclaration declaration]
: t=compoundType i=id SEMICOLON
{
    declaration = new VariableDeclaration(t, i);
};

statement returns [Statement statement]
: SEMICOLON // ignore.
| e=exprStatement
{
    statement = e;
}
| i=ifElseStatement
{
    statement = i;
}
| w=whileStatement
{
    statement = w;
}
| p=printStatement
{
    statement = p;
}
| pl=printLineStatement
{
    statement = pl;
}
| r=returnStatement
{
    statement = r;
}
| assign=assignmentStatement
{
    statement = assign;
}
| arrayAssign=arrayAssignment
{
    statement = arrayAssign;
};

exprStatement returns [ExpressionStatement statement]
: e=expr SEMICOLON
{
    statement = new ExpressionStatement(e);
};

ifElseStatement returns [IfStatement statement]
: IF OPEN_PAREN e=expr CLOSE_PAREN bIf=block (ELSE bElse=block)?
{
    if (bElse == null) {
        statement = new IfStatement(e, bIf);
    } else {
        statement = new IfElseStatement(e, bIf, bElse);
    }
};

whileStatement returns [WhileStatement statement]
: WHILE OPEN_PAREN e=expr CLOSE_PAREN b=block
{
    statement = new WhileStatement(e, b);
};

printStatement returns [PrintStatement statement]
: PRINT e=expr SEMICOLON
{
    statement = new PrintStatement(e);
};

printLineStatement returns [PrintLineStatement statement]
: PRINTLN e=expr SEMICOLON
{
    statement = new PrintLineStatement(e);
};

returnStatement returns [ReturnStatement statement]
: RETURN (e=expr)? SEMICOLON
{
    final Optional<Expression> optional =
            (e == null) ? Optional.empty(): Optional.of(e);
    statement = new ReturnStatement(optional);
};

assignmentStatement returns [AssignmentStatement statement]
: i=id EQUALS e=expr SEMICOLON
{
    statement = new AssignmentStatement(i, e);
};

arrayAssignment returns [ArrayAssignment assignment]
: i=id OPEN_BRACKET index=expr CLOSE_BRACKET EQUALS assign=expr SEMICOLON
{
    assignment = new ArrayAssignment(i, index, assign);
};

block returns [Block block]
: OPEN_BRACE s=statements CLOSE_BRACE
{
    block = new Block(s);
};

expr returns [Expression expression]
: e=equality
{
    expression = e;
};

equality returns [Expression expression]
:   current=lessThan
    (EQUAL_OP e2=lessThan
        {
            current = new EqualityExpression(current, e2);
        }
    )*
{
    expression = current;
};

lessThan returns [Expression expression]
:   current=sum
    (LESS_OP e2=sum
        {
            current = new LessThanExpression(current, e2);
        }
    )*
{
    expression = current;
};

sum returns [Expression expression]
:   current=sub
    (ADD_OP e2=sub
        {
            current = new AddExpression(current, e2);
        }
    )*
{
    expression = current;
};

sub returns [Expression expression]
:   current=mult
    (SUB_OP e2=mult
        {
            current = new SubtractExpression(current, e2);
        }
    )*
{
    expression = current;
};

mult returns [Expression expression]
:   current=atom
    (MULT_OP e2=atom
        {
            current = new MultiplyExpression(current, e2);
        }
    )*
{
    expression = current;
};

atom returns [Expression expression]
: l=literal
{
    expression = l;
}
| i=id
{
    expression = i;
}
| p=parenExpr
{
    expression = p;
}
| a=arrayReference
{
    expression = a;
}
| f=functionCall
{
    expression = f;
};

literal returns [Literal literal]
: s=stringLiteral
{
    literal = s;
}
| i=intLiteral
{
    literal = i;
}
| f=floatLiteral
{
    literal = f;
}
| c=charLiteral
{
    literal = c;
}
| b=booleanLiteral
{
    literal = b;
};

stringLiteral returns [StringLiteral literal]
: constant=STRING_CONSTANT
{
    final String value = constant.getText();
    literal = new StringLiteral(value);
};

intLiteral returns [IntegerLiteral literal]
: constant=INT_CONSTANT
{
    final int value = Integer.parseInt(constant.getText());
    literal = new IntegerLiteral(value);
};

floatLiteral returns [FloatLiteral literal]
: constant=FLOAT_CONSTANT
{
    final float value = Float.parseFloat(constant.getText());
    literal = new FloatLiteral(value);
};

charLiteral returns [CharacterLiteral literal]
: constant=CHAR_CONSTANT
{
    final String text = constant.getText();
    assert text.length() == 3; // Account for quotes.
    final char value = text.charAt(1);
    literal = new CharacterLiteral(value);
};

booleanLiteral returns [BooleanLiteral literal]
: constant=BOOLEAN_CONSTANT
{
    final boolean value = Boolean.parseBoolean(constant.getText());
    literal = new BooleanLiteral(value);
};

id returns [Identifier id]
: i=ID
{
    final String text = i.getText();
    id = new Identifier(text);
};

parenExpr returns [ParenExpression expression]
: OPEN_PAREN e=expr CLOSE_PAREN
{
    expression = new ParenExpression(e);
};

arrayReference returns [ArrayReference reference]
: i=id OPEN_BRACKET e=expr CLOSE_BRACKET
{
    reference = new ArrayReference(i, e);
};

functionCall returns [FunctionCall functionCall]
: i=id OPEN_PAREN e=exprList CLOSE_PAREN
{
    functionCall = new FunctionCall(i, e);
};

exprList returns [ExpressionList exprList]
: e=expr expressions=exprMore
{
    expressions.add(0, e);
    exprList = new ExpressionList(expressions);
}
| // epsilon.
{
    expressions = Collections.emptyList();
    exprList = new ExpressionList(expressions);
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
;


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
