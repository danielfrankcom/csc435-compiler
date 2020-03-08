package ca.frankcom.csc435.compiler.ast;

import ca.frankcom.csc435.compiler.ast.visit.IAstVisitor;

import java.util.Objects;

public class Identifier extends Expression {

    public Identifier(String text,
                      int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
        assert text != null;
        mText = text;
    }

    private final String mText;

    public String getText() {
        return mText;
    }

    @Override
    public <T> T accept(IAstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Identifier) {
            final Identifier known = (Identifier) other;
            return super.equals(known)
                    && Objects.equals(mText, known.mText);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mText);
    }

}
