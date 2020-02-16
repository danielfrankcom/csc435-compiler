package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.visit.IAstVisitor;

import java.util.Objects;

public class VariableDeclaration extends AstNode {

    public VariableDeclaration(TypeNode typeNode, Identifier id,
                               int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
        assert typeNode != null;
        assert id != null;

        mTypeNode = typeNode;
        mId = id;
    }

    private final TypeNode mTypeNode;
    private final Identifier mId;

    public TypeNode getTypeNode() {
        return mTypeNode;
    }

    public Identifier getId() {
        return mId;
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
        if (other instanceof VariableDeclaration) {
            final VariableDeclaration known = (VariableDeclaration) other;
            return super.equals(known)
                    && Objects.equals(mTypeNode, known.mTypeNode)
                    && Objects.equals(mId, known.mId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mTypeNode, mId);
    }

}
