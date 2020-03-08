package ca.frankcom.csc435.compiler.ast;

public abstract class Statement extends AstNode {

    Statement(int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
    }

}
