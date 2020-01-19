package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class Block extends AstNode {

    public Block(StatementList statements) {
        assert statements != null;
        mStatements = statements;
    }

    private final StatementList mStatements;

    public StatementList getStatements() {
        return mStatements;
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
        if (other instanceof Block) {
            final Block known = (Block) other;
            return Objects.equals(mStatements, known.mStatements);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mStatements);
    }

}
