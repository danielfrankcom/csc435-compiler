package om.frankc.csc435.compiler.ir;

import om.frankc.csc435.compiler.ir.visit.IIrVisitor;

public class PrintLn implements IInstruction {

    public PrintLn(IrTemp operand) {
        assert operand != null;
        mOperand = operand;
    }

    private final IrTemp mOperand;

    public IrTemp getOperand() {
        return mOperand;
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
