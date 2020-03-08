package ca.frankcom.csc435.compiler.ir;

import ca.frankcom.csc435.compiler.ir.visit.IIrVisitor;

public class IrAdd extends BinaryOperation {

    public IrAdd(IrTemp result, IrTemp left, IrTemp right) {
        super(result, left, right);
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
