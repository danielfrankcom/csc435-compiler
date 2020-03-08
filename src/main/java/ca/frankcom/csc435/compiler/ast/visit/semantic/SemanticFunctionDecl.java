package ca.frankcom.csc435.compiler.ast.visit.semantic;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;

public class SemanticFunctionDecl {

    SemanticFunctionDecl(SemanticType type, String name, List<SemanticParam> params) {
        assert type != null;
        assert name != null;
        assert params != null;
        mType = type;
        mName = name;
        mParams = params;
    }

    private final SemanticType mType;
    private final String mName;
    private final List<SemanticParam> mParams;

    public SemanticType getReturnType() {
        return mType;
    }

    public String getName() {
        return mName;
    }

    public ImmutableList<SemanticParam> getParams() {
        return ImmutableList.copyOf(mParams);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof SemanticFunctionDecl) {
            final SemanticFunctionDecl known = (SemanticFunctionDecl) other;
            return Objects.equals(mType, known.mType)
                    && Objects.equals(mName, known.mName)
                    && Objects.equals(mParams, known.mParams);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mType, mName, mParams);
    }

}
