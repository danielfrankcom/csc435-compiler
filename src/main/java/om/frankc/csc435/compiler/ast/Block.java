package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.visit.IAstVisitor;

import java.util.Objects;

public class Block extends AstNode {

    public Block(StatementList statements,
                 int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
        assert statements != null;
        mStatements = statements;
    }

    private final StatementList mStatements;

    public StatementList getStatements() {
        return mStatements;
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
        if (other instanceof Block) {
            final Block known = (Block) other;
            return super.equals(known)
                    && Objects.equals(mStatements, known.mStatements);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mStatements);
    }

}
