package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public class LimitDirectives implements IJasminNode {

    public LimitDirectives(int localLimit, int stackLimit) {
        assert localLimit >= 0;
        assert stackLimit >= 0;
        mLocalLimit = localLimit;
        mStackLimit = stackLimit;
    }

    private final int mLocalLimit;
    private final int mStackLimit;

    public int getLocalLimit() {
        return mLocalLimit;
    }

    public int getStackLimit() {
        return mStackLimit;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
