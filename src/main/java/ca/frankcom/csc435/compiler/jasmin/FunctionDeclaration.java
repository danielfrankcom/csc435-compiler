package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

import java.util.List;

public class FunctionDeclaration implements IJasminNode {

    public FunctionDeclaration(String name, List<JType.UniversalType> paramTypes, JType.ReturnType returnType) {
        assert name != null;
        assert paramTypes != null;
        assert returnType != null;

        mName = name;
        mParamTypes = paramTypes;
        mReturnType = returnType;
    }

    private final String mName;
    private final List<JType.UniversalType> mParamTypes;
    private final JType.ReturnType mReturnType;

    public String getName() {
        return mName;
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
