package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class IntegerLiteral extends Literal {

    public IntegerLiteral(int value,
                          int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
        assert value >= 0;
        mValue = value;
    }

    private final int mValue;

    public int getValue() {
        return mValue;
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof IntegerLiteral) {
            final IntegerLiteral known = (IntegerLiteral) other;
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
