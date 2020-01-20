package om.frankc.csc435.compiler.ast;

public class SubtractExpression extends OperatorExpression {

    public SubtractExpression(Expression leftSide, Expression rightSide) {
        super(leftSide, rightSide);
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

}
