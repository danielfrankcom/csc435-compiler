package om.frankc.csc435.compiler.ast;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;

public class FormalParameterList extends AstNode {

    public FormalParameterList(List<FormalParameter> parameters) {
        assert parameters != null;
        mParameters = parameters;
    }

    private final List<FormalParameter> mParameters;

    public ImmutableList<FormalParameter> getParameters() {
        return ImmutableList.copyOf(mParameters);
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
        if (other instanceof FormalParameterList) {
            final FormalParameterList known = (FormalParameterList) other;
            return Objects.equals(mParameters, known.mParameters);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mParameters);
    }

}
