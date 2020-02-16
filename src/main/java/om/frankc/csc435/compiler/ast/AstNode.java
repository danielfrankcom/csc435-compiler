package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.visit.IAstVisitor;

import java.util.Objects;

public abstract class AstNode {

    AstNode(int lineNumber, int linePosition) {
        assert lineNumber > 0;
        assert linePosition >= 0;
        mLineNumber = lineNumber;
        mLinePosition = linePosition;
    }

    private final int mLineNumber;
    private final int mLinePosition;

    public int getLineNumber() {
        return mLineNumber;
    }

    public int getLinePosition() {
        return mLinePosition;
    }

    public abstract void accept(IAstVisitor visitor);

    /**
     * {@inheritDoc}
     * <p>
     * Classes that extends this interface <strong>MUST</strong> call the superclass to
     * ensure that all elements are properly compared to determine equality.
     * </p>
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof AstNode) {
            final AstNode known = (AstNode) other;
            return Objects.equals(mLineNumber, known.mLineNumber)
                    && Objects.equals(mLinePosition, known.mLinePosition);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Classes that extends this interface <strong>MUST</strong> call the superclass to
     * ensure that all elements are properly hashed.
     * </p>
     */
    @Override
    public int hashCode() {
        return Objects.hash(mLineNumber, mLinePosition);
    }

}
