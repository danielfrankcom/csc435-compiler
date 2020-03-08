package om.frankc.csc435.compiler.ir.visit.print;

import om.frankc.csc435.compiler.ir.*;
import om.frankc.csc435.compiler.ir.visit.IIrVisitor;

import java.util.List;
import java.util.Optional;
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

    private void println(int integer) {
        println(Integer.toString(integer));
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
        final String output;
        if (type instanceof IrType.Atomic) {
            final IrType.Atomic atomic = (IrType.Atomic) type;
            switch (atomic) {
                case Boolean:
                    output = "Z";
                    break;
                case Character:
                    output = "C";
                    break;
                case FloatingPoint:
                    output = "F";
                    break;
                case Integer:
                    output = "I";
                    break;
                case Void:
                    output = "V";
                    break;
                default:
                    final String message = "Unexpected type";
                    throw new IllegalArgumentException(message);
            }
        } else {
            assert type instanceof IrType.Reference;
            final IrType.Reference reference = (IrType.Reference) type;
            switch (reference) {
                case String:
                    output = "U";
                    break;
                case BooleanArray:
                    output = "AZ";
                    break;
                case CharacterArray:
                    output = "AC";
                    break;
                case FloatingPointArray:
                    output = "AF";
                    break;
                case IntegerArray:
                    output = "AI";
                    break;
                case StringArray:
                    output = "AU";
                    break;
                default:
                    final String message = "Unexpected type";
                    throw new IllegalArgumentException(message);
            }
        }
        print(output);
        return null;
    }

    @Override
    public Void visit(IrLabel instruction) {
        mIndentationLevel--;
        print("L");
        print(instruction.getLabelNum());
        println(":;");
        mIndentationLevel++;
        return null;
    }

    @Override
    public Void visit(Jump instruction) {
        print("GOTO L");
        print(instruction.getJumpLabel().getLabelNum());
        println(";");
        return null;
    }

    @Override
    public Void visit(ConditionalJump instruction) {
        print("IF ");
        instruction.getCondition().accept(this);
        print(" GOTO L");
        print(instruction.getJumpLabel().getLabelNum());
        println(";");
        return null;
    }

    @Override
    public Void visit(IrInitializeArray instruction) {
        instruction.getTemp().accept(this);
        print(" := NEWARRAY ");
        instruction.getType().accept(this);
        print(" ");
        print(instruction.getSize());
        println(";");

        return null;
    }

    @Override
    public Void visit(IrArrayAssignment instruction) {
        instruction.getArray().accept(this);
        print("[");
        instruction.getIndex().accept(this);
        print("] := ");
        instruction.getValue().accept(this);
        println(";");

        return null;
    }

    @Override
    public Void visit(IrArrayAccess instruction) {
        instruction.getResult().accept(this);
        print(" := ");
        instruction.getArray().accept(this);
        print("[");
        instruction.getIndex().accept(this);
        println("];");

        return null;
    }

    @Override
    public Void visit(ConstantAssignment instruction) {
        instruction.getOperand().accept(this);
        print(" := ");
        print(instruction.getConstant());
        println(";");

        return null;
    }

    @Override
    public Void visit(VariableAssignment instruction) {
        instruction.getLeft().accept(this);
        print(" := ");
        instruction.getRight().accept(this);
        println(";");

        return null;
    }

    @Override
    public Void visit(Print instruction) {
        print("PRINT");

        final IrTemp temp = instruction.getOperand();
        temp.getType().accept(this);
        print(" ");
        temp.accept(this);
        println(";");

        return null;
    }

    @Override
    public Void visit(PrintLn instruction) {
        print("PRINTLN");

        final IrTemp temp = instruction.getOperand();
        temp.getType().accept(this);
        print(" ");
        temp.accept(this);
        println(";");

        return null;
    }

    @Override
    public Void visit(IrCall instruction) {
        print("CALL ");
        final String name = instruction.getFunctionName();
        print(name);

        print("(");
        for (IrTemp temp : instruction.getParams()) {
            temp.accept(this);
        }
        println(");");

        return null;
    }

    @Override
    public Void visit(IrCallWithResult instruction) {
        instruction.getResult().accept(this);
        print(" := ");
        this.visit((IrCall) instruction);

        return null;
    }

    @Override
    public Void visit(IrReturn instruction) {
        final Optional<IrTemp> possibleValue = instruction.getValue();

        print("RETURN");
        if (possibleValue.isPresent()) {
            final IrTemp value = possibleValue.get();
            print(" ");
            value.accept(this);
        }

        println(";");
        return null;
    }

    private void printBinaryOperation(BinaryOperation operation, String operator) {
        final IrTemp left = operation.getLeft();
        final IrTemp right = operation.getRight();
        final IrTemp result = operation.getResult();
        final IrType opType = left.getType();

        result.accept(this);
        print(" := ");
        left.accept(this);
        print(" ");
        opType.accept(this);
        print(operator);
        print(" ");
        right.accept(this);
        println(";");
    }

    @Override
    public Void visit(IrEquality instruction) {
        printBinaryOperation(instruction, "==");
        return null;
    }

    @Override
    public Void visit(IrLessThan instruction) {
        printBinaryOperation(instruction, "<");
        return null;
    }

    @Override
    public Void visit(IrAdd instruction) {
        printBinaryOperation(instruction, "+");
        return null;
    }

    @Override
    public Void visit(IrSubtract instruction) {
        printBinaryOperation(instruction, "-");
        return null;
    }

    @Override
    public Void visit(IrMultiply instruction) {
        printBinaryOperation(instruction, "*");
        return null;
    }

    @Override
    public Void visit(IrInversion instruction) {
        instruction.getResult().accept(this);
        print(" := Z! ");
        instruction.getOriginal().accept(this);
        println(";");

        return null;
    }

}
