package ca.frankcom.csc435.compiler.ast;

import ca.frankcom.csc435.compiler.ast.visit.IAstVisitor;

import java.util.List;

public class FormalParameterList extends NodeList<FormalParameter> {

    public FormalParameterList(List<FormalParameter> parameters,
                               int lineNumber, int linePosition) {
        super(parameters, lineNumber, linePosition);
    }

    @Override
    public <T> T accept(IAstVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
