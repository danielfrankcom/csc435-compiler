package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public class JLoadConstant implements JInstruction {

    public JLoadConstant(JConstant constant) {
        assert constant != null;
        mConstant = constant;
    }

    private final JConstant mConstant;

    public JConstant getConstant() {
        return mConstant;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
