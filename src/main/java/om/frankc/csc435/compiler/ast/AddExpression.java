package om.frankc.csc435.compiler.ast;

public class AddExpression extends OperatorExpression {

    public AddExpression(Expression leftSide, Expression rightSide) {
        super(leftSide, rightSide);
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

}
