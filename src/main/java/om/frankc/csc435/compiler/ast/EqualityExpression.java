package om.frankc.csc435.compiler.ast;

public class EqualityExpression extends OperatorExpression {

    public EqualityExpression(Expression leftSide, Expression rightSide,
                              int lineNumber, int linePosition) {
        super(leftSide, rightSide, lineNumber, linePosition);
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

}
