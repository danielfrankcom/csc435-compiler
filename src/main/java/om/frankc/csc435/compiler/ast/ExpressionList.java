package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.ast.visit.IAstVisitor;

import java.util.List;

public class ExpressionList extends NodeList<Expression> {

    public ExpressionList(List<Expression> elements,
                          int lineNumber, int linePosition) {
        super(elements, lineNumber, linePosition);
    }

    @Override
    public <T> T accept(IAstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
