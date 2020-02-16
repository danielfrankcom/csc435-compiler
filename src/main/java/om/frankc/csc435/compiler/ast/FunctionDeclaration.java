package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.visit.IAstVisitor;

import java.util.Objects;

public class FunctionDeclaration extends AstNode {

    public FunctionDeclaration(TypeNode typeNode, Identifier id, FormalParameterList paramList,
                               int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
        assert id != null;
        assert typeNode != null;
        assert paramList != null;

        mTypeNode = typeNode;
        mId = id;
        mParamList = paramList;
    }

    private final TypeNode mTypeNode;
    private final Identifier mId;
    private final FormalParameterList mParamList;

    public TypeNode getTypeNode() {
        return mTypeNode;
    }

    public Identifier getId() {
        return mId;
    }

    public FormalParameterList getParamList() {
        return mParamList;
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
        if (other instanceof FunctionDeclaration) {
            final FunctionDeclaration known = (FunctionDeclaration) other;
            return super.equals(known)
                    && Objects.equals(mTypeNode, known.mTypeNode)
                    && Objects.equals(mId, known.mId)
                    && Objects.equals(mParamList, known.mParamList);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mTypeNode, mId, mParamList);
    }

}
