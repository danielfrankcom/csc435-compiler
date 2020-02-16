package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.visit.IAstVisitor;

public class SubtractExpression extends OperatorExpression {

    public SubtractExpression(Expression leftSide, Expression rightSide,
                              int lineNumber, int linePosition) {
        super(leftSide, rightSide, lineNumber, linePosition);
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

}
