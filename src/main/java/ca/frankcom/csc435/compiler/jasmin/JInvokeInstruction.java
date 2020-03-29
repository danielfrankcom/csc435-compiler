package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

import java.util.List;

public class JInvokeInstruction implements JInstruction {

    public JInvokeInstruction(String programName, String functionName,
                              List<JType.UniversalType> paramName, JType.ReturnType returnType) {
        assert programName != null;
        assert functionName != null;
        assert paramName != null;
        assert returnType != null;

        mProgramName = programName;
        mFunctionName = functionName;
        mParamTypes = paramName;
        mReturnType = returnType;
    }

    private final String mProgramName;
    private final String mFunctionName;
    private final List<JType.UniversalType> mParamTypes;
    private final JType.ReturnType mReturnType;

    public String getProgramName() {
        return mProgramName;
    }

    public String getFunctionName() {
        return mFunctionName;
    }

    public List<JType.UniversalType> getParamTypes() {
        return mParamTypes;
    }

    public JType.ReturnType getReturnType() {
        return mReturnType;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
