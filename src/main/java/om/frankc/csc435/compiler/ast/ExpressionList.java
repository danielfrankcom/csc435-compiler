package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.visit.IAstVisitor;

import java.util.List;

public class ExpressionList extends NodeList<Expression> {

    public ExpressionList(List<Expression> elements,
                          int lineNumber, int linePosition) {
        super(elements, lineNumber, linePosition);
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

}
