package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.ast.visit.IAstVisitor;

import java.util.Objects;

public class Function extends AstNode {

    public Function(FunctionDeclaration declaration, FunctionBody body,
                    int lineNumber, int linePosition) {
        super(lineNumber, linePosition);
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
    public <T> T accept(IAstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Function) {
            final Function known = (Function) other;
            return super.equals(known)
                    && Objects.equals(mDeclaration, known.mDeclaration)
                    && Objects.equals(mBody, known.mBody);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mDeclaration, mBody);
    }

}
