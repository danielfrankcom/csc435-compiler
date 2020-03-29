package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public class JArrayLoad implements JInstruction {

    public JArrayLoad(JArrayType arrayType) {
        assert arrayType != null;
        mArrayType = arrayType;
    }

    private final JArrayType mArrayType;

    public JArrayType getArrayType() {
        return mArrayType;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
