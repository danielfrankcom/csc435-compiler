package ca.frankcom.csc435.compiler.ast;

public abstract class Expression extends AstNode {

    Expression(int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
    }

}
