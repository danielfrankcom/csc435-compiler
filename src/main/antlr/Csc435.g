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
    program = new Program(f, f.getLineNumber(), f.getLinePosition());
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
    assert functions.size() > 0;
    final Function first = functions.get(0);
    fns = new FunctionList(functions, first.getLineNumber(), first.getLinePosition());
};

function returns [Function function]
: declaration=functionDecl body=functionBody
{
    function = new Function(declaration, body, declaration.getLineNumber(), declaration.getLinePosition());
};

functionDecl returns [FunctionDeclaration declaration]
: t=compoundType i=id pre=OPEN_PAREN params=formalParameters[pre] CLOSE_PAREN
{
    declaration = new FunctionDeclaration(t, i, params, t.getLineNumber(), t.getLinePosition());
};

formalParameters [Token precursor] returns [FormalParameterList paramList]
: t=compoundType i=id params=moreFormals
{
    final FormalParameter firstParam = new FormalParameter(t, i, t.getLineNumber(), t.getLinePosition());
    params.add(0, firstParam);
    paramList = new FormalParameterList(params, precursor.getLine(), precursor.getCharPositionInLine());
}
| // epsilon.
{
    params = Collections.emptyList();
    paramList = new FormalParameterList(params, precursor.getLine(), precursor.getCharPositionInLine());
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
    parameter = new FormalParameter(t, i, t.getLineNumber(), t.getLinePosition());
};

functionBody returns [FunctionBody body]
: pre=OPEN_BRACE decls=varDecls[pre] stmts=statements[pre] CLOSE_BRACE
{
    body = new FunctionBody(decls, stmts, $pre.line, $pre.pos);
};

varDecls [Token precursor] returns [VariableDeclarationList declList]
    @init {
        final List<VariableDeclaration> declarations = new LinkedList<>();
    }
:   (declaration=varDecl
        {
            declarations.add(declaration);
        }
    )*
{
    declList = new VariableDeclarationList(declarations, precursor.getLine(), precursor.getCharPositionInLine());
};

statements [Token precursor] returns [StatementList stmts]
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
    stmts = new StatementList(statements, precursor.getLine(), precursor.getCharPositionInLine());
};

compoundType returns [TypeNode type]
: t=type
{
    type = t;
}
| t=type OPEN_BRACKET constant=intLiteral CLOSE_BRACKET
{
    type = new ArrayType(t, constant, t.getLineNumber(), t.getLinePosition());
};

type returns [Type type]
: i=INT
{
    type = new Type(Type.Name.Integer, $i.line, $i.pos);
}
| f=FLOAT
{
    type = new Type(Type.Name.FloatingPoint, $f.line, $f.pos);
}
| c=CHAR
{
    type = new Type(Type.Name.Character, $c.line, $c.pos);
}
| s=STRING
{
    type = new Type(Type.Name.String, $s.line, $s.pos);
}
| b=BOOLEAN
{
    type = new Type(Type.Name.Boolean, $b.line, $b.pos);
}
| v=VOID
{
    type = new Type(Type.Name.Void, $v.line, $v.pos);
};

varDecl returns [VariableDeclaration declaration]
: t=compoundType i=id SEMICOLON
{
    declaration = new VariableDeclaration(t, i, t.getLineNumber(), t.getLinePosition());
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
    statement = new ExpressionStatement(e, e.getLineNumber(), e.getLinePosition());
};

ifElseStatement returns [IfStatement statement]
: start=IF OPEN_PAREN e=expr CLOSE_PAREN bIf=block (ELSE bElse=block)?
{
    if (bElse == null) {
        statement = new IfStatement(e, bIf, $start.line, $start.pos);
    } else {
        statement = new IfElseStatement(e, bIf, bElse, $start.line, $start.pos);
    }
};

whileStatement returns [WhileStatement statement]
: start=WHILE OPEN_PAREN e=expr CLOSE_PAREN b=block
{
    statement = new WhileStatement(e, b, $start.line, $start.pos);
};

printStatement returns [PrintStatement statement]
: start=PRINT e=expr SEMICOLON
{
    statement = new PrintStatement(e, $start.line, $start.pos);
};

printLineStatement returns [PrintLineStatement statement]
: start=PRINTLN e=expr SEMICOLON
{
    statement = new PrintLineStatement(e, $start.line, $start.pos);
};

