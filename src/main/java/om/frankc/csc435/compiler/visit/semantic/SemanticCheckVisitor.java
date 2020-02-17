package om.frankc.csc435.compiler.visit.semantic;

import com.google.common.collect.ImmutableList;
import om.frankc.csc435.compiler.ast.*;
import om.frankc.csc435.compiler.util.Environment;
import om.frankc.csc435.compiler.visit.IAstVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        for (FormalParameter formalParam : formalParams.getElements()) {

            final SemanticType paramType = formalParam.accept(this);
            if (paramType.equals(SemanticType.VOID)) {
                final TypeNode typeNode = formalParam.getTypeNode();
                throwException(String.format("Parameter type may not be 'void' at %s", formatPos(typeNode)));
            }

            final Identifier paramId = formalParam.getId();
            final String paramName = paramId.getText();
            if (mVariableEnvironment.containsKey(paramName)) {
                throwException(String.format("Duplicate parameter name '%s' found at %s",
                        paramName, formatPos(paramId)));
            }

            final SemanticParam param = new SemanticParam(paramType, paramName);
            semanticParams.add(param);
            mVariableEnvironment.put(paramName, paramType);
        }

        final SemanticType returnType = declaration.getTypeNode().accept(this);
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
        mVariableEnvironment.beginScope();

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

    @Override
    public SemanticType visit(IfStatement statement) {
        return null;
    }

    @Override
    public SemanticType visit(IfElseStatement statement) {
        return null;
    }

    @Override
    public SemanticType visit(WhileStatement statement) {
        return null;
    }

    @Override
    public SemanticType visit(PrintStatement statement) {
        return null;
    }

    @Override
    public SemanticType visit(PrintLineStatement statement) {
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

        final Expression expression = statement.getExpression();
        final SemanticType lhsType = result.get();
        final SemanticType rhsType = expression.accept(this);
        assert rhsType != null;

        if (lhsType.equals(rhsType) == false) {
            throwException(String.format("Type '%s' cannot be assigned to '%s' at %s", rhsType.toString(),
                    lhsType.toString(), formatPos(expression)));
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

        final Expression assignment = statement.getAssignment();
        final SemanticType rhsType = assignment.accept(this);
        assert rhsType != null;

        if (validAssignment.equals(rhsType) == false) {
            throwException(String.format("Type '%s' cannot be assigned to '%s' at %s", rhsType, lhsType,
                    formatPos(assignment)));
        }

        return null;
    }

    @Override
    public SemanticType visit(Block block) {
        return null;
    }

    @Override
    public SemanticType visit(LessThanExpression expression) {
        return null;
    }

    @Override
    public SemanticType visit(EqualityExpression expression) {
        return null;
    }

    @Override
    public SemanticType visit(AddExpression expression) {
        return null;
    }

    @Override
    public SemanticType visit(SubtractExpression expression) {
        return null;
    }

    @Override
    public SemanticType visit(MultiplyExpression expression) {
        return null;
    }

    @Override
    public SemanticType visit(ParenExpression expression) {
        return null;
    }

    @Override
    public SemanticType visit(ArrayReference reference) {
        return null;
    }

    @Override
    public SemanticType visit(FunctionCall functionCall) {
        return null;
    }

    @Override
    public SemanticType visit(ExpressionList expressionList) {
        return null;
    }

}
