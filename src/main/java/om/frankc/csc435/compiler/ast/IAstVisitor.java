package om.frankc.csc435.compiler.ast;

public interface IAstVisitor {

    void visit(Program program);

    void visit(FunctionList functions);

    void visit(Function function);

    void visit(FunctionDeclaration declaration);

    void visit(ArrayType arrayType);

    void visit(Type type);

    void visit(Identifier id);

    void visit(IntegerLiteral literal);

    void visit(StringLiteral literal);

    void visit(FloatLiteral literal);

    void visit(CharacterLiteral literal);

    void visit(BooleanLiteral literal);

    void visit(FormalParameterList paramList);

    void visit(FormalParameter parameter);

    void visit(FunctionBody body);

    void visit(VariableDeclarationList declarations);

    void visit(VariableDeclaration declaration);

    void visit(StatementList statements);

    void visit(ExpressionStatement statement);

    void visit(WhileStatement statement);

    void visit(Block block);

    void visit(ExpressionList expressionList);

}
