package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class Identifier extends Expression {

    public Identifier(String text) {
        assert text != null;
        mText = text;
    }

    private final String mText;

    public String getText() {
        return mText;
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Identifier) {
            final Identifier known = (Identifier) other;
            return Objects.equals(mText, known.mText);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mText);
    }

}
