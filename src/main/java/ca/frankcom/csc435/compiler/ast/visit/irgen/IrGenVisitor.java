package ca.frankcom.csc435.compiler.ast.visit.irgen;

import ca.frankcom.csc435.compiler.ast.*;
import ca.frankcom.csc435.compiler.ast.visit.IAstVisitor;
import ca.frankcom.csc435.compiler.ir.*;
import ca.frankcom.csc435.compiler.util.Environment;
import com.google.common.collect.ImmutableList;

import java.util.*;

public class IrGenVisitor implements IAstVisitor<IrTemp> {

    private static IrType convertType(TypeNode typeNode) {
        if (typeNode instanceof Type) {
            final Type type = (Type) typeNode;

            assert Type.Name.values().length == 6;
            switch (type.getName()) {
                case Void:
                    return IrType.Atomic.Void;
                case Boolean:
                    return IrType.Atomic.Boolean;
                case String:
                    return IrType.Reference.String;
                case Integer:
                    return IrType.Atomic.Integer;
                case Character:
                    return IrType.Atomic.Character;
                case FloatingPoint:
                    return IrType.Atomic.FloatingPoint;
                default:
                    final String message = "Unexpected enum value";
                    throw new IllegalArgumentException(message);
            }

        } else {
            assert typeNode instanceof ArrayType;
            final ArrayType arrayType = (ArrayType) typeNode;
            final Type elementType = arrayType.getType();

            assert Type.Name.values().length == 6;
            switch (elementType.getName()) {
                case Boolean:
                    return IrType.Reference.BooleanArray;
                case String:
                    return IrType.Reference.StringArray;
                case Integer:
                    return IrType.Reference.IntegerArray;
                case Character:
                    return IrType.Reference.CharacterArray;
                case FloatingPoint:
                    return IrType.Reference.FloatingPointArray;
                default:
                    final String message = "Unexpected enum value";
                    throw new IllegalArgumentException(message);
            }
        }
    }

    public IrGenVisitor(String programName) {
        assert programName != null;
        mProgramName = programName;
    }

    private final String mProgramName;

    private final Environment<String, IrTemp> mVariableEnvironment = new Environment<>();
    private final Map<String, IrType> mFunctionReturnTypes = new HashMap<>();
    private final List<IrFunction> mFunctions = new LinkedList<>();

    private TemporaryFactory mTempFactory = null;
    private LabelFactory mLabelFactory = null;
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

        // Initialize the return type map so that functions
        // can call one another within the next loop.
        for (Function function : functions.getElements()) {
            final FunctionDeclaration declaration = function.getDeclaration();
            final String name = declaration.getId().getText();
            final TypeNode astType = declaration.getTypeNode();
            final IrType returnType = convertType(astType);
            mFunctionReturnTypes.put(name, returnType);
        }

        for (Function function : functions.getElements()) {
            function.accept(this);
        }

