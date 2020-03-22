package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public class StringConstant implements JConstant {

    public StringConstant(String value) {
        mValue = value;
    }

    private final String mValue;

    public String getValue() {
        return mValue;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
