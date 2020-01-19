package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class ArrayType extends TypeNode {

    public ArrayType(Type type, IntegerLiteral constant) {
        assert type != null;
        assert constant != null;

        mType = type;
        mSize = constant;
    }

    private final Type mType;
    private final IntegerLiteral mSize;

    public Type getType() {
        return mType;
    }

    public IntegerLiteral getSize() {
        return mSize;
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
        if (other instanceof ArrayType) {
            final ArrayType known = (ArrayType) other;
            return Objects.equals(mType, known.mType)
                    && Objects.equals(mSize, known.mSize);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mType, mSize);
    }

}
