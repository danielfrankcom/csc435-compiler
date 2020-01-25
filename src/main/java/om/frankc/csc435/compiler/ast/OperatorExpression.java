package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public abstract class OperatorExpression extends Expression {

    public OperatorExpression(Expression leftSide, Expression rightSide,
                              int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
        assert leftSide != null;
        assert rightSide != null;

        mLeftSide = leftSide;
        mRightSide = rightSide;
    }

    private final Expression mLeftSide;
    private final Expression mRightSide;

    public Expression getLeftSide() {
        return mLeftSide;
    }

    public Expression getRightSide() {
        return mRightSide;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof OperatorExpression) {
            final OperatorExpression known = (OperatorExpression) other;
            return super.equals(known)
                    && Objects.equals(mLeftSide, known.mLeftSide)
                    && Objects.equals(mRightSide, known.mRightSide);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mLeftSide, mRightSide);
    }

}
