package om.frankc.csc435.compiler.ir;

import om.frankc.csc435.compiler.ir.visit.IIrVisitor;

public class VariableAssignment implements IInstruction {

    public VariableAssignment(IrTemp left, IrTemp right) {
        assert left != null;
// todo        assert right != null;
        mLeft = left;
        mRight = right;
    }

    private final IrTemp mLeft;
    private final IrTemp mRight;

    public IrTemp getLeft() {
        return mLeft;
    }

    public IrTemp getRight() {
        return mRight;
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
