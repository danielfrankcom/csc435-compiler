package om.frankc.csc435.compiler.visit;

import om.frankc.csc435.compiler.ast.*;

public interface IAstVisitor<T> {

    T visit(Program program);

    T visit(FunctionList functions);

    T visit(Function function);

    T visit(FunctionDeclaration declaration);

    T visit(ArrayType arrayType);

    T visit(Type type);

    T visit(Identifier id);

    T visit(IntegerLiteral literal);

    T visit(StringLiteral literal);

    T visit(FloatLiteral literal);

    T visit(CharacterLiteral literal);

    T visit(BooleanLiteral literal);

    T visit(FormalParameterList paramList);

    T visit(FormalParameter parameter);

    T visit(FunctionBody body);

    T visit(VariableDeclarationList declarations);

    T visit(VariableDeclaration declaration);

    T visit(StatementList statements);

    T visit(ExpressionStatement statement);

    T visit(IfStatement statement);

    T visit(IfElseStatement statement);

    T visit(WhileStatement statement);

    T visit(PrintStatement statement);

    T visit(PrintLineStatement statement);

    T visit(ReturnStatement statement);

    T visit(AssignmentStatement statement);

    T visit(ArrayAssignment assignment);

    T visit(Block block);

    T visit(LessThanExpression expression);

    T visit(EqualityExpression expression);

    T visit(AddExpression expression);

    T visit(SubtractExpression expression);

    T visit(MultiplyExpression expression);

    T visit(ParenExpression expression);

    T visit(ArrayReference reference);

    T visit(FunctionCall functionCall);

    T visit(ExpressionList expressionList);

}
