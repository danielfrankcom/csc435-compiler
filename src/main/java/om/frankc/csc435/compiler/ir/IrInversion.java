package om.frankc.csc435.compiler.ir;

import om.frankc.csc435.compiler.ir.visit.IIrVisitor;

public class IrInversion implements IInstruction {

    public IrInversion(IrTemp result, IrTemp original) {
        assert result != null;
        assert original != null;
        mResult = result;
        mOriginal = original;
    }

    private final IrTemp mResult;
    private final IrTemp mOriginal;

    public IrTemp getResult() {
        return mResult;
    }

    public IrTemp getOriginal() {
        return mOriginal;
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
