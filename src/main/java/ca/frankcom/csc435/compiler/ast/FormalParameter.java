package ca.frankcom.csc435.compiler.ast;

import ca.frankcom.csc435.compiler.ast.visit.IAstVisitor;

import java.util.Objects;

public class FormalParameter extends AstNode {

    public FormalParameter(TypeNode typeNode, Identifier id,
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
        if (other instanceof FormalParameter) {
            final FormalParameter known = (FormalParameter) other;
            return super.equals(known)
                    && Objects.equals(mTypeNode, known.mTypeNode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mTypeNode);
    }

}
