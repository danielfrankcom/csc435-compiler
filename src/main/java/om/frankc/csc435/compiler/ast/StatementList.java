package om.frankc.csc435.compiler.ast;

import java.util.List;

public class StatementList extends NodeList<Statement> {

    public StatementList(List<Statement> statements) {
        super(statements);
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

}
