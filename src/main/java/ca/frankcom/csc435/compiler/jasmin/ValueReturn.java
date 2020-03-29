package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public class ValueReturn implements JReturn {

    public ValueReturn(JInstructionType type) {
        assert type != null;
        mType = type;
    }

    private final JInstructionType mType;

    public JInstructionType getType() {
        return mType;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
