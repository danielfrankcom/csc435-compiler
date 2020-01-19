package om.frankc.csc435.compiler.ast;

public interface IAstVisitor {

    void visit(Program program);

    void visit(Function function);

    void visit(FunctionDeclaration declaration);

    void visit(ArrayType arrayType);

    void visit(Type type);

    void visit(Identifier id);

    void visit(IntegerLiteral literal);

    void visit(FormalParameterList paramList);

    void visit(FormalParameter parameter);

    void visit(FunctionBody body);

}
