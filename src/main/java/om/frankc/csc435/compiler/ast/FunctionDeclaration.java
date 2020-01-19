package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class FunctionDeclaration extends AstNode {

    public FunctionDeclaration(TypeNode typeNode, Identifier id, FormalParameterList paramList) {
        assert id != null;
        assert typeNode != null;
        // todo: assert paramList != null;

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
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof FunctionDeclaration) {
            final FunctionDeclaration known = (FunctionDeclaration) other;
            return Objects.equals(mTypeNode, known.mTypeNode)
                    && Objects.equals(mId, known.mId)
                    && Objects.equals(mParamList, known.mParamList);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mTypeNode, mId, mParamList);
    }

}
