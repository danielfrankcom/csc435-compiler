package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class ArrayReference extends Expression {

    public ArrayReference(Identifier id, Expression expression) {
        assert id != null;
        assert expression != null;

        mId = id;
        mExpression = expression;
    }

    private final Identifier mId;
    private final Expression mExpression;

    public Identifier getId() {
        return mId;
    }

    public Expression getExpression() {
        return mExpression;
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
        if (other instanceof ArrayReference) {
            final ArrayReference known = (ArrayReference) other;
            return Objects.equals(mId, known.mId)
                    && Objects.equals(mExpression, known.mExpression);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mExpression);
    }

}
