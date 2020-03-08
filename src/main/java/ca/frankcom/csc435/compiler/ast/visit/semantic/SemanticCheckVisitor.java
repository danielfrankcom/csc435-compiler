package ca.frankcom.csc435.compiler.ast.visit.semantic;

import ca.frankcom.csc435.compiler.ast.*;
import ca.frankcom.csc435.compiler.ast.visit.IAstVisitor;
import ca.frankcom.csc435.compiler.util.Environment;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.*;

public class SemanticCheckVisitor implements IAstVisitor<SemanticType> {

    private final Environment<String, SemanticType> mVariableEnvironment = new Environment<>();
    private final Environment<String, SemanticFunctionDecl> mFunctionEnvironment = new Environment<>();

    private static String formatPos(AstNode node) {
        return String.format("%d:%d", node.getLineNumber(), node.getLinePosition());
    }

    private static void throwException(String message) {
        throw new SemanticException(message);
    }

    private SemanticFunctionDecl mCurrentFunction = null;

    @Override
    public SemanticType visit(Program program) {
        final FunctionList functionList = program.getFunctions();
        final ImmutableList<Function> functions = functionList.getElements();

        boolean mainFound = false;
        Function main = null;
        for (Function function : functions) {
            final FunctionDeclaration declaration = function.getDeclaration();
            final String name = declaration.getId().getText();

            mainFound = name.equals("main");
            main = function;
            if (mainFound) {
                break;
            }
        }

        if (mainFound == false) {
            throwException(String.format("No function 'main' found at %s", formatPos(program)));
        }

        final FunctionDeclaration mainDeclaration = main.getDeclaration();

        final TypeNode mainType = mainDeclaration.getTypeNode();
        if (mainType instanceof Type == false || ((Type) mainType).getName() != Type.Name.Void) {
            throwException(String.format("Function 'main' must be of type 'void' at %s", formatPos(mainDeclaration)));
        }

        final FormalParameterList mainParams = mainDeclaration.getParamList();
        if (mainParams.size() != 0) {
            throwException(String.format("Function 'main' may not have parameters at %s", formatPos(mainParams)));
        }

        functionList.accept(this);
        return null;
    }

    @Override
    public SemanticType visit(FunctionList functions) {
        // We visit the declarations first to ensure that any
        // function calls within a function body can be checked.
        for (Function function : functions.getElements()) {
            function.getDeclaration().accept(this);
        }

        for (Function function : functions.getElements()) {
            final String name = function.getDeclaration().getId().getText();
            final Optional<SemanticFunctionDecl> declaration = mFunctionEnvironment.get(name);
            assert declaration.isPresent();

            mCurrentFunction = declaration.get();
            function.getBody().accept(this);
        }

        return null;
    }

    @Override
    public SemanticType visit(Function function) {
        final String message = "This method is not needed by this visitor and should not be called.";
        throw new UnsupportedOperationException(message);
    }

    @Override
    public SemanticType visit(FunctionDeclaration declaration) {
        final Identifier id = declaration.getId();
        final String name = id.getText();

        if (mFunctionEnvironment.containsKey(name)) {
            throwException(String.format("Duplicate function '%s' found at %s", name, formatPos(id)));
        }

        final FormalParameterList formalParams = declaration.getParamList();
        final List<SemanticParam> semanticParams = new ArrayList<>(formalParams.size());

        // We cannot use the variable environment here as all declarations
        // must be processed and checked before any function bodies.
        final Set<String> uniqueNames = new HashSet<>();

        for (FormalParameter formalParam : formalParams.getElements()) {

            final SemanticType paramType = formalParam.accept(this);
            final TypeNode typeNode = formalParam.getTypeNode();
            if (paramType.equals(SemanticType.VOID)) {
                throwException(String.format("Parameter type may not be 'void' at %s", formatPos(typeNode)));
            } else if (paramType.getName().equals(Type.Name.Void)) {
                throwException(String.format("Array type 'void' invalid at %s", formatPos(typeNode)));
            }

            final Identifier paramId = formalParam.getId();
            final String paramName = paramId.getText();
            if (uniqueNames.contains(paramName)) {
                throwException(String.format("Duplicate parameter name '%s' found at %s",
                        paramName, formatPos(paramId)));
            }

            final SemanticParam param = new SemanticParam(paramType, paramName);
            semanticParams.add(param);
            uniqueNames.add(paramName);
        }

        final TypeNode typeNode = declaration.getTypeNode();
        final SemanticType returnType = typeNode.accept(this);
        if (returnType.isArray() && returnType.getName().equals(Type.Name.Void)) {
            throwException(String.format("Array type 'void' invalid at %s", formatPos(typeNode)));
        }

        final SemanticFunctionDecl mapValue = new SemanticFunctionDecl(returnType, name, semanticParams);
        mFunctionEnvironment.put(name, mapValue);

        return null;
    }

