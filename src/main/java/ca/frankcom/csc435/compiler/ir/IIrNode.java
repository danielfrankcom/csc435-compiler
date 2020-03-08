package ca.frankcom.csc435.compiler.ir;

import ca.frankcom.csc435.compiler.ir.visit.IIrVisitor;

public interface IIrNode {

    <T> T accept(IIrVisitor<T> visitor);

}
