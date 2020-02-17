package om.frankc.csc435.compiler.visit.semantic;

// todo: handler needs this exception on it
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
