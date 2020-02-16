package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.visit.IAstVisitor;

import java.util.Objects;

public class AssignmentStatement extends Statement {

    public AssignmentStatement(Identifier id, Expression expression,
                               int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
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
    public <T> T accept(IAstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof AssignmentStatement) {
            final AssignmentStatement known = (AssignmentStatement) other;
            return super.equals(known)
                    && Objects.equals(mId, known.mId)
                    && Objects.equals(mExpression, known.mExpression);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mId, mExpression);
    }

}
