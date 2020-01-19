package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class WhileStatement extends Statement {

    public WhileStatement(Expression expression, Block block) {
        // todo: assert expression != null;
        assert block != null;

        mExpression = expression;
        mBlock = block;
    }

    private final Expression mExpression;
    private final Block mBlock;

    public Expression getExpression() {
        return mExpression;
    }

    public Block getBlock() {
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
        if (other instanceof WhileStatement) {
            final WhileStatement known = (WhileStatement) other;
            return Objects.equals(mExpression, known.mExpression)
                    && Objects.equals(mBlock, known.mBlock);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mExpression, mBlock);
    }

}
