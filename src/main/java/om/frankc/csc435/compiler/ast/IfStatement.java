package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.visit.IAstVisitor;

import java.util.Objects;

public class IfStatement extends Statement {

    public IfStatement(Expression expression, Block block,
                       int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
        assert expression != null;
        assert block != null;

        mExpression = expression;
        mBlock = block;
    }

    private final Expression mExpression;
    private final Block mBlock;

    public Expression getExpression() {
        return mExpression;
    }

    public Block getIfBlock() {
        return mBlock;
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
        if (other instanceof IfStatement) {
            final IfStatement known = (IfStatement) other;
            return super.equals(known)
                    && Objects.equals(mExpression, known.mExpression)
                    && Objects.equals(mBlock, known.mBlock);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mExpression, mBlock);
    }

}
