package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public class JArrayStore implements JInstruction {

    public JArrayStore(JArrayType arrayType) {
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
