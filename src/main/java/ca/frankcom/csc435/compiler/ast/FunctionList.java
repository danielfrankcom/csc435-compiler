package ca.frankcom.csc435.compiler.ast;

import ca.frankcom.csc435.compiler.ast.visit.IAstVisitor;

import java.util.List;

public class FunctionList extends NodeList<Function> {

    public FunctionList(List<Function> functions,
                        int lineNumber, int linePosition) {
        super(functions, lineNumber, linePosition);
    }

    @Override
    public <T> T accept(IAstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
