package om.frankc.csc435.compiler.ast;

public abstract class AstNode {

    public abstract void accept(IAstVisitor visitor);

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract int hashCode();

}
