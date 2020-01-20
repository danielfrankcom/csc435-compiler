package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class PrintStatement extends Statement {

    public PrintStatement(Expression expression) {
        assert expression != null;
        mExpression = expression;
    }

    private final Expression mExpression;

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
        if (other instanceof PrintStatement) {
            final PrintStatement known = (PrintStatement) other;
            return Objects.equals(mExpression, known.mExpression);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mExpression);
    }

}
