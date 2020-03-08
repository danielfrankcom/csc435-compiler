package ca.frankcom.csc435.compiler.ast;

import ca.frankcom.csc435.compiler.ast.visit.IAstVisitor;

import java.util.Objects;
import java.util.Optional;

public class ReturnStatement extends Statement {

    public ReturnStatement(Optional<Expression> expression,
                           int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
        assert expression != null;
        mExpression = expression;
    }

    private final Optional<Expression> mExpression;

    public Optional<Expression> getExpression() {
        return mExpression;
    }

    public boolean hasExpression() {
        return mExpression.isPresent();
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
        if (other instanceof ReturnStatement) {
            final ReturnStatement known = (ReturnStatement) other;
            return super.equals(known)
                    && Objects.equals(mExpression, known.mExpression);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mExpression);
    }

}
