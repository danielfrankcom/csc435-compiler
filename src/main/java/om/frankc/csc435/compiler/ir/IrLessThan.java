package om.frankc.csc435.compiler.ir;

import om.frankc.csc435.compiler.ir.visit.IIrVisitor;

public class IrLessThan extends BinaryOperation {

    public IrLessThan(IrTemp result, IrTemp left, IrTemp right) {
        super(result, left, right);
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
