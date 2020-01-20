package om.frankc.csc435.compiler.ast;

public class MultiplyExpression extends OperatorExpression {

    public MultiplyExpression(Expression leftSide, Expression rightSide) {
        super(leftSide, rightSide);
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

}
