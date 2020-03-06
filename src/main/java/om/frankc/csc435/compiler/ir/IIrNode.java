package om.frankc.csc435.compiler.ir;

import om.frankc.csc435.compiler.ir.visit.IIrVisitor;

public interface IIrNode {

    <T> T accept(IIrVisitor<T> visitor);

}
