package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.visit.IAstVisitor;

import java.util.Objects;

public class Type extends TypeNode {

    public enum Name {
        Boolean("boolean"),
        Character("char"),
        FloatingPoint("float"),
        Integer("int"),
        String("string"),
        Void("void");

        Name(String name) {
            assert name != null;
            mName = name;
        }

        private final String mName;

        String getName() {
            return mName;
        }

    }

    public Type(Name type,
                int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
        mType = type;
    }

    private final Name mType;

    public String getName() {
        return mType.getName();
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
        if (other instanceof Type) {
            final Type known = (Type) other;
            return super.equals(known)
                    && Objects.equals(mType, known.mType);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mType);
    }

}
