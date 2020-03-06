package om.frankc.csc435.compiler.ast;

import com.google.common.collect.ImmutableList;
import om.frankc.csc435.compiler.ast.visit.IAstVisitor;

import java.util.List;
import java.util.Objects;

public abstract class NodeList<E extends AstNode> extends AstNode {

    public NodeList(List<E> elements,
                    int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
        assert elements != null;
        mElements = elements;
    }

    private final List<E> mElements;

    public ImmutableList<E> getElements() {
        return ImmutableList.copyOf(mElements);
    }

    public int size() {
        return mElements.size();
    }

    @Override
    public abstract <T> T accept(IAstVisitor<T> visitor);

    @Override
    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (this.getClass() == other.getClass()) {
            final NodeList<?> known = (NodeList<?>) other;
            return super.equals(known)
                    && Objects.equals(mElements, known.mElements);
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(super.hashCode(), mElements);
    }

}
