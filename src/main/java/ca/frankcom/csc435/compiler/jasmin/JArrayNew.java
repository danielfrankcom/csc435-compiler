package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public class JArrayNew implements JInstruction {

    public JArrayNew(JArrayType arrayType, JType.UniversalType elementType) {
        assert arrayType != null;
        assert elementType != null;
        mArrayType = arrayType;
        mElementType = elementType;
    }

    private final JArrayType mArrayType;
    private final JType.UniversalType mElementType;

    public JArrayType getArrayType() {
        return mArrayType;
    }

    public JType.UniversalType getElementType() {
        return mElementType;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
