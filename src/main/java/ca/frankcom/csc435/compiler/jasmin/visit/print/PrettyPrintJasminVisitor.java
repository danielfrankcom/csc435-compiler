package ca.frankcom.csc435.compiler.jasmin.visit.print;

import ca.frankcom.csc435.compiler.jasmin.*;
import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

import java.util.function.Consumer;

public class PrettyPrintJasminVisitor implements IJasminVisitor<Void> {

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

    public PrettyPrintJasminVisitor(Consumer<String> output) {
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

    private static String convertType(JType type) {
        assert type != null;

        if (type instanceof JType.Return) {
            return "V";
        }

        assert type instanceof JType.UniversalType;
        final JType.Universal universalType = (JType.Universal) type;

        final String output;
        switch (universalType) {
            case Boolean:
                output = "Z";
                break;
            case BooleanArray:
                output = "[Z";
                break;
            case Character:
                output = "C";
                break;
            case CharacterArray:
                output = "[C";
                break;
            case FloatingPoint:
                output = "F";
                break;
            case FloatingPointArray:
                output = "[F";
                break;
            case Integer:
                output = "I";
                break;
            case IntegerArray:
                output = "[I";
                break;
            case String:
                output = "Ljava/lang/String;";
                break;
            case StringArray:
                output = "[Ljava/lang/String;";
                break;
            default:
                final String message = "Unexpected type";
                throw new IllegalArgumentException(message);
        }

        return output;
    }

    private static String convertType(JInstructionType type) {
        assert type != null;

        switch (type) {
            case FloatingPoint:
                return "f";
            case Address:
                return "a";
            case Integer:
                return "i";
            default:
                final String message = "Unexpected type";
                throw new IllegalArgumentException(message);
        }
    }

    private static String convertArrayType(JType.UniversalType type) {
        assert type != null;
        assert type instanceof JType.UniversalType;
        final JType.Universal universalType = (JType.Universal) type;

        final String output;
        switch (universalType) {
            case Integer:
                output = "int";
                break;
            case FloatingPoint:
                output = "float";
                break;
            case Character:
                output = "char";
                break;
            case Boolean:
                output = "boolean";
                break;
            case String:
                output = "java/lang/String";
                break;
            default:
                final String message = "Unexpected type";
                throw new IllegalArgumentException(message);
        }

        return output;
    }

    @Override
    public Void visit(JProgram program) {
        final String programName = program.getName();

        println(JasminBoilerplate.getHeader(programName));
        println();

        for (JFunction function : program.getFunctions()) {
            function.accept(this);
            println();
        }

        println(JasminBoilerplate.getFooter(programName));
        println();

        return null;
    }

    @Override
    public Void visit(JFunction function) {
        print(".method public static ");
        function.getDeclaration().accept(this);
        println();

        mIndentationLevel++;

        function.getLimitDirectives().accept(this);
        println();

        for (VarDirective directive : function.getVarDirectives()) {
            directive.accept(this);
        }
        println();

        for (JInstruction instruction : function.getInstructions()) {
            instruction.accept(this);
        }

        mIndentationLevel--;

        println(".end method");

        return null;
    }

    @Override
    public Void visit(FunctionDeclaration declaration) {
        print(declaration.getName());
        print("(");

        for (JType.UniversalType type : declaration.getParamTypes()) {
            print(convertType(type));
        }

        print(")");
        print(convertType(declaration.getReturnType()));

        return null;
    }

    @Override
    public Void visit(LimitDirectives directives) {
        print(".limit locals ");
        println(directives.getLocalLimit());

        print(".limit stack ");
        println(directives.getStackLimit());

        return null;
    }

    @Override
    public Void visit(VarDirective directive) {
        print(".var ");
        print(directive.getVarNumber());
        print(" is T");
        print(directive.getVarNumber());
        print(" ");
        print(convertType(directive.getType()));
        print(" from ");
        print(directive.getStartLabel().toString());
        print(" to ");
        print(directive.getEndLabel().toString());
        println();

        return null;
    }

    @Override
    public Void visit(JLabel label) {
        mIndentationLevel--;
        print(label.toString());
        println(":");
        mIndentationLevel++;
        return null;
    }

    @Override
    public Void visit(JLoad instruction) {
        final JInstructionType type = instruction.getType();
        print(convertType(type));

        print("load ");
        println(instruction.getVarNumber());

        return null;
    }

    @Override
    public Void visit(JStore instruction) {
        final JInstructionType type = instruction.getType();
        print(convertType(type));

        print("store ");
        println(instruction.getVarNumber());

        return null;
    }

    @Override
    public Void visit(JDuplicateTopOfStack instruction) {
        println("dup");

        return null;
    }

    @Override
    public Void visit(JIfZero instruction) {
        print("ifeq ");
        println(instruction.getSuccessLabel().toString());

        return null;
    }

    @Override
    public Void visit(JIfLessThanZero instruction) {
        print("iflt ");
        println(instruction.getSuccessLabel().toString());

        return null;
    }

    @Override
    public Void visit(JIfGreaterThanZero instruction) {
        print("ifgt ");
        println(instruction.getSuccessLabel().toString());

        return null;
    }

    @Override
    public Void visit(JGoTo instruction) {
        print("goto ");
        println(instruction.getLabel().toString());

        return null;
    }

    @Override
    public Void visit(ValueReturn instruction) {
        final JInstructionType type = instruction.getType();
        print(convertType(type));

        println("return");

        return null;
    }

    @Override
    public Void visit(VoidReturn instruction) {
        println("return");
        return null;
    }

    @Override
    public Void visit(JInvokeInstruction instruction) {
        print("invokestatic ");
        print(instruction.getProgramName());
        print("/");
        print(instruction.getFunctionName());

        print("(");
        for (JType.UniversalType type : instruction.getParamTypes()) {
            print(convertType(type));
        }
        print(")");

        println(convertType(instruction.getReturnType()));

        return null;
    }

    @Override
    public Void visit(JLoadPrintStream instruction) {
        println("getstatic java/lang/System/out Ljava/io/PrintStream;");
        return null;
    }

    @Override
    public Void visit(JStringBuilderNew instruction) {
        println("new java/lang/StringBuilder");
        return null;
    }

    @Override
    public Void visit(JStringBuilderInit instruction) {
        println("invokespecial java/lang/StringBuilder/<init>()V");
        return null;
    }

    @Override
    public Void visit(JStringBuilderAppend instruction) {
        println("invokevirtual java/lang/StringBuilder/append(Ljava/lang/String;)Ljava/lang/StringBuilder;");
        return null;
    }

    @Override
    public Void visit(JStringBuilderOutput instruction) {
        println("invokevirtual java/lang/StringBuilder/toString()Ljava/lang/String;");
        return null;
    }

    @Override
    public Void visit(JStringCompare instruction) {
        println("invokevirtual java/lang/String/compareTo(Ljava/lang/String;)I");
        return null;
    }

    @Override
    public Void visit(JArrayNew instruction) {
        final JArrayType arrayType = instruction.getArrayType();
        if (arrayType.equals(JArrayType.Address)) {
            print("anewarray ");
        } else {
            assert arrayType.equals(JArrayType.Primitive);
            print("newarray ");
        }

        final JType.UniversalType elementType = instruction.getElementType();
        final String type = convertArrayType(elementType);
        println(type);

        return null;
    }

    @Override
    public Void visit(JArrayStore instruction) {
        final JArrayType arrayType = instruction.getArrayType();
        if (arrayType.equals(JArrayType.Address)) {
            println("aastore");
        } else {
            assert arrayType.equals(JArrayType.Primitive);
            println("iastore");
        }

        return null;
    }

    @Override
    public Void visit(JArrayLoad instruction) {
        final JArrayType arrayType = instruction.getArrayType();
        if (arrayType.equals(JArrayType.Address)) {
            println("aaload");
        } else {
            assert arrayType.equals(JArrayType.Primitive);
            println("iaload");
        }

        return null;
    }

    @Override
    public Void visit(JPrint instruction) {
        print("invokevirtual java/io/PrintStream/print(");
        print(convertType(instruction.getType()));
        println(")V");
        return null;
    }

    @Override
    public Void visit(JPrintLn instruction) {
        print("invokevirtual java/io/PrintStream/println(");
        print(convertType(instruction.getType()));
        println(")V");
        return null;
    }

    @Override
    public Void visit(JLoadConstant instruction) {
        print("ldc ");
        instruction.getConstant().accept(this);
        println();

        return null;
    }

    @Override
    public Void visit(IntegerConstant constant) {
        print(constant.getValue());

        return null;
    }

    @Override
    public Void visit(BooleanConstant constant) {
        final boolean value = constant.getValue();
        final int intValue = value ? 1 : 0;
        print(intValue);

        return null;
    }

    @Override
    public Void visit(FloatConstant constant) {
        print(constant.getValue());

        return null;
    }

    @Override
    public Void visit(CharConstant constant) {
        final int intValue = constant.getValue();
        print(intValue);

        return null;
    }

    @Override
    public Void visit(StringConstant constant) {
        print("\"");
        print(constant.getValue());
        print("\"");

        return null;
    }

    @Override
    public Void visit(JFloatCompare instruction) {
        println("fcmpg");
        return null;
    }

    @Override
    public Void visit(JFloatSubtract instruction) {
        println("fsub");
        return null;
    }

    @Override
    public Void visit(JFloatAdd instruction) {
        println("fadd");
        return null;
    }

    @Override
    public Void visit(JFloatMultiply instruction) {
        println("fmul");
        return null;
    }

    @Override
    public Void visit(JIntegerSubtract instruction) {
        println("isub");
        return null;
    }

    @Override
    public Void visit(JIntegerAdd instruction) {
        println("iadd");
        return null;
    }

    @Override
    public Void visit(JIntegerMultiply instruction) {
        println("imul");
        return null;
    }

    @Override
    public Void visit(JIntegerXor instruction) {
        println("ixor");
        return null;
    }

}
