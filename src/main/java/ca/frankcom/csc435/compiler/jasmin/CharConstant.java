package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public class CharConstant implements JConstant {

    public CharConstant(char value) {
        mValue = value;
    }

    private final char mValue;

    public char getValue() {
        return mValue;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
