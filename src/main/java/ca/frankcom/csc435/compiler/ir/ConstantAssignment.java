package ca.frankcom.csc435.compiler.ir;

import ca.frankcom.csc435.compiler.ir.visit.IIrVisitor;

public class ConstantAssignment implements IInstruction {

    public static abstract class Value {

        @Override
        public abstract String toString();

    }

    private ConstantAssignment(IrTemp operand, Value constant) {
        assert operand != null;
        assert constant != null;
        mOperand = operand;
        mConstant = constant;
    }

    private final IrTemp mOperand;
    private final Value mConstant;

    public IrTemp getOperand() {
        return mOperand;
    }

    public Value getValue() {
        return mConstant;
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public ConstantAssignment(IrTemp operand, int constant) {
        this(operand, new IntegerValue(constant));
    }

    public static class IntegerValue extends Value {

        IntegerValue(int value) {
            mValue = value;
        }

        private final int mValue;

        public int get() {
            return mValue;
        }

        @Override
        public String toString() {
            return Integer.toString(mValue);
        }

    }

    public ConstantAssignment(IrTemp operand, float constant) {
        this(operand, new FloatValue(constant));
    }

    public static class FloatValue extends Value {

        FloatValue(float value) {
            mValue = value;
        }

        private final float mValue;

        public float get() {
            return mValue;
        }

        @Override
        public String toString() {
            return Float.toString(mValue);
        }

    }

    public ConstantAssignment(IrTemp operand, char constant) {
        this(operand, new CharValue(constant));
    }

    public static class CharValue extends Value {

        CharValue(char value) {
            mValue = value;
        }

        private final char mValue;

        public char get() {
            return mValue;
        }

        @Override
        public String toString() {
            return "'" + mValue + "'";
        }

    }

    public ConstantAssignment(IrTemp operand, boolean constant) {
        this(operand, new BooleanValue(constant));
    }

    public static class BooleanValue extends Value {

        BooleanValue(boolean value) {
            mValue = value;
        }

        private final boolean mValue;

        public boolean get() {
            return mValue;
        }

        @Override
        public String toString() {
            return Boolean.toString(mValue).toUpperCase();
        }

    }

    public ConstantAssignment(IrTemp operand, String constant) {
        this(operand, new StringValue(constant));
    }

    public static class StringValue extends Value {

        StringValue(String value) {
            mValue = value;
        }

        private final String mValue;

        public String get() {
            return mValue;
        }

        @Override
        public String toString() {
            return "\"" + mValue + "\"";
        }

    }

}