        return null;
    }

    /**
     * Return statements in 'void' functions are optional in our language, but necessary in the IR.
     *
     * @param returnType The return type of the current function.
     * @return {@code true} if a 'return' statement should be added, {@code false} otherwise.
     */
    private boolean isManualReturnNeeded(IrType returnType) {
        final boolean isVoid = returnType.equals(IrType.Atomic.Void);

        final int numInstructions = mInstructionList.size();
        final int lastInstruction = numInstructions - 1;
        final boolean missingReturn = numInstructions == 0 ||
                mInstructionList.get(lastInstruction) instanceof IrReturn == false;

        return isVoid && missingReturn;
    }

    @Override
    public IrTemp visit(Function function) {
        mTempFactory = new TemporaryFactory();
        mLabelFactory = new LabelFactory();
        mInstructionList = new LinkedList<>();
        mVariableEnvironment.beginScope();

        function.getDeclaration().accept(this);
        function.getBody().accept(this);

        final FunctionDeclaration declaration = function.getDeclaration();
        final String functionName = declaration.getId().getText();
        final IrType returnType = convertType(declaration.getTypeNode());

        if (isManualReturnNeeded(returnType)) {
            mInstructionList.add(new IrReturn(Optional.empty()));
        }

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
        mLabelFactory = null;
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
        final IrTemp temp = mTempFactory.get(IrType.Atomic.Integer, IrTemp.Category.TEMPORARY);
        final IInstruction instruction = new ConstantAssignment(temp, literal.getValue());
        mInstructionList.add(instruction);
        return temp;
    }

    @Override
    public IrTemp visit(StringLiteral literal) {
        final IrTemp temp = mTempFactory.get(IrType.Reference.String, IrTemp.Category.TEMPORARY);
        final IInstruction instruction = new ConstantAssignment(temp, literal.getValue());
        mInstructionList.add(instruction);
        return temp;
    }

    @Override
    public IrTemp visit(FloatLiteral literal) {
        final IrTemp temp = mTempFactory.get(IrType.Atomic.FloatingPoint, IrTemp.Category.TEMPORARY);
        final IInstruction instruction = new ConstantAssignment(temp, literal.getValue());
        mInstructionList.add(instruction);
        return temp;
    }

    @Override
    public IrTemp visit(CharacterLiteral literal) {
        final IrTemp temp = mTempFactory.get(IrType.Atomic.Character, IrTemp.Category.TEMPORARY);
        final IInstruction instruction = new ConstantAssignment(temp, literal.getValue());
        mInstructionList.add(instruction);
        return temp;
    }

    @Override
    public IrTemp visit(BooleanLiteral literal) {
        final IrTemp temp = mTempFactory.get(IrType.Atomic.Boolean, IrTemp.Category.TEMPORARY);
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
        final TypeNode typeNode = declaration.getTypeNode();
        final IrType irType = convertType(typeNode);
        final IrTemp temp = mTempFactory.get(irType, IrTemp.Category.LOCAL);
        mVariableEnvironment.put(name, temp);

        if (typeNode instanceof ArrayType) {
            final ArrayType arrayType = (ArrayType) typeNode;
            final Type elementType = arrayType.getType();
            final IrType irElementType = convertType(elementType);
            final int size = arrayType.getSize().getValue();
            final IInstruction instruction = new IrInitializeArray(irElementType, size, temp);
            mInstructionList.add(instruction);
        }

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
        statement.getExpression().accept(this);
        return null;
    }

    @Override
    public IrTemp visit(IfStatement statement) {
        final IrTemp condition = statement.getExpression().accept(this);
        assert condition.getType() == IrType.Atomic.Boolean;

        final IrTemp inverted = mTempFactory.get(condition.getType(), IrTemp.Category.TEMPORARY);
        final IInstruction inversionInstruction = new IrInversion(inverted, condition);
        mInstructionList.add(inversionInstruction);

        final IrLabel endLabel = mLabelFactory.get();

        final IInstruction conditionalJump = new ConditionalJump(inverted, endLabel);
        mInstructionList.add(conditionalJump);

        statement.getIfBlock().accept(this);
        mInstructionList.add(endLabel);

        return null;
    }

    @Override
    public IrTemp visit(IfElseStatement statement) {
        final IrTemp condition = statement.getExpression().accept(this);
        assert condition.getType() == IrType.Atomic.Boolean;

        final IrTemp inverted = mTempFactory.get(condition.getType(), IrTemp.Category.TEMPORARY);
        final IInstruction inversionInstruction = new IrInversion(inverted, condition);
        mInstructionList.add(inversionInstruction);

        final IrLabel elseLabel = mLabelFactory.get();
        final IrLabel endLabel = mLabelFactory.get();

        final IInstruction conditionalJump = new ConditionalJump(inverted, elseLabel);
        mInstructionList.add(conditionalJump);

        statement.getIfBlock().accept(this);
        final IInstruction jump = new Jump(endLabel);
        mInstructionList.add(jump);

        mInstructionList.add(elseLabel);
        statement.getElseBlock().accept(this);

        mInstructionList.add(endLabel);

        return null;
    }

    @Override
    public IrTemp visit(WhileStatement statement) {
        final IrLabel startLabel = mLabelFactory.get();
        final IrLabel endLabel = mLabelFactory.get();
        mInstructionList.add(startLabel);

        final IrTemp condition = statement.getExpression().accept(this);
        assert condition.getType() == IrType.Atomic.Boolean;

        final IrTemp inverted = mTempFactory.get(condition.getType(), IrTemp.Category.TEMPORARY);
        final IInstruction inversionInstruction = new IrInversion(inverted, condition);
        mInstructionList.add(inversionInstruction);

        final IInstruction conditionalJump = new ConditionalJump(inverted, endLabel);
        mInstructionList.add(conditionalJump);

        statement.getBlock().accept(this);
        final IInstruction jump = new Jump(startLabel);
        mInstructionList.add(jump);

        mInstructionList.add(endLabel);

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
        final Optional<Expression> possibleExpression = statement.getExpression();

        final IInstruction instruction;
        if (possibleExpression.isPresent()) {
            final Expression expression = possibleExpression.get();
            final IrTemp temp = expression.accept(this);
            instruction = new IrReturn(Optional.of(temp));
        } else {
            instruction = new IrReturn(Optional.empty());
        }

        mInstructionList.add(instruction);
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
        final IrTemp index = assignment.getIndex().accept(this);
        final IrTemp value = assignment.getAssignment().accept(this);

        final String arrayName = assignment.getId().getText();
        final Optional<IrTemp> arrayTemp = mVariableEnvironment.get(arrayName);
        assert arrayTemp.isPresent();

        final IInstruction instruction = new IrArrayAssignment(arrayTemp.get(), index, value);
        mInstructionList.add(instruction);

        return null;
    }

    @Override
    public IrTemp visit(Block block) {
        block.getStatements().accept(this);
        return null;
    }

    private interface BinaryOperationFactory {
        BinaryOperation create(IrTemp result, IrTemp left, IrTemp right);

    }

    private IrTemp convertBooleanOperatorExpression(OperatorExpression expression, BinaryOperationFactory factory) {
        final IrTemp left = expression.getLeftSide().accept(this);
        final IrTemp right = expression.getRightSide().accept(this);

        final IrTemp result = mTempFactory.get(IrType.Atomic.Boolean, IrTemp.Category.TEMPORARY);
        final IInstruction instruction = factory.create(result, left, right);
        mInstructionList.add(instruction);

        return result;
    }

    @Override
    public IrTemp visit(EqualityExpression expression) {
        return convertBooleanOperatorExpression(expression, IrEquality::new);
    }

    @Override
    public IrTemp visit(LessThanExpression expression) {
        return convertBooleanOperatorExpression(expression, IrLessThan::new);
    }

    private IrTemp convertTypedOperatorExpression(OperatorExpression expression, BinaryOperationFactory factory) {
        final IrTemp left = expression.getLeftSide().accept(this);
        final IrTemp right = expression.getRightSide().accept(this);

        final IrTemp result = mTempFactory.get(left.getType(), IrTemp.Category.TEMPORARY);
        final IInstruction instruction = factory.create(result, left, right);
        mInstructionList.add(instruction);

        return result;
    }

    @Override
    public IrTemp visit(AddExpression expression) {
        return convertTypedOperatorExpression(expression, IrAdd::new);
    }

    @Override
    public IrTemp visit(SubtractExpression expression) {
        return convertTypedOperatorExpression(expression, IrSubtract::new);
    }

    @Override
    public IrTemp visit(MultiplyExpression expression) {
        return convertTypedOperatorExpression(expression, IrMultiply::new);
    }

    @Override
    public IrTemp visit(ParenExpression expression) {
        return expression.getExpression().accept(this);
    }

    @Override
    public IrTemp visit(ArrayReference reference) {
        final IrTemp index = reference.getExpression().accept(this);
        assert index.getType() == IrType.Atomic.Integer;

        final String arrayName = reference.getId().getText();
        final Optional<IrTemp> arrayOptional = mVariableEnvironment.get(arrayName);
        assert arrayOptional.isPresent();
        final IrTemp array = arrayOptional.get();

        final IrType arrayType = array.getType();
        assert arrayType instanceof IrType.Reference;
        final IrType.Reference referenceType = (IrType.Reference) arrayType;
        final IrType elementType = referenceType.getUnderlyingType();
        assert elementType != null;

        final IrTemp temp = mTempFactory.get(elementType, IrTemp.Category.TEMPORARY);
        final IInstruction instruction = new IrArrayAccess(temp, array, index);
        mInstructionList.add(instruction);

        return temp;
    }

    @Override
    public IrTemp visit(FunctionCall functionCall) {
        final List<Expression> paramExpressions = functionCall.getExpressions().getElements();
        final List<IrTemp> paramTemps = new ArrayList<>(paramExpressions.size());
        for (Expression expression : paramExpressions) {
            final IrTemp temp = expression.accept(this);
            paramTemps.add(temp);
        }

        final String functionName = functionCall.getId().getText();
        final IrType returnType = mFunctionReturnTypes.get(functionName);
        assert returnType != null;

        final IInstruction instruction;
        final IrTemp temp;
        if (returnType.equals(IrType.Atomic.Void)) {
            temp = null;
            instruction = new IrCall(functionName, paramTemps);
        } else {
            temp = mTempFactory.get(returnType, IrTemp.Category.TEMPORARY);
            instruction = new IrCallWithResult(functionName, paramTemps, temp);
        }

        mInstructionList.add(instruction);
        return temp;
    }

    @Override
    public IrTemp visit(ExpressionList expressionList) {
        final String message = "There is no IR to generate for this statement";
        throw new UnsupportedOperationException(message);
    }

}
