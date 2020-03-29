package ca.frankcom.csc435.compiler.ir.visit.jasmingen;

import ca.frankcom.csc435.compiler.ir.*;
import ca.frankcom.csc435.compiler.ir.visit.IIrVisitor;
import ca.frankcom.csc435.compiler.jasmin.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class JasminGenVisitor implements IIrVisitor<Void> {

    // Arbitrary choice, should be sufficiently large enough.
    // todo: correct this
    private static final int STACK_SIZE_LIMIT = 16;

    private static JType.UniversalType convertType(IrType type) {
        final JType.UniversalType result;

        if (type instanceof IrType.Atomic) {
            final IrType.Atomic atomicType = (IrType.Atomic) type;

            switch (atomicType) {
                case Boolean:
                    result = JType.Universal.Boolean;
                    break;
                case Character:
                    result = JType.Universal.Character;
                    break;
                case FloatingPoint:
                    result = JType.Universal.FloatingPoint;
                    break;
                case Integer:
                    result = JType.Universal.Integer;
                    break;
                default:
                    final String message = String.format("Invalid parameter type %s provided.", type);
                    throw new IllegalArgumentException(message);
            }

        } else {
            assert type instanceof IrType.Reference;
            final IrType.Reference referenceType = (IrType.Reference) type;

            switch (referenceType) {
                case String:
                    result = JType.Universal.String;
                    break;
                case StringArray:
                    result = JType.Universal.StringArray;
                    break;
                case BooleanArray:
                    result = JType.Universal.BooleanArray;
                    break;
                case CharacterArray:
                    result = JType.Universal.CharacterArray;
                    break;
                case FloatingPointArray:
                    result = JType.Universal.FloatingPointArray;
                    break;
                case IntegerArray:
                    result = JType.Universal.IntegerArray;
                    break;
                default:
                    final String message = String.format("Invalid parameter type %s provided.", type);
                    throw new IllegalArgumentException(message);
            }
        }

        return result;
    }

    private static JType.ReturnType convertReturnType(IrType type) {
        if (type instanceof IrType.Atomic) {
            final IrType.Atomic atomicType = (IrType.Atomic) type;
            if (atomicType == IrType.Atomic.Void) {
                return JType.Return.Void;
            }
        }

        return convertType(type);
    }

    private static JInstructionType convertInstructionType(IrType type) {
        final JInstructionType result;

        if (type instanceof IrType.Atomic) {
            final IrType.Atomic atomicType = (IrType.Atomic) type;

            switch (atomicType) {
                case Boolean:
                case Character:
                case Integer:
                    result = JInstructionType.Integer;
                    break;
                case FloatingPoint:
                    result = JInstructionType.FloatingPoint;
                    break;
                default:
                    final String message = String.format("Invalid type %s provided.", type);
                    throw new IllegalArgumentException(message);
            }

        } else {
            assert type instanceof IrType.Reference;
            result = JInstructionType.Address;
        }

        return result;
    }

    private final List<JFunction> mFunctions = new LinkedList<>();

    private List<JInstruction> mInstructionList = null;
    private int mNextLabel = 0;

    private JProgram mProgram = null;
    private String mProgramName = null;

    public JProgram getProgram() {
        return mProgram;
    }

    private JLabel getNextLabel() {
        final int nextInt = mNextLabel++;
        return new JLabel("L_" + nextInt);
    }

    @Override
    public Void visit(IrProgram program) {
        mProgramName = program.getName();

        for (IrFunction function : program.getFunctions()) {
            function.accept(this);
        }

        mProgram = new JProgram(mProgramName, mFunctions);

        return null;
    }

    private static FunctionDeclaration getDeclaration(IrFunction function) {

        final List<IrType> irParamTypes = function.getParamTypes();
        final List<JType.UniversalType> jasminParamTypes = new ArrayList<>(irParamTypes.size());
        for (IrType type : irParamTypes) {
            jasminParamTypes.add(convertType(type));
        }

        final IrType irReturnType = function.getReturnType();
        final JType.ReturnType jasminReturnType = convertReturnType(irReturnType);

        final String name = function.getName();

        return new FunctionDeclaration(name, jasminParamTypes, jasminReturnType);
    }

    @Override
    public Void visit(IrFunction function) {
        mInstructionList = new LinkedList<>();
        mNextLabel = 0;

        final JLabel firstLabel = new JLabel("L_first");
        final JLabel lastLabel = new JLabel("L_last");

        final List<VarDirective> varDirectives = new LinkedList<>();
        for (IrTemp temp : function.getDeclarations()) {
            final int varNumber = temp.getNumber();

            final IrType irType = temp.getType();
            final JType.UniversalType jasminType = convertType(irType);

            final VarDirective directive = new VarDirective(varNumber, jasminType, firstLabel, lastLabel);
            varDirectives.add(directive);
        }

        mInstructionList.add(firstLabel);
        for (IInstruction irInstruction : function.getInstructions()) {
            irInstruction.accept(this);
        }

        mInstructionList.add(lastLabel);

        final LimitDirectives limitDirectives = new LimitDirectives(varDirectives.size(), STACK_SIZE_LIMIT);
        final FunctionDeclaration functionDeclaration = getDeclaration(function);

        final JFunction jasminFunction = new JFunction(functionDeclaration, limitDirectives, varDirectives, mInstructionList);
        mFunctions.add(jasminFunction);

        mInstructionList = null;
        mNextLabel = 0;
        return null;
    }

    @Override
    public Void visit(IrTemp temporary) {
        final String message = "There is no IR to generate for this statement";
        throw new UnsupportedOperationException(message);
    }

    @Override
    public Void visit(IrType type) {
        final String message = "There is no IR to generate for this statement";
        throw new UnsupportedOperationException(message);
    }

    @Override
    public Void visit(IrLabel label) {
        final int labelNum = label.getLabelNum();
        final String labelName = "L_ir_" + labelNum;
        final JInstruction instruction = new JLabel(labelName);
        mInstructionList.add(instruction);

        return null;
    }

    @Override
    public Void visit(Jump instruction) {
        final int existingInstructions = mInstructionList.size();
        if (existingInstructions > 0) {
            final int lastInstructionIndex = existingInstructions - 1;
            final JInstruction lastInstruction = mInstructionList.get(lastInstructionIndex);

            // Placing a jump after a return statement
            // results in invalid Jasmin code.
            if (lastInstruction instanceof JReturn) {
                return null;
            }
        }

        final int labelNum = instruction.getJumpLabel().getLabelNum();
        final String labelName = "L_ir_" + labelNum;
        final JLabel label = new JLabel(labelName);

        final JInstruction goToInstruction = new JGoTo(label);
        mInstructionList.add(goToInstruction);

        return null;
    }

    @Override
    public Void visit(ConditionalJump jump) {
        assert jump.getCondition().getType().equals(IrType.Atomic.Boolean);

        final int varNumber = jump.getCondition().getNumber();
        final JInstruction load = new JLoad(JInstructionType.Integer, varNumber);
        mInstructionList.add(load);

        final int labelNum = jump.getJumpLabel().getLabelNum();
        final String labelName = "L_ir_" + labelNum;
        final JLabel label = new JLabel(labelName);
        final JInstruction conditionalJump = new JIfGreaterThanZero(label);
        mInstructionList.add(conditionalJump);

        return null;
    }

    @Override
    public Void visit(IrInitializeArray initialization) {
        final int size = initialization.getSize();
        final JConstant sizeConstant = new IntegerConstant(size);
        final JInstruction loadConstant = new JLoadConstant(sizeConstant);
        mInstructionList.add(loadConstant);

        final IrType irType = initialization.getType();
        final JType.UniversalType elementType = convertType(irType);

        final JInstruction newArray;
        if (elementType.equals(JType.Universal.String)) {
            newArray = new JArrayNew(JArrayType.Address, elementType);
        } else {
            newArray = new JArrayNew(JArrayType.Primitive, elementType);
        }
        mInstructionList.add(newArray);

        final IrTemp result = initialization.getTemp();
        final IrType resultType = result.getType();
        final JInstructionType resultInstructionType = convertInstructionType(resultType);
        final JInstruction store = new JStore(resultInstructionType, result.getNumber());
        mInstructionList.add(store);

        return null;
    }

    @Override
    public Void visit(IrArrayAssignment assignment) {
        final IrTemp arrayTemp = assignment.getArray();
        final IrType arrayTempType = arrayTemp.getType();
        final JInstructionType arrayLoadType = convertInstructionType(arrayTempType);
        final JInstruction arrayLoad = new JLoad(arrayLoadType, arrayTemp.getNumber());
        mInstructionList.add(arrayLoad);

        final IrTemp indexTemp = assignment.getIndex();
        final IrType indexTempType = indexTemp.getType();
        final JInstructionType indexLoadType = convertInstructionType(indexTempType);
        final JInstruction indexLoad = new JLoad(indexLoadType, indexTemp.getNumber());
        mInstructionList.add(indexLoad);

        final IrTemp valueTemp = assignment.getValue();
        final IrType valueTempType = valueTemp.getType();
        final JInstructionType valueLoadType = convertInstructionType(valueTempType);
        final JInstruction valueLoad = new JLoad(valueLoadType, valueTemp.getNumber());
        mInstructionList.add(valueLoad);

        final JType.UniversalType elementType = convertType(assignment.getValue().getType());

        final JInstruction arrayStore;
        if (elementType.equals(JType.Universal.String)) {
            arrayStore = new JArrayStore(JArrayType.Address);
        } else {
            arrayStore = new JArrayStore(JArrayType.Primitive);
        }
        mInstructionList.add(arrayStore);

        return null;
    }

    @Override
    public Void visit(IrArrayAccess access) {
        final IrTemp arrayTemp = access.getArray();
        final IrType arrayTempType = arrayTemp.getType();
        final JInstructionType arrayLoadType = convertInstructionType(arrayTempType);
        final JInstruction arrayLoad = new JLoad(arrayLoadType, arrayTemp.getNumber());
        mInstructionList.add(arrayLoad);

        final IrTemp indexTemp = access.getIndex();
        final IrType indexTempType = indexTemp.getType();
        final JInstructionType indexLoadType = convertInstructionType(indexTempType);
        final JInstruction indexLoad = new JLoad(indexLoadType, indexTemp.getNumber());
        mInstructionList.add(indexLoad);

        final JType.UniversalType elementType = convertType(access.getResult().getType());

        final JInstruction instruction;
        if (elementType.equals(JType.Universal.String)) {
            instruction = new JArrayLoad(JArrayType.Address);
        } else {
            instruction = new JArrayLoad(JArrayType.Primitive);
        }
        mInstructionList.add(instruction);

        final IrTemp resultTemp = access.getResult();
        final IrType resultTempType = resultTemp.getType();
        final JInstructionType resultStoreType = convertInstructionType(resultTempType);
        final JInstruction valueStore = new JStore(resultStoreType, resultTemp.getNumber());
        mInstructionList.add(valueStore);

        return null;
    }

    @Override
    public Void visit(ConstantAssignment assignment) {
        final IrTemp operand = assignment.getOperand();
        final IrType operandType = operand.getType();
        final JInstructionType instructionType = convertInstructionType(operandType);

        final ConstantAssignment.Value value = assignment.getValue();
        final JConstant constant;
        if (value instanceof ConstantAssignment.IntegerValue) {
            final ConstantAssignment.IntegerValue intVal = (ConstantAssignment.IntegerValue) value;
            constant = new IntegerConstant(intVal.get());
        } else if (value instanceof ConstantAssignment.BooleanValue) {
            final ConstantAssignment.BooleanValue booleanValue = (ConstantAssignment.BooleanValue) value;
            constant = new BooleanConstant(booleanValue.get());
        } else if (value instanceof ConstantAssignment.CharValue) {
            final ConstantAssignment.CharValue charValue = (ConstantAssignment.CharValue) value;
            constant = new CharConstant(charValue.get());
        } else if (value instanceof ConstantAssignment.FloatValue) {
            final ConstantAssignment.FloatValue floatValue = (ConstantAssignment.FloatValue) value;
            constant = new FloatConstant(floatValue.get());
        } else {
            assert value instanceof ConstantAssignment.StringValue;
            final ConstantAssignment.StringValue stringValue = (ConstantAssignment.StringValue) value;
            constant = new StringConstant(stringValue.get());
        }

        final JInstruction loadConstant = new JLoadConstant(constant);
        mInstructionList.add(loadConstant);

        final int varNumber = operand.getNumber();
        final JInstruction varStore = new JStore(instructionType, varNumber);
        mInstructionList.add(varStore);

        return null;
    }

    @Override
    public Void visit(VariableAssignment assignment) {

        final IrTemp left = assignment.getLeft();
        final IrTemp right = assignment.getRight();

        final IrType operandType = right.getType();
        final JInstructionType instructionType = convertInstructionType(operandType);

        final JInstruction rightLoad = new JLoad(instructionType, right.getNumber());
        mInstructionList.add(rightLoad);

        final JInstruction leftStore = new JStore(instructionType, left.getNumber());
        mInstructionList.add(leftStore);

        return null;
    }

    @Override
    public Void visit(Print instruction) {
        final IrTemp operand = instruction.getOperand();
        final IrType operandType = operand.getType();
        final JInstructionType instructionType = convertInstructionType(operandType);

        final JInstruction streamLoad = new JLoadPrintStream();
        mInstructionList.add(streamLoad);

        final int varNumber = operand.getNumber();
        final JInstruction varLoad = new JLoad(instructionType, varNumber);
        mInstructionList.add(varLoad);

        final JType.UniversalType varType = convertType(operandType);
        final JInstruction printInstruction = new JPrint(varType);
        mInstructionList.add(printInstruction);

        return null;
    }

    @Override
    public Void visit(PrintLn instruction) {
        final IrTemp operand = instruction.getOperand();
        final IrType operandType = operand.getType();
        final JInstructionType instructionType = convertInstructionType(operandType);

        final JInstruction streamLoad = new JLoadPrintStream();
        mInstructionList.add(streamLoad);

        final int varNumber = operand.getNumber();
        final JInstruction loadInstruction = new JLoad(instructionType, varNumber);
        mInstructionList.add(loadInstruction);

        final JType.UniversalType varType = convertType(operandType);
        final JInstruction printInstruction = new JPrintLn(varType);
        mInstructionList.add(printInstruction);

        return null;
    }

    private void generateFunctionInvoke(IrCall instruction, IrType irReturnType, int resultVarNum) {
        final List<IrTemp> params = instruction.getParams();
        final List<JType.UniversalType> paramTypes = new ArrayList<>(params.size());

        for (IrTemp param : params) {
            final IrType originalType = param.getType();

            final JInstructionType instructionType = convertInstructionType(originalType);
            final JInstruction loadInstruction = new JLoad(instructionType, param.getNumber());
            mInstructionList.add(loadInstruction);

            final JType.UniversalType varType = convertType(param.getType());
            paramTypes.add(varType);
        }

        final JType.ReturnType returnType = convertReturnType(irReturnType);
        final JInstruction invoke = new JInvokeInstruction(mProgramName,
                instruction.getFunctionName(), paramTypes, returnType);
        mInstructionList.add(invoke);

        if (returnType.equals(JType.Return.Void) == false) {
            final JInstructionType returnInstructionType = convertInstructionType(irReturnType);
            final JInstruction resultStore = new JStore(returnInstructionType, resultVarNum);
            mInstructionList.add(resultStore);
        }

    }

    @Override
    public Void visit(IrCall instruction) {
        final IrType returnType = IrType.Atomic.Void;
        generateFunctionInvoke(instruction, returnType, -1);

        return null;
    }

    @Override
    public Void visit(IrCallWithResult instruction) {
        final IrType returnType = instruction.getResult().getType();
        final int resultVarNum = instruction.getResult().getNumber();
        generateFunctionInvoke(instruction, returnType, resultVarNum);

        return null;
    }

    @Override
    public Void visit(IrReturn instruction) {
        final Optional<IrTemp> value = instruction.getValue();
        if (value.isPresent() == false) {
            mInstructionList.add(new VoidReturn());
            return null;
        }

        final IrTemp temp = value.get();
        final IrType returnType = temp.getType();
        final JInstructionType instructionType = convertInstructionType(returnType);

        final int varNumber = temp.getNumber();
        final JInstruction loadInstruction = new JLoad(instructionType, varNumber);
        mInstructionList.add(loadInstruction);

        final JInstruction returnInstruction = new ValueReturn(instructionType);
        mInstructionList.add(returnInstruction);

        return null;
    }

    private void generateBooleanOperation(BinaryOperation operation, Function<JLabel, JInstruction> comparisonFactory) {
        final IrTemp result = operation.getResult();
        final IrType resultIrType = result.getType();
        final JInstructionType resultType = convertInstructionType(resultIrType);

        final IrTemp left = operation.getLeft();
        final IrTemp right = operation.getRight();

        final IrType leftIrType = left.getType();
        final JInstructionType operandType = convertInstructionType(leftIrType);

        final int leftVar = left.getNumber();
        final JInstruction loadLeft = new JLoad(operandType, leftVar);
        mInstructionList.add(loadLeft);

        final int rightVar = right.getNumber();
        final JInstruction loadRight = new JLoad(operandType, rightVar);
        mInstructionList.add(loadRight);

        final JInstruction comparison;
        switch (operandType) {
            case Integer:
                comparison = new JIntegerSubtract();
                break;
            case FloatingPoint:
                comparison = new JFloatCompare();
                break;
            case Address:
                // Arrays are not supported for this operation, but
                // these are already filtered by semantic checking.
                comparison = new JStringCompare();
                break;
            default:
                final String message = String.format("Invalid type %s provided.", operandType);
                throw new IllegalArgumentException(message);
        }
        mInstructionList.add(comparison);

        final JLabel zeroLabel = getNextLabel();
        final JLabel otherLabel = getNextLabel();

        final JInstruction ifZero = comparisonFactory.apply(zeroLabel);
        mInstructionList.add(ifZero);

        final JInstruction loadFalse = new JLoadConstant(new BooleanConstant(false));
        mInstructionList.add(loadFalse);

        final JInstruction skipSuccess = new JGoTo(otherLabel);
        mInstructionList.add(skipSuccess);

        mInstructionList.add(zeroLabel);

        final JInstruction loadTrue = new JLoadConstant(new BooleanConstant(true));
        mInstructionList.add(loadTrue);

        mInstructionList.add(otherLabel);

        final JInstruction resultStore = new JStore(resultType, result.getNumber());
        mInstructionList.add(resultStore);
    }

    @Override
    public Void visit(IrEquality equality) {
        generateBooleanOperation(equality, JIfZero::new);

        return null;
    }

    @Override
    public Void visit(IrLessThan lessThan) {
        generateBooleanOperation(lessThan, JIfLessThanZero::new);

        return null;
    }

    @Override
    public Void visit(IrAdd add) {
        final IrTemp result = add.getResult();
        final IrType resultIrType = result.getType();
        final JInstructionType resultType = convertInstructionType(resultIrType);

        final int leftVar = add.getLeft().getNumber();
        final JInstruction loadLeft = new JLoad(resultType, leftVar);
        mInstructionList.add(loadLeft);

        final int rightVar = add.getRight().getNumber();
        final JInstruction loadRight = new JLoad(resultType, rightVar);
        mInstructionList.add(loadRight);

        switch (resultType) {
            case Integer:
                mInstructionList.add(new JIntegerAdd());
                break;
            case FloatingPoint:
                mInstructionList.add(new JFloatAdd());
                break;
            case Address:
                // Arrays are not supported for this operation, but
                // these are already filtered by semantic checking.
                mInstructionList.add(new JLoadPrintStream());

                mInstructionList.add(new JStringBuilderNew());
                mInstructionList.add(new JDuplicateTopOfStack());
                mInstructionList.add(new JStringBuilderInit());

                mInstructionList.add(new JLoad(resultType, leftVar));
                mInstructionList.add(new JStringBuilderAppend());

                mInstructionList.add(new JLoad(resultType, rightVar));
                mInstructionList.add(new JStringBuilderAppend());

                mInstructionList.add(new JStringBuilderOutput());
                break;
            default:
                final String message = String.format("Invalid type %s provided.", resultType);
                throw new IllegalArgumentException(message);
        }

        final JInstruction resultStore = new JStore(resultType, result.getNumber());
        mInstructionList.add(resultStore);

        return null;
    }

    @Override
    public Void visit(IrSubtract subtract) {
        final IrTemp result = subtract.getResult();
        final IrType resultIrType = result.getType();
        final JInstructionType resultType = convertInstructionType(resultIrType);

        final int leftVar = subtract.getLeft().getNumber();
        final JInstruction loadLeft = new JLoad(resultType, leftVar);
        mInstructionList.add(loadLeft);

        final int rightVar = subtract.getRight().getNumber();
        final JInstruction loadRight = new JLoad(resultType, rightVar);
        mInstructionList.add(loadRight);

        final JInstruction operation;
        switch (resultType) {
            case Integer:
                operation = new JIntegerSubtract();
                break;
            case FloatingPoint:
                operation = new JFloatSubtract();
                break;
            default:
                final String message = String.format("Invalid type %s provided.", resultType);
                throw new IllegalArgumentException(message);
        }
        mInstructionList.add(operation);

        final JInstruction resultStore = new JStore(resultType, result.getNumber());
        mInstructionList.add(resultStore);

        return null;
    }

    @Override
    public Void visit(IrMultiply multiply) {
        final IrTemp result = multiply.getResult();
        final IrType resultIrType = result.getType();
        final JInstructionType resultType = convertInstructionType(resultIrType);

        final int leftVar = multiply.getLeft().getNumber();
        final JInstruction loadLeft = new JLoad(resultType, leftVar);
        mInstructionList.add(loadLeft);

        final int rightVar = multiply.getRight().getNumber();
        final JInstruction loadRight = new JLoad(resultType, rightVar);
        mInstructionList.add(loadRight);

        final JInstruction operation;
        switch (resultType) {
            case Integer:
                operation = new JIntegerMultiply();
                break;
            case FloatingPoint:
                operation = new JFloatMultiply();
                break;
            default:
                final String message = String.format("Invalid type %s provided.", resultType);
                throw new IllegalArgumentException(message);
        }
        mInstructionList.add(operation);

        final JInstruction resultStore = new JStore(resultType, result.getNumber());
        mInstructionList.add(resultStore);

        return null;
    }

    @Override
    public Void visit(IrInversion inversion) {

        final IrTemp original = inversion.getOriginal();
        final IrType originalIrType = original.getType();
        final JInstructionType originalType = convertInstructionType(originalIrType);

        final JInstruction load = new JLoad(originalType, original.getNumber());
        mInstructionList.add(load);

        final JConstant constantOne = new IntegerConstant(1);
        final JInstruction loadOne = new JLoadConstant(constantOne);
        mInstructionList.add(loadOne);

        final JInstruction xor = new JIntegerXor();
        mInstructionList.add(xor);

        final IrTemp result = inversion.getResult();
        final IrType resultIrType = result.getType();
        final JInstructionType resultType = convertInstructionType(resultIrType);

        final JInstruction store = new JStore(resultType, result.getNumber());
        mInstructionList.add(store);

        return null;
    }

}