    @Override
    public SemanticType visit(ArrayType arrayType) {
        final Type.Name typeName = arrayType.getType().getName();
        final int numElements = arrayType.getSize().getValue();
        return SemanticType.forArray(typeName, numElements);
    }

    @Override
    public SemanticType visit(Type type) {
        return SemanticType.forName(type.getName());
    }

    @Override
    public SemanticType visit(Identifier id) {
        final String name = id.getText();
        final Optional<SemanticType> result = mVariableEnvironment.get(name);

        if (result.isPresent() == false) {
            throwException(String.format("Cannot access undeclared variable '%s' at %s", name, formatPos(id)));
        }

        return result.get();
    }

    @Override
    public SemanticType visit(IntegerLiteral literal) {
        return SemanticType.INT;
    }

    @Override
    public SemanticType visit(StringLiteral literal) {
        return SemanticType.STRING;
    }

    @Override
    public SemanticType visit(FloatLiteral literal) {
        return SemanticType.FLOAT;
    }

    @Override
    public SemanticType visit(CharacterLiteral literal) {
        return SemanticType.CHAR;
    }

    @Override
    public SemanticType visit(BooleanLiteral literal) {
        return SemanticType.BOOL;
    }

    @Override
    public SemanticType visit(FormalParameterList paramList) {
        final String message = "This method is not needed by this visitor and should not be called.";
        throw new UnsupportedOperationException(message);
    }

    @Override
    public SemanticType visit(FormalParameter parameter) {
        return parameter.getTypeNode().accept(this);
    }

    @Override
    public SemanticType visit(FunctionBody body) {
        assert mCurrentFunction != null;

        mVariableEnvironment.beginScope();

        for (SemanticParam param : mCurrentFunction.getParams()) {
            final String name = param.getName();
            final SemanticType type = param.getType();
            assert mVariableEnvironment.containsKey(param.getName()) == false;
            mVariableEnvironment.put(name, type);
        }

        body.getDeclarations().accept(this);
        body.getStatements().accept(this);

        mVariableEnvironment.endScope();

        return null;
    }

    @Override
    public SemanticType visit(VariableDeclarationList declarationList) {
        final ImmutableList<VariableDeclaration> declarations = declarationList.getElements();

        for (VariableDeclaration declaration : declarations) {
            final Identifier id = declaration.getId();
            final String name = declaration.getId().getText();
            if (mVariableEnvironment.containsKey(name)) {
                throwException(String.format("Variable '%s' may not hide parameter at %s", name, formatPos(id)));
            }
        }

        for (VariableDeclaration declaration : declarations) {
            declaration.accept(this);
        }

        return null;
    }

    @Override
    public SemanticType visit(VariableDeclaration declaration) {
        final Identifier id = declaration.getId();
        final String name = id.getText();
        if (mVariableEnvironment.containsKey(name)) {
            throwException(String.format("Duplicate variable named '%s' declared at %s", name, formatPos(id)));
        }

        final TypeNode typeNode = declaration.getTypeNode();
        final SemanticType type = typeNode.accept(this);
        if (type.equals(SemanticType.VOID)) {
            throwException(String.format("Variable '%s' may not use 'void' type at %s", name, formatPos(typeNode)));
        } else if (type.getName().equals(Type.Name.Void)) {
            throwException(String.format("Array type 'void' invalid at %s", formatPos(typeNode)));
        }

        mVariableEnvironment.put(name, type);

        return null;
    }

    @Override
    public SemanticType visit(StatementList statements) {
        assert mCurrentFunction != null;

        for (Statement statement : statements.getElements()) {
            statement.accept(this);
        }

        return null;
    }

    @Override
    public SemanticType visit(ExpressionStatement statement) {
        statement.getExpression().accept(this);
        return null;
    }

    private void validateBooleanCondition(Expression expression) {
        final SemanticType type = expression.accept(this);
        if (type.equals(SemanticType.BOOL) == false) {
            throwException(String.format("Non-boolean condition ('%s') provided to statement at %s", type,
                    formatPos(expression)));
        }
    }

    @Override
    public SemanticType visit(IfStatement statement) {
        validateBooleanCondition(statement.getExpression());
        statement.getIfBlock().accept(this);

        return null;
    }

    @Override
    public SemanticType visit(IfElseStatement statement) {
        validateBooleanCondition(statement.getExpression());
        statement.getIfBlock().accept(this);
        statement.getElseBlock().accept(this);

        return null;
    }

    @Override
    public SemanticType visit(WhileStatement statement) {
        validateBooleanCondition(statement.getExpression());
        statement.getBlock().accept(this);

        return null;
    }

