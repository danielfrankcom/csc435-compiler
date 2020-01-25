package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class CharacterLiteral extends Literal {

    public CharacterLiteral(char value,
                            int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
        mValue = value;
    }

    private final char mValue;

    public char getValue() {
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
        if (other instanceof CharacterLiteral) {
            final CharacterLiteral known = (CharacterLiteral) other;
            return super.equals(known)
                    && Objects.equals(mValue, known.mValue);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mValue);
    }

}
