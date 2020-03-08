package ca.frankcom.csc435.compiler.ir;

import ca.frankcom.csc435.compiler.ir.visit.IIrVisitor;

public class Jump implements IInstruction {

    public Jump(IrLabel jumpLabel) {
        assert jumpLabel != null;
        mJumpLabel = jumpLabel;
    }

    private final IrLabel mJumpLabel;

    public IrLabel getJumpLabel() {
        return mJumpLabel;
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
