package om.frankc.csc435.compiler.ir;

import om.frankc.csc435.compiler.ir.visit.IIrVisitor;

public final class IrType implements IIrNode {

    public static IrType VOID = new IrType("V");
    public static IrType BOOLEAN = new IrType("Z");
    public static IrType CHARACTER = new IrType("C");
    public static IrType INTEGER = new IrType("I");
    public static IrType FLOATING_POINT = new IrType("F");
    public static IrType STRING = new IrType("U");

    public static IrType arrayOf(IrType type) {
        return new IrType("A" + type.toString());
    }

    private IrType(String identifier) {
        assert identifier != null;
        mId = identifier;
    }

    private final String mId;

    @Override
    public final String toString() {
        return mId;
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
