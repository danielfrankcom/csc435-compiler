package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.visit.IAstVisitor;

import java.util.Objects;

public class ArrayAssignment extends Statement {

    public ArrayAssignment(Identifier id, Expression index, Expression assignment,
                           int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
        assert id != null;
        assert index != null;
        assert assignment != null;

        mId = id;
        mIndex = index;
        mAssignment = assignment;
    }

    private final Identifier mId;
    private final Expression mIndex;
    private final Expression mAssignment;

    public Identifier getId() {
        return mId;
    }

    public Expression getIndex() {
        return mIndex;
    }

    public Expression getAssignment() {
        return mAssignment;
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
        if (other instanceof ArrayAssignment) {
            final ArrayAssignment known = (ArrayAssignment) other;
            return super.equals(known)
                    && Objects.equals(mId, known.mId)
                    && Objects.equals(mIndex, known.mIndex)
                    && Objects.equals(mAssignment, known.mAssignment);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mId, mIndex, mAssignment);
    }

}
