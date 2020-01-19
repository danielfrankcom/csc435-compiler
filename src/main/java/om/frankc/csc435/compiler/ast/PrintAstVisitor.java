package om.frankc.csc435.compiler.ast;

import java.util.Iterator;

public class PrintAstVisitor implements IAstVisitor {

    @Override
    public void visit(Program program) {
        System.out.println();
        for (Function function : program.getFunctions()) {
            function.accept(this);
            System.out.println();
            System.out.println();
        }
    }

    @Override
    public void visit(Function function) {
        function.getDeclaration().accept(this);
        System.out.print(' ');
        //function.getBody().accept(this);
    }

    @Override
    public void visit(FunctionDeclaration declaration) {
        declaration.getTypeNode().accept(this);
        System.out.print(' ');
        declaration.getId().accept(this);
        System.out.print(' ');
        declaration.getParamList().accept(this);
    }

    @Override
    public void visit(ArrayType arrayType) {
        arrayType.getType().accept(this);
        System.out.print('[');
        arrayType.getSize().accept(this);
        System.out.print(']');
    }

    @Override
    public void visit(Type type) {
        System.out.print(type.getName());
    }

    @Override
    public void visit(Identifier id) {
        System.out.print(id.getText());
    }

    @Override
    public void visit(IntegerLiteral literal) {
        System.out.print(literal.getValue());
    }

    @Override
    public void visit(FormalParameterList paramList) {
        System.out.print('(');

        final Iterator<FormalParameter> params = paramList.getParameters().iterator();
        while (params.hasNext()) {
            final FormalParameter current = params.next();
            current.accept(this);

            if (params.hasNext()) {
                System.out.print(", ");
            }
        }

        System.out.print(')');
    }

    @Override
    public void visit(FormalParameter parameter) {
        parameter.getTypeNode().accept(this);
        System.out.print(' ');
        parameter.getId().accept(this);
    }

    @Override
    public void visit(FunctionBody body) {
        System.out.println("body!");
    }

}
