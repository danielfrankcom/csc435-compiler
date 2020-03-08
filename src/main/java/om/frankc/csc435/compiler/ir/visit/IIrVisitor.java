package om.frankc.csc435.compiler.ir.visit;

import om.frankc.csc435.compiler.ir.*;

public interface IIrVisitor<T> {

    T visit(IrProgram program);

    T visit(IrFunction function);

    T visit(IrTemp temporary);

    T visit(IrType type);

    T visit(IrLabel instruction);

    T visit(Jump instruction);

    T visit(ConditionalJump instruction);

    T visit(IrInitializeArray instruction);

    T visit(IrArrayAssignment instruction);

    T visit(IrArrayAccess instruction);

    T visit(ConstantAssignment instruction);

    T visit(VariableAssignment instruction);

    T visit(Print instruction);

    T visit(PrintLn instruction);

    T visit(IrCall instruction);

    T visit(IrCallWithResult instruction);

    T visit(IrReturn instruction);

    T visit(IrEquality instruction);

    T visit(IrLessThan instruction);

    T visit(IrAdd instruction);

    T visit(IrSubtract instruction);

    T visit(IrMultiply instruction);

    T visit(IrInversion instruction);

}
