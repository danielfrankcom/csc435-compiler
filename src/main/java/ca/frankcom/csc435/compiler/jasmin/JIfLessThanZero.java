package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public class JIfLessThanZero implements JInstruction {

    public JIfLessThanZero(JLabel successLabel) {
        assert successLabel != null;
        mSuccessLabel = successLabel;
    }

    private final JLabel mSuccessLabel;

    public JLabel getSuccessLabel() {
        return mSuccessLabel;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
