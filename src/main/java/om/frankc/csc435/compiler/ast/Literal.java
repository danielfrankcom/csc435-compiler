package om.frankc.csc435.compiler.ast;

public abstract class Literal extends Expression {

    Literal(int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
    }

}
