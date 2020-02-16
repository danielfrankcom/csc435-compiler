package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.visit.IAstVisitor;

import java.util.Objects;

public class PrintLineStatement extends Statement {

    public PrintLineStatement(Expression expression,
                              int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
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
        if (other instanceof PrintLineStatement) {
            final PrintLineStatement known = (PrintLineStatement) other;
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
