package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.ast.visit.IAstVisitor;

import java.util.Objects;

/**
 * Represents the root of an abstract syntax tree.
 */
public class Program extends AstNode {

    public Program(FunctionList functions,
                   int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
        assert functions != null;
        mFunctions = functions;
    }

    private final FunctionList mFunctions;

    public FunctionList getFunctions() {
        return mFunctions;
    }

    @Override
    public <T> T accept(IAstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Program) {
            final Program known = (Program) other;
            return super.equals(known)
                    && Objects.equals(mFunctions, known.mFunctions);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mFunctions);
    }

}
