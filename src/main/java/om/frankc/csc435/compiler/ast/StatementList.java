package om.frankc.csc435.compiler.ast;

import om.frankc.csc435.compiler.visit.IAstVisitor;

import java.util.List;

public class StatementList extends NodeList<Statement> {

    public StatementList(List<Statement> statements,
                         int lineNumber, int linePosition) {
        super(statements, lineNumber, linePosition);
    }

    @Override
    public <T> T accept(IAstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
