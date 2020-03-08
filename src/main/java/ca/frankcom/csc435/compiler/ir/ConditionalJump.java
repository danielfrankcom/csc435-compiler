package ca.frankcom.csc435.compiler.ir;

import ca.frankcom.csc435.compiler.ir.visit.IIrVisitor;

public class ConditionalJump implements IInstruction {

    public ConditionalJump(IrTemp condition, IrLabel jumpLabel) {
        assert condition != null;
        assert condition.getType() == IrType.Atomic.Boolean;
        assert jumpLabel != null;
        mCondition = condition;
        mJumpLabel = jumpLabel;
    }

    private final IrTemp mCondition;
    private final IrLabel mJumpLabel;

    public IrTemp getCondition() {
        return mCondition;
    }

    public IrLabel getJumpLabel() {
        return mJumpLabel;
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
