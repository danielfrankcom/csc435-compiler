package om.frankc.csc435.compiler.ir.visit.print;

import om.frankc.csc435.compiler.ir.*;
import om.frankc.csc435.compiler.ir.visit.IIrVisitor;

import java.util.List;
import java.util.function.Consumer;

public class PrettyPrintIrVisitor implements IIrVisitor<Void> {

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

    public PrettyPrintIrVisitor(Consumer<String> output) {
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
    public Void visit(IrProgram program) {
        print("PROG ");
        println(program.getName());

        for (IrFunction function : program.getFunctions()) {
            println();
            function.accept(this);
        }

        return null;
    }

    @Override
    public Void visit(IrFunction function) {
        print("FUNC ");
        print(function.getName());

        print(" (");
        for (IrType type : function.getParamTypes()) {
            type.accept(this);
        }
        print(")");
        function.getReturnType().accept(this);
        println();
        println("{");

        mIndentationLevel++;
        // Special handling for temporary declarations, not
        // accounted for in visitor implementation of node
        for (IrTemp declaration : function.getDeclarations()) {
            print("TEMP ");
            print(declaration.getNumber());
            print(":");
            declaration.getType().accept(this);
            println(";");
        }

        mIndentationLevel++;
        final List<IInstruction> instructions = function.getInstructions();
        if (instructions.size() > 0) {
            println();
        }
        for (IInstruction instruction : instructions) {
            instruction.accept(this);
        }

        mIndentationLevel -= 2;
        println("}");

        return null;
    }

    @Override
    public Void visit(IrTemp temporary) {
        print("T");
        print(temporary.getNumber());
        return null;
    }

    @Override
    public Void visit(IrType type) {
        print(type.toString());
        return null;
    }

    @Override
    public Void visit(IrLabel label) {
        return null;
    }

    @Override
    public Void visit(ConstantAssignment assignment) {
        assignment.getOperand().accept(this);
        print(" := ");
        print(assignment.getConstant());
        println(";");
        return null;
    }

    @Override
    public Void visit(VariableAssignment assignment) {
        assignment.getLeft().accept(this);
        print(" := ");
        assignment.getRight().accept(this);
        println(";");
        return null;
    }

    @Override
    public Void visit(Print statement) {
        print("PRINT");

        final IrTemp temp = statement.getOperand();
        temp.getType().accept(this);
        print(" ");
        temp.accept(this);
        println(";");

        return null;
    }

    @Override
    public Void visit(PrintLn statement) {
        print("PRINTLN");

        final IrTemp temp = statement.getOperand();
        temp.getType().accept(this);
        print(" ");
        temp.accept(this);
        println(";");

        return null;
    }

}
