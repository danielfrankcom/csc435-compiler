package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class IntegerLiteral extends AstNode {

    public IntegerLiteral(int value) {
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
            return Objects.equals(mValue, known.mValue);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mValue);
    }

}