    private void validatePrintStatement(Expression expression, String statementType) {
        final SemanticType type = expression.accept(this);
        final boolean typeValid = type.equals(SemanticType.INT)
                || type.equals(SemanticType.FLOAT)
                || type.equals(SemanticType.CHAR)
                || type.equals(SemanticType.STRING)
                || type.equals(SemanticType.BOOL);

        if (typeValid == false) {
            throwException(String.format("Invalid type '%s' provided to %s statement at %s", type, statementType,
                    formatPos(expression)));
        }
    }

    @Override
    public SemanticType visit(PrintStatement statement) {
        validatePrintStatement(statement.getExpression(), "print");
        return null;
    }

    @Override
    public SemanticType visit(PrintLineStatement statement) {
        validatePrintStatement(statement.getExpression(), "println");
        return null;
    }

    @Override
    public SemanticType visit(ReturnStatement statement) {
        assert mCurrentFunction != null;
        final String name = mCurrentFunction.getName();
        final SemanticType expectedType = mCurrentFunction.getReturnType();

        final Optional<Expression> result = statement.getExpression();
        final boolean correct;
        if (result.isPresent()) {
            final Expression expression = result.get();
            final SemanticType returnType = expression.accept(this);
            assert returnType != null;
            correct = returnType.equals(expectedType);
        } else {
            correct = expectedType.equals(SemanticType.VOID);
        }

        if (correct == false) {
            throwException(String.format("Function '%s' requires return type '%s' at %s", name,
                    expectedType, formatPos(statement)));
        }
        return null;
    }

    @Override
    public SemanticType visit(AssignmentStatement statement) {
        final String name = statement.getId().getText();
        final Optional<SemanticType> result = mVariableEnvironment.get(name);
        if (result.isPresent() == false) {
            throwException(String.format("Variable '%s' not declared prior to assignment at %s", name, formatPos(statement)));
        }

        final SemanticType lhsType = result.get();
        final SemanticType rhsType = statement.getExpression().accept(this);
        assert rhsType != null;

        if (lhsType.equals(rhsType) == false) {
            throwException(String.format("Type '%s' cannot be assigned to '%s' at %s", rhsType.toString(),
                    lhsType.toString(), formatPos(statement)));
        }

        return null;
    }

    @Override
    public SemanticType visit(ArrayAssignment statement) {
        final String name = statement.getId().getText();
        final Optional<SemanticType> result = mVariableEnvironment.get(name);
        if (result.isPresent() == false) {
            throwException(String.format("Variable '%s' not declared prior to assignment at %s", name, formatPos(statement)));
        }

        final Expression index = statement.getIndex();
        final SemanticType indexType = index.accept(this);
        if (indexType.equals(SemanticType.INT) == false) {
            throwException(String.format("Array index must be integer at %s", formatPos(index)));
        }

        final SemanticType lhsType = result.get();
        if (lhsType.isArray() == false) {
            throwException(String.format("Type '%s' may not be indexed at %s", lhsType, formatPos(index)));
        }

        final Type.Name lhsTypeName = lhsType.getName();
        final SemanticType validAssignment = SemanticType.forName(lhsTypeName);

        final SemanticType rhsType = statement.getAssignment().accept(this);
        assert rhsType != null;

        if (validAssignment.equals(rhsType) == false) {
            throwException(String.format("Type '%s' cannot be assigned to '%s' at %s", rhsType, lhsType,
                    formatPos(statement)));
        }

        return null;
    }

    @Override
    public SemanticType visit(Block block) {
        block.getStatements().accept(this);
        return null;
    }

    private SemanticType checkBooleanOperator(OperatorExpression expression,
                                              ImmutableSet<SemanticType> supportedTypes) {
        final Expression leftExpression = expression.getLeftSide();
        final SemanticType leftType = leftExpression.accept(this);

        final Expression rightExpression = expression.getRightSide();
        final SemanticType rightType = rightExpression.accept(this);

        if (leftType.equals(rightType) == false) {
            throwException(String.format("Attempt to compare incomparable types '%s' and '%s' at %s", leftType,
                    rightType, formatPos(expression)));
        }

        if (supportedTypes.contains(leftType) == false) {
            throwException(String.format("May not perform comparison with type '%s' at %s", leftType,
                    formatPos(expression)));
        }

        return SemanticType.BOOL;
    }

    @Override
    public SemanticType visit(EqualityExpression expression) {
        final ImmutableSet<SemanticType> supportedTypes = ImmutableSet.of(
                SemanticType.INT,
                SemanticType.FLOAT,
                SemanticType.CHAR,
                SemanticType.STRING,
                SemanticType.BOOL
        );
        return checkBooleanOperator(expression, supportedTypes);
    }

