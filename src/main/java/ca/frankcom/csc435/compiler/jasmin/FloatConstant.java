package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public class FloatConstant implements JConstant {

    public FloatConstant(float value) {
        mValue = value;
    }

    private final float mValue;

    public float getValue() {
        return mValue;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
