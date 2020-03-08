package ca.frankcom.csc435.compiler.ast.visit.semantic;

public class SemanticException extends RuntimeException {

    SemanticException(String message) {
        mMessage = message;
    }

    final String mMessage;

    @Override
    public String toString() {
        return mMessage;
    }
}
