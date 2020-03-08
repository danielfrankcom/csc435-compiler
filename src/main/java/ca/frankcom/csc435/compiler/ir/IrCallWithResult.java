package ca.frankcom.csc435.compiler.ir;

import ca.frankcom.csc435.compiler.ir.visit.IIrVisitor;

import java.util.List;

public class IrCallWithResult extends IrCall {

    public IrCallWithResult(String functionName, List<IrTemp> params, IrTemp result) {
        super(functionName, params);
        assert result != null;
        mResult = result;
    }

    private final IrTemp mResult;

    public IrTemp getResult() {
        return mResult;
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
