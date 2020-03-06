package om.frankc.csc435.compiler.ir;

import om.frankc.csc435.compiler.ir.visit.IIrVisitor;

public class IrLabel implements IIrNode {

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
