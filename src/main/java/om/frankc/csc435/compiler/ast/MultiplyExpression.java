package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.ast.visit.IAstVisitor;

public class MultiplyExpression extends OperatorExpression {

    public MultiplyExpression(Expression leftSide, Expression rightSide,
                              int lineNumber, int linePosition) {
        super(leftSide, rightSide, lineNumber, linePosition);
    }

    @Override
    public <T> T accept(IAstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
