package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public class JPrint implements JInstruction {

    public JPrint(JType.UniversalType type) {
        assert type != null;
        mType = type;
    }

    private final JType.UniversalType mType;

    public JType.UniversalType getType() {
        return mType;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
