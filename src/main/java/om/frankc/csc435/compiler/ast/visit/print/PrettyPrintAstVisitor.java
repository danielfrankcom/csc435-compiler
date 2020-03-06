package om.frankc.csc435.compiler.ast.visit.print;

import om.frankc.csc435.compiler.ast.*;
import om.frankc.csc435.compiler.ast.visit.IAstVisitor;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;

public class PrettyPrintAstVisitor implements IAstVisitor<Void> {

    private static final int INDENTATION_SPACES = 4;

    /**
     * Repeat the provided {@link String} the provided number of times.
     *
     * @param string The {@link String} to repeat.
     * @param times  The number of times to repeat it.
     * @return The generated {@link String}.
     */
    private static String repeat(String string, int times) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; i++) {
            builder.append(string);
        }
        return builder.toString();
    }

    public PrettyPrintAstVisitor(Consumer<String> output) {
        assert output != null;
        mOutput = output;
    }

    private final Consumer<String> mOutput;

    private int mIndentationLevel = 0;
    private StringBuilder mCurrentLine = new StringBuilder();
    private boolean mIsFirstLine = true;

    private void print(String string) {
        mCurrentLine.append(string);
    }

    private void print(int integer) {
        print(Integer.toString(integer));
    }

    private void print(float floatingPoint) {
        print(Float.toString(floatingPoint));
    }

    private void print(char character) {
        print(Character.toString(character));
    }

    private void print(boolean bool) {
        print(Boolean.toString(bool));
    }

    private void println(String string) {
        mCurrentLine.append(string);
        println();
    }

    private void println(char character) {
        println(Character.toString(character));
    }

    private void println() {
        String currentLine = mCurrentLine.toString();

        // We don't want an indent if the line
        // has no content.
        if (currentLine.length() > 0) {
            final int spaces = mIndentationLevel * INDENTATION_SPACES;
            final String indent = repeat(" ", spaces);
            currentLine = indent + currentLine;
        }

        // We don't want to prepend a new line
        // if we haven't printed anything yet.
        if (mIsFirstLine) {
            mIsFirstLine = false;
        } else {
            currentLine = '\n' + currentLine;
        }

        mOutput.accept(currentLine);

        mCurrentLine = new StringBuilder();
    }

    @Override
    public Void visit(Program program) {
        program.getFunctions().accept(this);

        return null;
    }

    @Override
    public Void visit(FunctionList functions) {
        for (Function function : functions.getElements()) {
            function.accept(this);
            println();
        }

        return null;
    }

    @Override
    public Void visit(Function function) {
        function.getDeclaration().accept(this);
        println();
        function.getBody().accept(this);

        return null;
    }

    @Override
    public Void visit(FunctionDeclaration declaration) {
        declaration.getTypeNode().accept(this);
        print(' ');
        declaration.getId().accept(this);
        print(" (");
        declaration.getParamList().accept(this);
        print(')');

        return null;
    }

    @Override
    public Void visit(ArrayType arrayType) {
        arrayType.getType().accept(this);
        print('[');
        arrayType.getSize().accept(this);
        print(']');

        return null;
    }

    @Override
    public Void visit(Type type) {
        print(type.toString());

        return null;
    }

    @Override
    public Void visit(Identifier id) {
        print(id.getText());

        return null;
    }

    @Override
    public Void visit(IntegerLiteral literal) {
        print(literal.getValue());

        return null;
    }

    @Override
    public Void visit(StringLiteral literal) {
        print("\"");
        print(literal.getValue());
        print("\"");

        return null;
    }

    @Override
    public Void visit(FloatLiteral literal) {
        print(literal.getValue());

        return null;
    }

    @Override
    public Void visit(CharacterLiteral literal) {
        print('\'');
        print(literal.getValue());
        print('\'');

        return null;
    }

    @Override
    public Void visit(BooleanLiteral literal) {
        print(literal.getValue());

        return null;
    }

    @Override
    public Void visit(FormalParameterList paramList) {
        final Iterator<FormalParameter> params = paramList.getElements().iterator();
        while (params.hasNext()) {
            final FormalParameter current = params.next();
            current.accept(this);

            if (params.hasNext()) {
                print(", ");
            }
        }

        return null;
    }

    @Override
    public Void visit(FormalParameter parameter) {
        parameter.getTypeNode().accept(this);
        print(' ');
        parameter.getId().accept(this);

        return null;
    }

    @Override
    public Void visit(FunctionBody body) {
        println('{');
        mIndentationLevel++;

        final VariableDeclarationList declarations = body.getDeclarations();
        final StatementList statements = body.getStatements();

        declarations.accept(this);
        if (declarations.getElements().size() > 0 && statements.getElements().size() > 0) {
            println();
        }

        body.getStatements().accept(this);
        mIndentationLevel--;
        println('}');

        return null;
    }

    @Override
    public Void visit(VariableDeclarationList declarations) {
        for (VariableDeclaration current : declarations.getElements()) {
            current.accept(this);
            println();
        }

        return null;
    }

    @Override
    public Void visit(VariableDeclaration declaration) {
        declaration.getTypeNode().accept(this);
        print(' ');
        declaration.getId().accept(this);
        print(';');

        return null;
    }

    @Override
    public Void visit(StatementList statements) {
        for (Statement current : statements.getElements()) {
            current.accept(this);
            println();
        }

        return null;
    }

    @Override
    public Void visit(ExpressionStatement statement) {
        statement.getExpression().accept(this);
        print(';');

        return null;
    }

    @Override
    public Void visit(IfStatement statement) {
        print("if (");
        statement.getExpression().accept(this);
        println(')');
        statement.getIfBlock().accept(this);

        return null;
    }

    @Override
    public Void visit(IfElseStatement statement) {
        print("if (");
        statement.getExpression().accept(this);
        println(')');
        statement.getIfBlock().accept(this);
        println();
        println("else");
        statement.getElseBlock().accept(this);

        return null;
    }

    @Override
    public Void visit(WhileStatement statement) {
        print("while (");
        statement.getExpression().accept(this);
        println(')');
        statement.getBlock().accept(this);

        return null;
    }

    @Override
    public Void visit(PrintStatement statement) {
        print("print ");
        statement.getExpression().accept(this);
        print(';');

        return null;
    }

    @Override
    public Void visit(PrintLineStatement statement) {
        print("println ");
        statement.getExpression().accept(this);
        print(';');

        return null;
    }

    @Override
    public Void visit(ReturnStatement statement) {
        print("return");

        final Optional<Expression> expression = statement.getExpression();
        expression.ifPresent((e) -> {
            print(' ');
            e.accept(this);
        });

        print(';');

        return null;
    }

    @Override
    public Void visit(AssignmentStatement statement) {
        statement.getId().accept(this);
        print('=');
        statement.getExpression().accept(this);
        print(';');

        return null;
    }

    @Override
    public Void visit(ArrayAssignment assignment) {
        assignment.getId().accept(this);
        print('[');
        assignment.getIndex().accept(this);
        print("]=");
        assignment.getAssignment().accept(this);
        print(';');

        return null;
    }

    @Override
    public Void visit(Block block) {
        println('{');
        mIndentationLevel++;
        block.getStatements().accept(this);
        mIndentationLevel--;
        print('}');

        return null;
    }

    @Override
    public Void visit(EqualityExpression expression) {
        expression.getLeftSide().accept(this);
        print("==");
        expression.getRightSide().accept(this);

        return null;
    }

    @Override
    public Void visit(LessThanExpression expression) {
        expression.getLeftSide().accept(this);
        print('<');
        expression.getRightSide().accept(this);

        return null;
    }

    @Override
    public Void visit(AddExpression expression) {
        expression.getLeftSide().accept(this);
        print('+');
        expression.getRightSide().accept(this);

        return null;
    }

    @Override
    public Void visit(SubtractExpression expression) {
        expression.getLeftSide().accept(this);
        print('-');
        expression.getRightSide().accept(this);

        return null;
    }

    @Override
    public Void visit(MultiplyExpression expression) {
        expression.getLeftSide().accept(this);
        print('*');
        expression.getRightSide().accept(this);

        return null;
    }

    @Override
    public Void visit(ParenExpression expression) {
        print('(');
        expression.getExpression().accept(this);
        print(')');

        return null;
    }

    @Override
    public Void visit(ArrayReference reference) {
        reference.getId().accept(this);
        print('[');
        reference.getExpression().accept(this);
        print(']');

        return null;
    }

    @Override
    public Void visit(FunctionCall functionCall) {
        functionCall.getId().accept(this);
        print('(');
        functionCall.getExpressions().accept(this);
        print(')');

        return null;
    }

    @Override
    public Void visit(ExpressionList expressionList) {
        final Iterator<Expression> expressions = expressionList.getElements().iterator();
        while (expressions.hasNext()) {
            final Expression current = expressions.next();
            current.accept(this);

            if (expressions.hasNext()) {
                print(",");
            }
        }

        return null;
    }

}
