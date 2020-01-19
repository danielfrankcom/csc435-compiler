package om.frankc.csc435.compiler.ast;

import java.util.List;

public class FunctionList extends NodeList<Function> {

    public FunctionList(List<Function> functions) {
        super(functions);
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

}
