package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class StringLiteral extends Literal {

    public StringLiteral(String value) {
        assert value != null;
        mValue = value;
    }

    private final String mValue;

    public String getValue() {
        return mValue;
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
        if (other instanceof StringLiteral) {
            final StringLiteral known = (StringLiteral) other;
            return Objects.equals(mValue, known.mValue);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mValue);
    }

}
