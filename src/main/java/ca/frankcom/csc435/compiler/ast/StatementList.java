package ca.frankcom.csc435.compiler.ast;

import ca.frankcom.csc435.compiler.ast.visit.IAstVisitor;

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
