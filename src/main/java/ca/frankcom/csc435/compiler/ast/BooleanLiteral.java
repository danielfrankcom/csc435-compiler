package ca.frankcom.csc435.compiler.ast;

import ca.frankcom.csc435.compiler.ast.visit.IAstVisitor;

import java.util.Objects;

public class BooleanLiteral extends Literal {

    public BooleanLiteral(boolean value,
                          int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
        mValue = value;
    }

    private final boolean mValue;

    public boolean getValue() {
        return mValue;
    }

    @Override
    public <T> T accept(IAstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof BooleanLiteral) {
            final BooleanLiteral known = (BooleanLiteral) other;
            return super.equals(known)
                    && Objects.equals(mValue, known.mValue);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mValue);
    }

}
