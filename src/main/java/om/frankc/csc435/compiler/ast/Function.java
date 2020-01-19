package om.frankc.csc435.compiler.ast;

import java.util.Objects;

public class Function extends AstNode {

    public Function(FunctionDeclaration declaration, FunctionBody body) {
        assert declaration != null;
        assert body != null;

        mDeclaration = declaration;
        mBody = body;
    }

    private final FunctionDeclaration mDeclaration;
    private final FunctionBody mBody;

    public FunctionDeclaration getDeclaration() {
        return mDeclaration;
    }

    public FunctionBody getBody() {
        return mBody;
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
        if (other instanceof Function) {
            final Function known = (Function) other;
            return Objects.equals(mDeclaration, known.mDeclaration)
                    && Objects.equals(mBody, known.mBody);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mDeclaration, mBody);
    }

}
