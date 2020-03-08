package om.frankc.csc435.compiler.ir;

import om.frankc.csc435.compiler.ir.visit.IIrVisitor;

public class IrLabel implements IInstruction {

    public IrLabel(int labelNum) {
        assert labelNum >= 0;
        mLabelNum = labelNum;
    }

    private final int mLabelNum;

    public int getLabelNum() {
        return mLabelNum;
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
