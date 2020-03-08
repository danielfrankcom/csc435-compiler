package ca.frankcom.csc435.compiler.ir;

import ca.frankcom.csc435.compiler.ir.visit.IIrVisitor;

public class IrTemp implements IIrNode {

    // Limit imposed by the JVM.
    private static final int MAX_TEMPORARY_NUMBER = 65_536;

    public enum Category {
        PARAMETER,
        LOCAL,
        TEMPORARY
    }

    public IrTemp(int number, IrType type, Category category) {
        assert number <= MAX_TEMPORARY_NUMBER;
        assert number >= 0;
        assert type != null;
        assert category != null;
        mNumber = number;
        mType = type;
        mCategory = category;
    }

    private final int mNumber;
    private final IrType mType;
    private final Category mCategory;

    public int getNumber() {
        return mNumber;
    }

    public IrType getType() {
        return mType;
    }

    public Category getCategory() {
        return mCategory;
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
