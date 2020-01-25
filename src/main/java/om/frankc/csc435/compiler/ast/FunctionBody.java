package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class FunctionBody extends AstNode {

    public FunctionBody(VariableDeclarationList declarations, StatementList statements,
                        int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
        assert declarations != null;
        assert statements != null;
        mDeclarations = declarations;
        mStatements = statements;
    }

    private final VariableDeclarationList mDeclarations;
    private final StatementList mStatements;

    public VariableDeclarationList getDeclarations() {
        return mDeclarations;
    }

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
        if (other instanceof FunctionBody) {
            final FunctionBody known = (FunctionBody) other;
            return super.equals(known)
                    && Objects.equals(mDeclarations, known.mDeclarations)
                    && Objects.equals(mStatements, known.mStatements);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mDeclarations, mStatements);
    }

}