returnStatement returns [ReturnStatement statement]
: start=RETURN (e=expr)? SEMICOLON
{
    final Optional<Expression> optional =
            (e == null) ? Optional.empty(): Optional.of(e);
    statement = new ReturnStatement(optional, $start.line, $start.pos);
};

assignmentStatement returns [AssignmentStatement statement]
: i=id EQUALS e=expr SEMICOLON
{
    statement = new AssignmentStatement(i, e, i.getLineNumber(), i.getLinePosition());
};

arrayAssignment returns [ArrayAssignment assignment]
: i=id OPEN_BRACKET index=expr CLOSE_BRACKET EQUALS assign=expr SEMICOLON
{
    assignment = new ArrayAssignment(i, index, assign, i.getLineNumber(), i.getLinePosition());
};

block returns [Block block]
: start=OPEN_BRACE s=statements[start] CLOSE_BRACE
{
    block = new Block(s, $start.line, $start.pos);
};

expr returns [Expression expression]
: e=equality
{
    expression = e;
};

equality returns [Expression expression]
:   current=lessThan
    (op=EQUAL_OP e2=lessThan
        {
            current = new EqualityExpression(current, e2, $op.line, $op.pos);
        }
    )*
{
    expression = current;
};

lessThan returns [Expression expression]
:   current=sum
    (op=LESS_OP e2=sum
        {
            current = new LessThanExpression(current, e2, $op.line, $op.pos);
        }
    )*
{
    expression = current;
};

sum returns [Expression expression]
:   current=sub
    (op=ADD_OP e2=sub
        {
            current = new AddExpression(current, e2, $op.line, $op.pos);
        }
    )*
{
    expression = current;
};

sub returns [Expression expression]
:   current=mult
    (op=SUB_OP e2=mult
        {
            current = new SubtractExpression(current, e2, $op.line, $op.pos);
        }
    )*
{
    expression = current;
};

mult returns [Expression expression]
:   current=atom
    (op=MULT_OP e2=atom
        {
            current = new MultiplyExpression(current, e2, $op.line, $op.pos);
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
    literal = new StringLiteral(value, $constant.line, $constant.pos);
};

intLiteral returns [IntegerLiteral literal]
: constant=INT_CONSTANT
{
    final int value = Integer.parseInt(constant.getText());
    literal = new IntegerLiteral(value, $constant.line, $constant.pos);
};

floatLiteral returns [FloatLiteral literal]
: constant=FLOAT_CONSTANT
{
    final float value = Float.parseFloat(constant.getText());
    literal = new FloatLiteral(value, $constant.line, $constant.pos);
};

charLiteral returns [CharacterLiteral literal]
: constant=CHAR_CONSTANT
{
    final String text = constant.getText();
    assert text.length() == 3; // Account for quotes.
    final char value = text.charAt(1);
    literal = new CharacterLiteral(value, $constant.line, $constant.pos);
};

booleanLiteral returns [BooleanLiteral literal]
: constant=BOOLEAN_CONSTANT
{
    final boolean value = Boolean.parseBoolean(constant.getText());
    literal = new BooleanLiteral(value, $constant.line, $constant.pos);
};

id returns [Identifier id]
: i=ID
{
    final String text = i.getText();
    id = new Identifier(text, $i.line, $i.pos);
};

parenExpr returns [ParenExpression expression]
: start=OPEN_PAREN e=expr CLOSE_PAREN
{
    expression = new ParenExpression(e, $start.line, $start.pos);
};

arrayReference returns [ArrayReference reference]
: i=id OPEN_BRACKET e=expr CLOSE_BRACKET
{
    reference = new ArrayReference(i, e, i.getLineNumber(), i.getLinePosition());
};

functionCall returns [FunctionCall functionCall]
: i=id pre=OPEN_PAREN e=exprList[pre] CLOSE_PAREN
{
    functionCall = new FunctionCall(i, e, i.getLineNumber(), i.getLinePosition());
};

exprList [Token precursor] returns [ExpressionList exprList]
: e=expr expressions=exprMore
{
    expressions.add(0, e);
    exprList = new ExpressionList(expressions, precursor.getLine(), precursor.getCharPositionInLine());
}
| // epsilon.
{
    expressions = Collections.emptyList();
    exprList = new ExpressionList(expressions, precursor.getLine(), precursor.getCharPositionInLine());
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
