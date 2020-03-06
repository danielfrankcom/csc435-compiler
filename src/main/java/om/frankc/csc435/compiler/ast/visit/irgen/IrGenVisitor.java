package om.frankc.csc435.compiler.ast.visit.irgen;

import com.google.common.collect.ImmutableList;
import om.frankc.csc435.compiler.ast.*;
import om.frankc.csc435.compiler.ast.visit.IAstVisitor;
import om.frankc.csc435.compiler.ir.*;
import om.frankc.csc435.compiler.util.Environment;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class IrGenVisitor implements IAstVisitor<IrTemp> {

    private static IrType convertType(TypeNode typeNode) {
        if (typeNode instanceof Type) {
            final Type type = (Type) typeNode;

            assert Type.Name.values().length == 6;
            switch (type.getName()) {
                case Void:
                    return IrType.VOID;
                case Boolean:
                    return IrType.BOOLEAN;
                case String:
                    return IrType.STRING;
                case Integer:
                    return IrType.INTEGER;
                case Character:
                    return IrType.CHARACTER;
                case FloatingPoint:
                    return IrType.FLOATING_POINT;
                default:
                    final String message = "Unexpected enum value";
                    throw new IllegalArgumentException(message);
            }

        } else {
            assert typeNode instanceof ArrayType;
            final ArrayType arrayType = (ArrayType) typeNode;
            final TypeNode elementType = arrayType.getType();
            return IrType.arrayOf(convertType(elementType));
        }
    }

    public IrGenVisitor(String programName) {
        assert programName != null;
        mProgramName = programName;
    }

    private final String mProgramName;

    private final Environment<String, IrTemp> mVariableEnvironment = new Environment<>();
    private final List<IrFunction> mFunctions = new LinkedList<>();

    private TemporaryFactory mTempFactory = null;
    private List<IInstruction> mInstructionList = null;
    private IrProgram mProgram = null;

    public IrProgram getProgram() {
        return mProgram;
    }

    @Override
    public IrTemp visit(Program program) {
        final FunctionList functionList = program.getFunctions();
        functionList.accept(this);

        mProgram = new IrProgram(mProgramName, mFunctions);

        return null;
    }

    @Override
    public IrTemp visit(FunctionList functions) {
        for (Function function : functions.getElements()) {
            function.accept(this);
        }
        return null;
    }

    @Override
    public IrTemp visit(Function function) {
        mTempFactory = new TemporaryFactory();
        mInstructionList = new LinkedList<>();
        mVariableEnvironment.beginScope();

        function.getDeclaration().accept(this);
        function.getBody().accept(this);

        final FunctionDeclaration declaration = function.getDeclaration();
        final String functionName = declaration.getId().getText();
        final IrType returnType = convertType(declaration.getTypeNode());

        final FormalParameterList paramList = declaration.getParamList();
        final List<IrType> paramTypes = new LinkedList<>();
        for (FormalParameter param : paramList.getElements()) {
            final IrType type = convertType(param.getTypeNode());
            paramTypes.add(type);
        }

        final ImmutableList<IrTemp> createdTemps = mTempFactory.getReturned();
        final IrFunction irFunction = new IrFunction(functionName, returnType, paramTypes,
                createdTemps, mInstructionList);
        mFunctions.add(irFunction);

        mVariableEnvironment.endScope();
        mInstructionList = null;
        mTempFactory = null;
        return null;
    }

    @Override
    public IrTemp visit(FunctionDeclaration declaration) {
        declaration.getParamList().accept(this);
        return null;
    }

    @Override
    public IrTemp visit(ArrayType arrayType) {
        final String message = "There is no IR to generate for this statement";
        throw new UnsupportedOperationException(message);
    }

    @Override
    public IrTemp visit(Type type) {
        final String message = "There is no IR to generate for this statement";
        throw new UnsupportedOperationException(message);
    }

    @Override
    public IrTemp visit(Identifier id) {
        final Optional<IrTemp> tempBox = mVariableEnvironment.get(id.getText());
        assert tempBox.isPresent();
        return tempBox.get();
    }

    @Override
    public IrTemp visit(IntegerLiteral literal) {
        final IrTemp temp = mTempFactory.get(IrType.INTEGER, IrTemp.Category.TEMPORARY);
        final IInstruction instruction = new ConstantAssignment(temp, literal.getValue());
        mInstructionList.add(instruction);
        return temp;
    }

    @Override
    public IrTemp visit(StringLiteral literal) {
        final IrTemp temp = mTempFactory.get(IrType.STRING, IrTemp.Category.TEMPORARY);
        final IInstruction instruction = new ConstantAssignment(temp, literal.getValue());
        mInstructionList.add(instruction);
        return temp;
    }

    @Override
    public IrTemp visit(FloatLiteral literal) {
        final IrTemp temp = mTempFactory.get(IrType.FLOATING_POINT, IrTemp.Category.TEMPORARY);
        final IInstruction instruction = new ConstantAssignment(temp, literal.getValue());
        mInstructionList.add(instruction);
        return temp;
    }

    @Override
    public IrTemp visit(CharacterLiteral literal) {
        final IrTemp temp = mTempFactory.get(IrType.CHARACTER, IrTemp.Category.TEMPORARY);
        final IInstruction instruction = new ConstantAssignment(temp, literal.getValue());
        mInstructionList.add(instruction);
        return temp;
    }

    @Override
    public IrTemp visit(BooleanLiteral literal) {
        final IrTemp temp = mTempFactory.get(IrType.BOOLEAN, IrTemp.Category.TEMPORARY);
        final IInstruction instruction = new ConstantAssignment(temp, literal.getValue());
        mInstructionList.add(instruction);
        return temp;
    }

    @Override
    public IrTemp visit(FormalParameterList paramList) {
        for (FormalParameter param : paramList.getElements()) {
            param.accept(this);
        }
        return null;
    }

    @Override
    public IrTemp visit(FormalParameter parameter) {
        final String name = parameter.getId().getText();
        final IrType type = convertType(parameter.getTypeNode());
        final IrTemp temp = mTempFactory.get(type, IrTemp.Category.PARAMETER);
        mVariableEnvironment.put(name, temp);

        return null;
    }

    @Override
    public IrTemp visit(FunctionBody body) {
        body.getDeclarations().accept(this);
        body.getStatements().accept(this);

        return null;
    }

    @Override
    public IrTemp visit(VariableDeclarationList declarations) {
        for (VariableDeclaration declaration : declarations.getElements()) {
            declaration.accept(this);
        }
        return null;
    }

    @Override
    public IrTemp visit(VariableDeclaration declaration) {
        final String name = declaration.getId().getText();
        final IrType type = convertType(declaration.getTypeNode());
        final IrTemp temp = mTempFactory.get(type, IrTemp.Category.LOCAL);
        mVariableEnvironment.put(name, temp);

        return null;
    }

    @Override
    public IrTemp visit(StatementList statements) {
        for (Statement statement : statements.getElements()) {
            statement.accept(this);
        }
        return null;
    }

    @Override
    public IrTemp visit(ExpressionStatement statement) {
        return null;
    }

    @Override
    public IrTemp visit(IfStatement statement) {
        return null;
    }

    @Override
    public IrTemp visit(IfElseStatement statement) {
        return null;
    }

    @Override
    public IrTemp visit(WhileStatement statement) {
        return null;
    }

    @Override
    public IrTemp visit(PrintStatement statement) {
        final IrTemp operand = statement.getExpression().accept(this);
        final IInstruction instruction = new Print(operand);
        mInstructionList.add(instruction);

        return null;
    }

    @Override
    public IrTemp visit(PrintLineStatement statement) {
        final IrTemp operand = statement.getExpression().accept(this);
        final IInstruction instruction = new PrintLn(operand);
        mInstructionList.add(instruction);

        return null;
    }

    @Override
    public IrTemp visit(ReturnStatement statement) {
        return null;
    }

    @Override
    public IrTemp visit(AssignmentStatement statement) {
        final IrTemp rightSide = statement.getExpression().accept(this);

        final String name = statement.getId().getText();
        final Optional<IrTemp> leftSideBox = mVariableEnvironment.get(name);
        assert leftSideBox.isPresent();
        final IrTemp leftSide = leftSideBox.get();

        final IInstruction instruction = new VariableAssignment(leftSide, rightSide);
        mInstructionList.add(instruction);

        return null;
    }

    @Override
    public IrTemp visit(ArrayAssignment assignment) {
        return null;
    }

    @Override
    public IrTemp visit(Block block) {
        return null;
    }

    @Override
    public IrTemp visit(EqualityExpression expression) {
        return null;
    }

    @Override
    public IrTemp visit(LessThanExpression expression) {
        return null;
    }

    @Override
    public IrTemp visit(AddExpression expression) {
        return null;
    }

    @Override
    public IrTemp visit(SubtractExpression expression) {
        return null;
    }

    @Override
    public IrTemp visit(MultiplyExpression expression) {
        return null;
    }

    @Override
    public IrTemp visit(ParenExpression expression) {
        return null;
    }

    @Override
    public IrTemp visit(ArrayReference reference) {
        return null;
    }

    @Override
    public IrTemp visit(FunctionCall functionCall) {
        return null;
    }

    @Override
    public IrTemp visit(ExpressionList expressionList) {
        return null;
    }

}
