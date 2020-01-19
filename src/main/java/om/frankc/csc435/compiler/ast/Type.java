package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class Type extends TypeNode {

    public static final Type BOOLEAN = new Type(TypeName.Boolean);
    public static final Type CHAR = new Type(TypeName.Character);
    public static final Type FLOAT = new Type(TypeName.FloatingPoint);
    public static final Type INT = new Type(TypeName.Integer);
    public static final Type STRING = new Type(TypeName.String);
    public static final Type VOID = new Type(TypeName.Void);

    private enum TypeName {
        Boolean("boolean"),
        Character("char"),
        FloatingPoint("float"),
        Integer("int"),
        String("string"),
        Void("void");

        TypeName(String name) {
            assert name != null;
            mName = name;
        }

        private final String mName;

        String getName() {
            return mName;
        }

    }

    private Type(TypeName type) {
        mType = type;
    }

    private final TypeName mType;

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
            return Objects.equals(mType, known.mType);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mType);
    }

}
