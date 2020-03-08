package ca.frankcom.csc435.compiler.ir;

public abstract class BinaryOperation implements IInstruction {

    public BinaryOperation(IrTemp result, IrTemp left, IrTemp right) {
        assert result != null;
        assert left != null;
        assert right != null;
        mResult = result;
        mLeft = left;
        mRight = right;
    }

    private final IrTemp mResult;
    private final IrTemp mLeft;
    private final IrTemp mRight;

    public IrTemp getResult() {
        return mResult;
    }

    public IrTemp getLeft() {
        return mLeft;
    }

    public IrTemp getRight() {
        return mRight;
    }

}
