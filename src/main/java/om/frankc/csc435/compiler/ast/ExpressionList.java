package om.frankc.csc435.compiler.ast;

import java.util.List;

public class ExpressionList extends NodeList<Expression> {

    public ExpressionList(List<Expression> elements) {
        super(elements);
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

}
