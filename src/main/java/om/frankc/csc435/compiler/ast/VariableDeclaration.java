package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class VariableDeclaration extends AstNode {

    public VariableDeclaration(TypeNode typeNode, Identifier id) {
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
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof VariableDeclaration) {
            final VariableDeclaration known = (VariableDeclaration) other;
            return Objects.equals(mTypeNode, known.mTypeNode)
                    && Objects.equals(mId, known.mId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mTypeNode, mId);
    }

}
