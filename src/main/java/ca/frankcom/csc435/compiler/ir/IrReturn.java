package ca.frankcom.csc435.compiler.ir;

import ca.frankcom.csc435.compiler.ir.visit.IIrVisitor;

import java.util.Optional;

public class IrReturn implements IInstruction {

    public IrReturn(Optional<IrTemp> value) {
        assert value != null;
        mValue = value;
    }

    private final Optional<IrTemp> mValue;

    public Optional<IrTemp> getValue() {
        return mValue;
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
