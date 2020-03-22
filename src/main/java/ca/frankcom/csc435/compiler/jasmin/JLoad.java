package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public class JLoad implements JInstruction {

    public JLoad(JInstructionType type, int varNumber) {
        assert type != null;
        assert varNumber >= 0;

        mType = type;
        mVarNumber = varNumber;
    }

    private final JInstructionType mType;
    private final int mVarNumber;

    public JInstructionType getType() {
        return mType;
    }

    public int getVarNumber() {
        return mVarNumber;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
