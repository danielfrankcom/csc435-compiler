package ca.frankcom.csc435.compiler.ast;

import ca.frankcom.csc435.compiler.ast.visit.IAstVisitor;

import java.util.Objects;

public class ArrayType extends TypeNode {

    public ArrayType(Type type, IntegerLiteral constant,
                     int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
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
    public <T> T accept(IAstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ArrayType) {
            final ArrayType known = (ArrayType) other;
            return super.equals(known)
                    && Objects.equals(mType, known.mType)
                    && Objects.equals(mSize, known.mSize);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mType, mSize);
    }

}
