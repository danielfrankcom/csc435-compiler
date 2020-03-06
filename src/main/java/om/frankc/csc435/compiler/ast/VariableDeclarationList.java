package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.ast.visit.IAstVisitor;

import java.util.List;

public class VariableDeclarationList extends NodeList<VariableDeclaration> {

    public VariableDeclarationList(List<VariableDeclaration> declarations,
                                   int lineNumber, int linePosition) {
        super(declarations, lineNumber, linePosition);
    }

    @Override
    public <T> T accept(IAstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
