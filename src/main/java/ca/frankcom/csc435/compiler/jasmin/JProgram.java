package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

import java.util.List;

public class JProgram implements IJasminNode {

    public JProgram(String name, List<JFunction> functions) {
        assert name != null;
        assert functions != null;

        mName = name;
        mFunctions = functions;
    }

    private final String mName;
    private final List<JFunction> mFunctions;

    public String getName() {
        return mName;
    }

    public List<JFunction> getFunctions() {
        return mFunctions;
    }

    @Override
    public <T> T accept(IJasminVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
