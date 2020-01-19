package om.frankc.csc435.compiler.ast;

import java.util.Iterator;
import java.util.function.Consumer;

public class PrettyPrintAstVisitor implements IAstVisitor {

    private static final int INDENTATION_SPACES = 4;

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
            final String indent = " ".repeat(spaces);
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
    public void visit(Program program) {
        program.getFunctions().accept(this);
    }

    @Override
    public void visit(FunctionList functions) {
        for (Function function : functions.getElements()) {
            function.accept(this);
            println();
        }
    }

    @Override
    public void visit(Function function) {
        function.getDeclaration().accept(this);
        println();
        function.getBody().accept(this);
    }

    @Override
    public void visit(FunctionDeclaration declaration) {
        declaration.getTypeNode().accept(this);
        print(' ');
        declaration.getId().accept(this);
        print(' ');
        declaration.getParamList().accept(this);
    }

    @Override
    public void visit(ArrayType arrayType) {
        arrayType.getType().accept(this);
        print('[');
        arrayType.getSize().accept(this);
        print(']');
    }

    @Override
    public void visit(Type type) {
        print(type.getName());
    }

    @Override
    public void visit(Identifier id) {
        print(id.getText());
    }

    @Override
    public void visit(IntegerLiteral literal) {
        print(literal.getValue());
    }

    @Override
    public void visit(StringLiteral literal) {
        print(literal.getValue());
    }

    @Override
    public void visit(FloatLiteral literal) {
        print(literal.getValue());
    }

    @Override
    public void visit(CharacterLiteral literal) {
        print('\'');
        print(literal.getValue());
        print('\'');
    }

    @Override
    public void visit(BooleanLiteral literal) {
        print(literal.getValue());
    }

    @Override
    public void visit(FormalParameterList paramList) {
        print('(');

        final Iterator<FormalParameter> params = paramList.getElements().iterator();
        while (params.hasNext()) {
            final FormalParameter current = params.next();
            current.accept(this);

            if (params.hasNext()) {
                print(", ");
            }
        }

        print(')');
    }

    @Override
    public void visit(FormalParameter parameter) {
        parameter.getTypeNode().accept(this);
        print(' ');
        parameter.getId().accept(this);
    }

    @Override
    public void visit(FunctionBody body) {
        println('{');
        mIndentationLevel++;
        body.getDeclarations().accept(this);
        println();
        body.getStatements().accept(this);
        mIndentationLevel--;
        println('}');
    }

    @Override
    public void visit(VariableDeclarationList declarations) {
        for (VariableDeclaration current : declarations.getElements()) {
            current.accept(this);
            println();
        }
    }

    @Override
    public void visit(VariableDeclaration declaration) {
        declaration.getTypeNode().accept(this);
        print(' ');
        declaration.getId().accept(this);
        print(';');
    }

    @Override
    public void visit(StatementList statements) {
        for (Statement current : statements.getElements()) {
            current.accept(this);
            println();
        }
    }

    @Override
    public void visit(ExpressionStatement statement) {
        statement.getExpression().accept(this);
        print(';');
    }

    @Override
    public void visit(WhileStatement statement) {
        print("while (");
        statement.getExpression().accept(this);
        println(')');
        statement.getBlock().accept(this);
    }

    @Override
    public void visit(Block block) {
        println('{');
        mIndentationLevel++;
        block.getStatements().accept(this);
        mIndentationLevel--;
        print('}');
    }

    @Override
    public void visit(ExpressionList expressionList) {

    }

}
