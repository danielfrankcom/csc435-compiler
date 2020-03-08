package ca.frankcom.csc435.compiler.ir;

import ca.frankcom.csc435.compiler.ir.visit.IIrVisitor;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class IrCall implements IInstruction {

    public IrCall(String functionName, List<IrTemp> params) {
        assert functionName != null;
        assert params != null;
        mFunctionName = functionName;
        mParams = params;
    }

    private final String mFunctionName;
    private final List<IrTemp> mParams;

    public String getFunctionName() {
        return mFunctionName;
    }

    public ImmutableList<IrTemp> getParams() {
        return ImmutableList.copyOf(mParams);
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
