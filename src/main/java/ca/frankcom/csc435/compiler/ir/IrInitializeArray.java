package ca.frankcom.csc435.compiler.ir;

import ca.frankcom.csc435.compiler.ir.visit.IIrVisitor;

public class IrInitializeArray implements IInstruction {

    /**
     * @param type The {@link IrType} of the elements contained within the array. The {@link IrType.Reference#isArray()}
     *             method should return {@code false}.
     * @param size The size of the array to initialize. The value must be greater than or equal to 0. Though an array
     *             of size 0 in this language in practice, we allow it.
     * @param temp The {@link IrTemp} that the array will be stored in once initialized.
     */
    public IrInitializeArray(IrType type, int size, IrTemp temp) {
        assert type != null;
        assert size >= 0; // 0 size not useful but valid
        assert temp != null;
        mType = type;
        mSize = size;
        mTemp = temp;
    }

    private final IrType mType;
    private final int mSize;
    private final IrTemp mTemp;

    public IrType getType() {
        return mType;
    }

    public int getSize() {
        return mSize;
    }

    public IrTemp getTemp() {
        return mTemp;
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
