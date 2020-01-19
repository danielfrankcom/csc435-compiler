package om.frankc.csc435.compiler.ast;

import java.util.List;

public class VariableDeclarationList extends NodeList<VariableDeclaration> {

    public VariableDeclarationList(List<VariableDeclaration> declarations) {
        super(declarations);
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

}
