package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public class JLabel implements JInstruction {

    public JLabel(String name) {
        assert name != null;
        mName = name;
    }

    private String mName;

    @Override
    public String toString() {
        return mName;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
