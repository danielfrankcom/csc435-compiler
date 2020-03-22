package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public class VarDirective implements IJasminNode {

    public VarDirective(int varNumber, JType.UniversalType type, JLabel startLabel, JLabel endLabel) {
        assert varNumber >= 0;
        assert type != null;
        assert startLabel != null;
        assert endLabel != null;

        mVarNumber = varNumber;
        mType = type;
        mStartLabel = startLabel;
        mEndLabel = endLabel;
    }

    private final int mVarNumber;
    private final JType.UniversalType mType;
    private final JLabel mStartLabel;
    private final JLabel mEndLabel;

    public int getVarNumber() {
        return mVarNumber;
    }

    public JType.UniversalType getType() {
        return mType;
    }

    public JLabel getStartLabel() {
        return mStartLabel;
    }

    public JLabel getEndLabel() {
        return mEndLabel;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
