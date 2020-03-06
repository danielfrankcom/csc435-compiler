package om.frankc.csc435.compiler.ir.visit;

import om.frankc.csc435.compiler.ir.*;

public interface IIrVisitor<T> {

    T visit(IrProgram program);

    T visit(IrFunction function);

    T visit(IrTemp temporary);

    T visit(IrType type);

    T visit(IrLabel label);

    T visit(ConstantAssignment assignment);

    T visit(VariableAssignment assignment);

    T visit(Print statement);

    T visit(PrintLn statement);

}
