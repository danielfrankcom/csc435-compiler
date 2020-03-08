package ca.frankcom.csc435.compiler.ir;

import ca.frankcom.csc435.compiler.ir.visit.IIrVisitor;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class IrProgram implements IIrNode {

    public IrProgram(String name, List<IrFunction> functions) {
        assert name != null;
        assert functions != null;
        mName = name;
        mFunctions = functions;
    }

    private final String mName;
    private final List<IrFunction> mFunctions;

    public String getName() {
        return mName;
    }

    public ImmutableList<IrFunction> getFunctions() {
        return ImmutableList.copyOf(mFunctions);
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
