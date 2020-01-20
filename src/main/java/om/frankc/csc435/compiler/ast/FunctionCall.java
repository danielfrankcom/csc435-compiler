package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class FunctionCall extends Expression {

    public FunctionCall(Identifier id, ExpressionList expressions) {
        assert id != null;
        assert expressions != null;

        mId = id;
        mExpressions = expressions;
    }

    private final Identifier mId;
    private final ExpressionList mExpressions;

    public Identifier getId() {
        return mId;
    }

    public ExpressionList getExpressions() {
        return mExpressions;
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
        if (other instanceof FunctionCall) {
            final FunctionCall known = (FunctionCall) other;
            return Objects.equals(mId, known.mId)
                    && Objects.equals(mExpressions, known.mExpressions);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mExpressions);
    }

}