    @Override
    public SemanticType visit(LessThanExpression expression) {
        final ImmutableSet<SemanticType> supportedTypes = ImmutableSet.of(
                SemanticType.INT,
                SemanticType.FLOAT,
                SemanticType.CHAR,
                SemanticType.STRING,
                SemanticType.BOOL
        );
        return checkBooleanOperator(expression, supportedTypes);
    }

    private SemanticType checkTypedOperator(OperatorExpression expression,
                                            ImmutableSet<SemanticType> supportedTypes) {
        final Expression leftExpression = expression.getLeftSide();
        final SemanticType leftType = leftExpression.accept(this);

        final Expression rightExpression = expression.getRightSide();
        final SemanticType rightType = rightExpression.accept(this);

        if (leftType.equals(rightType) == false) {
            throwException(String.format("Attempt to operate on non-identical types '%s' and '%s' at %s", leftType,
                    rightType, formatPos(expression)));
        }

        if (supportedTypes.contains(leftType) == false) {
            throwException(String.format("May not perform operation with type '%s' at %s", leftType,
                    formatPos(expression)));
        }

        return leftType;
    }

    @Override
    public SemanticType visit(AddExpression expression) {
        final ImmutableSet<SemanticType> supportedTypes = ImmutableSet.of(
                SemanticType.INT,
                SemanticType.FLOAT,
                SemanticType.CHAR,
                SemanticType.STRING
        );
        return checkTypedOperator(expression, supportedTypes);
    }

    @Override
    public SemanticType visit(SubtractExpression expression) {
        final ImmutableSet<SemanticType> supportedTypes = ImmutableSet.of(
                SemanticType.INT,
                SemanticType.FLOAT,
                SemanticType.CHAR
        );
        return checkTypedOperator(expression, supportedTypes);
    }

    @Override
    public SemanticType visit(MultiplyExpression expression) {
        final ImmutableSet<SemanticType> supportedTypes = ImmutableSet.of(
                SemanticType.INT,
                SemanticType.FLOAT
        );
        return checkTypedOperator(expression, supportedTypes);
    }

    @Override
    public SemanticType visit(ParenExpression expression) {
        return expression.getExpression().accept(this);
    }

    @Override
    public SemanticType visit(ArrayReference reference) {
        final String name = reference.getId().getText();
        final Optional<SemanticType> result = mVariableEnvironment.get(name);

        if (result.isPresent() == false) {
            throwException(String.format("Attempt to access undefined array '%s' at %s", name, formatPos(reference)));
        }

        final SemanticType idType = result.get();
        if (idType.isArray() == false) {
            throwException(String.format("Cannot index non-array type '%s' at %s", idType, formatPos(reference)));
        }

        final Expression expression = reference.getExpression();
        final SemanticType indexType = expression.accept(this);
        if (indexType.equals(SemanticType.INT) == false) {
            throwException(String.format("Array index must be of type 'int' at %s", formatPos(expression)));
        }

        return SemanticType.forName(idType.getName());
    }

    @Override
    public SemanticType visit(FunctionCall functionCall) {
        final String name = functionCall.getId().getText();

        final Optional<SemanticFunctionDecl> result = mFunctionEnvironment.get(name);
        if (result.isPresent() == false) {
            throwException(String.format("Attempt to call undefined function '%s' at %s", name, formatPos(functionCall)));
        }

        final SemanticFunctionDecl function = result.get();
        final ImmutableList<SemanticParam> expectedParams = function.getParams();

        final ExpressionList actualArgList = functionCall.getExpressions();
        final ImmutableList<Expression> actualParams = actualArgList.getElements();

        for (int paramNum = 1; paramNum <= expectedParams.size(); paramNum++) {
            final int index = paramNum - 1;
            final SemanticParam expected = expectedParams.get(index);

            if (actualParams.size() < paramNum) {
                throwException(String.format("Parameter '%s' (#%d) missing at %s", expected.getName(), paramNum,
                        formatPos(functionCall)));
            }

            final Expression actual = actualParams.get(index);
            final SemanticType actualType = actual.accept(this);

            if (expected.getType().equals(actualType) == false) {
                throwException(String.format("Parameter '%s' (#%d) expects type '%s', not '%s' at %s",
                        expected.getName(), paramNum, expected.getType(), actualType, formatPos(functionCall)));
            }
        }

        final int actualNumParams = actualArgList.size();
        final int expectedNumParams = expectedParams.size();
        if (expectedNumParams < actualNumParams) {
            final Expression firstExtra = actualParams.get(expectedNumParams);
            final SemanticType extraType = firstExtra.accept(this);
            throwException(String.format("Parameter of type '%s' unexpected (too many) at %s", extraType,
                    formatPos(firstExtra)));
        }

        return function.getReturnType();
    }

    @Override
    public SemanticType visit(ExpressionList expressionList) {
        final String message = "This method is not needed by this visitor and should not be called.";
        throw new UnsupportedOperationException(message);
    }

}
