package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

import java.util.List;

public class JFunction implements IJasminNode {

    public JFunction(FunctionDeclaration declaration, LimitDirectives limitDirectives,
                     List<VarDirective> varDirectives, List<JInstruction> instructions) {
        assert declaration != null;
        assert varDirectives != null;
        assert limitDirectives != null;
        assert instructions != null;

        mFunctionDeclaration = declaration;
        mLimitDirectives = limitDirectives;
        mVarDirectives = varDirectives;
        mInstructions = instructions;
    }

    private final FunctionDeclaration mFunctionDeclaration;
    private final LimitDirectives mLimitDirectives;
    private final List<VarDirective> mVarDirectives;
    private final List<JInstruction> mInstructions;

    public FunctionDeclaration getDeclaration() {
        return mFunctionDeclaration;
    }

    public LimitDirectives getLimitDirectives() {
        return mLimitDirectives;
    }

    public List<VarDirective> getVarDirectives() {
        return mVarDirectives;
    }

    public List<JInstruction> getInstructions() {
        return mInstructions;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
