package om.frankc.csc435.compiler.ast;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;

/**
 * Represents the root of an abstract syntax tree.
 */
public class Program extends AstNode {

    public Program(FunctionList functions) {
        assert functions != null;
        mFunctions = functions;
    }

    private final FunctionList mFunctions;

    public FunctionList getFunctions() {
        return mFunctions;
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Program) {
            final Program known = (Program) other;
            return Objects.equals(mFunctions, known.mFunctions);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mFunctions);
    }

}
