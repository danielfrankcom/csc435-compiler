package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class IfElseStatement extends IfStatement {

    public IfElseStatement(Expression expression, Block ifBlock, Block elseBlock,
                           int lineNumber, int linePosition) {
        super(expression, ifBlock, lineNumber, linePosition);
        assert elseBlock != null;

        mElseBlock = elseBlock;
    }

    private final Block mElseBlock;

    public Block getElseBlock() {
        return mElseBlock;
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
        if (other instanceof IfElseStatement) {
            final IfElseStatement known = (IfElseStatement) other;
            return super.equals(known)
                    && Objects.equals(getExpression(), known.getExpression())
                    && Objects.equals(getIfBlock(), known.getIfBlock())
                    && Objects.equals(getElseBlock(), known.getElseBlock());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getExpression(), getIfBlock(), getElseBlock());
    }

}
