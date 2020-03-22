package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public class JGoTo implements JInstruction {

    public JGoTo(JLabel label) {
        assert label != null;
        mLabel = label;
    }

    private final JLabel mLabel;

    public JLabel getLabel() {
        return mLabel;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
