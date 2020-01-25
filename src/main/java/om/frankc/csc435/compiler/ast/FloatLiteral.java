package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class FloatLiteral extends Literal {

    public FloatLiteral(float value,
                        int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
        assert value >= 0 ;
        mValue = value;
    }

    private final float mValue;

    public float getValue() {
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
        if (other instanceof FloatLiteral) {
            final FloatLiteral known = (FloatLiteral) other;
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
