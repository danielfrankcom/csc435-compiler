package om.frankc.csc435.compiler.ast;

import java.util.List;

public class FunctionList extends NodeList<Function> {

    public FunctionList(List<Function> functions,
                        int lineNumber, int linePosition) {
        super(functions, lineNumber, linePosition);
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

}
