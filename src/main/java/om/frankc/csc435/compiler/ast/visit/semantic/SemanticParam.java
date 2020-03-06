package om.frankc.csc435.compiler.ast.visit.semantic;

import java.util.Objects;

public class SemanticParam {

    SemanticParam(SemanticType type, String name) {
        assert type != null;
        assert name != null;
        mType = type;
        mName = name;
    }

    private final SemanticType mType;
    private final String mName;

    public SemanticType getType() {
        return mType;
    }

    public String getName() {
        return mName;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof SemanticParam) {
            final SemanticParam known = (SemanticParam) other;
            return Objects.equals(mType, known.mType)
                    && Objects.equals(mName, mName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mType, mName);
    }
}
