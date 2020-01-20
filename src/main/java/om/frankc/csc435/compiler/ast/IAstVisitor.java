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

    void visit(IfStatement statement);

    void visit(IfElseStatement statement);

    void visit(WhileStatement statement);

    void visit(PrintStatement statement);

    void visit(PrintLineStatement statement);

    void visit(ReturnStatement statement);

    void visit(AssignmentStatement statement);

    void visit(ArrayAssignment assignment);

    void visit(Block block);

    void visit(LessThanExpression expression);

    void visit(EqualityExpression expression);

    void visit(AddExpression expression);

    void visit(SubtractExpression expression);

    void visit(MultiplyExpression expression);

    void visit(ParenExpression expression);

    void visit(ArrayReference reference);

    void visit(FunctionCall functionCall);

    void visit(ExpressionList expressionList);

}
