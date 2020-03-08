package ca.frankcom.csc435.compiler.ir;

import ca.frankcom.csc435.compiler.ir.visit.IIrVisitor;

import java.util.List;

public class IrFunction implements IIrNode {

    public IrFunction(String name, IrType returnType, List<IrType> paramTypes,
                      List<IrTemp> declarations, List<IInstruction> instructions) {
        assert name != null;
        assert returnType != null;
        assert paramTypes != null;
        assert declarations != null;
        assert instructions != null;
        mName = name;
        mReturnType = returnType;
        mParamTypes = paramTypes;
        mDeclarations = declarations;
        mInstructions = instructions;
    }

    private final String mName;
    private final IrType mReturnType;
    private final List<IrType> mParamTypes;
    private final List<IrTemp> mDeclarations;
    private final List<IInstruction> mInstructions;

    public String getName() {
        return mName;
    }

    public IrType getReturnType() {
        return mReturnType;
    }

    public List<IrType> getParamTypes() {
        return mParamTypes;
    }

    public List<IrTemp> getDeclarations() {
        return mDeclarations;
    }

    public List<IInstruction> getInstructions() {
        return mInstructions;
    }

    @Override
    public <T> T accept(IIrVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
