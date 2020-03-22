package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public class BooleanConstant implements JConstant {

    public BooleanConstant(boolean value) {
        mValue = value;
    }

    private final boolean mValue;

    public boolean getValue() {
        return mValue;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
