package ca.frankcom.csc435.compiler.ir;

import ca.frankcom.csc435.compiler.ir.visit.IIrVisitor;

public class IrArrayAccess implements IInstruction {

    public IrArrayAccess(IrTemp result, IrTemp array, IrTemp index) {
        assert result != null;
        assert array != null;
        assert index != null;
        mResult = result;
        mArray = array;
        mIndex = index;
    }

    private final IrTemp mResult;
    private final IrTemp mArray;
    private final IrTemp mIndex;

    public IrTemp getResult() {
        return mResult;
    }

    public IrTemp getArray() {
        return mArray;
    }

    public IrTemp getIndex() {
        return mIndex;
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
