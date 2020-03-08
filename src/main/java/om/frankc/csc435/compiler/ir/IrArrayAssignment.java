package om.frankc.csc435.compiler.ir;

import om.frankc.csc435.compiler.ir.visit.IIrVisitor;

public class IrArrayAssignment implements IInstruction {

    public IrArrayAssignment(IrTemp array, IrTemp index, IrTemp value) {
        assert array != null;
        assert index != null;
        assert value != null;
        mArray = array;
        mIndex = index;
        mValue = value;
    }

    private final IrTemp mArray;
    private final IrTemp mIndex;
    private final IrTemp mValue;

    public IrTemp getArray() {
        return mArray;
    }

    public IrTemp getIndex() {
        return mIndex;
    }

    public IrTemp getValue() {
        return mValue;
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
