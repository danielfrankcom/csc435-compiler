package om.frankc.csc435.compiler.ast;

public class LessThanExpression extends OperatorExpression {

    public LessThanExpression(Expression leftSide, Expression rightSide,
                              int lineNumber, int linePosition) {
        super(leftSide, rightSide, lineNumber, linePosition);
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

}
