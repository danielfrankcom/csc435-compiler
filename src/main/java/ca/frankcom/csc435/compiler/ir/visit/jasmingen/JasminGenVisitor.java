package ca.frankcom.csc435.compiler.ir.visit.jasmingen;

import ca.frankcom.csc435.compiler.ir.*;
import ca.frankcom.csc435.compiler.ir.visit.IIrVisitor;
import ca.frankcom.csc435.compiler.jasmin.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    private static JInstructionType getInstructionType(IrType type) {
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

    public JProgram getProgram() {
        return mProgram;
    }

    private JLabel getNextLabel() {
        final int nextInt = mNextLabel++;
        return new JLabel("L_" + nextInt);
    }

    @Override
    public Void visit(IrProgram program) {
        for (IrFunction function : program.getFunctions()) {
            function.accept(this);
        }

        mProgram = new JProgram(program.getName(), mFunctions);

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
            if (temp.getCategory() == IrTemp.Category.PARAMETER) {
                // Parameters are added by Jasmin.
                continue;
            }

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
        return null;
    }

    @Override
    public Void visit(ConditionalJump jump) {
        return null;
    }

    @Override
    public Void visit(IrInitializeArray initialization) {
        return null;
    }

    @Override
    public Void visit(IrArrayAssignment assignment) {
        return null;
    }

    @Override
    public Void visit(IrArrayAccess access) {
        return null;
    }

    @Override
    public Void visit(ConstantAssignment assignment) {
        final IrTemp operand = assignment.getOperand();
        final IrType operandType = operand.getType();
        final JInstructionType instructionType = getInstructionType(operandType);

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
        final JInstructionType instructionType = getInstructionType(operandType);

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
        final JInstructionType instructionType = getInstructionType(operandType);

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
        final JInstructionType instructionType = getInstructionType(operandType);

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

    @Override
    public Void visit(IrCall instruction) {
        return null;
    }

    @Override
    public Void visit(IrCallWithResult instruction) {
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
        final JInstructionType instructionType = getInstructionType(returnType);

        final int varNumber = temp.getNumber();
        final JInstruction loadInstruction = new JLoad(instructionType, varNumber);
        mInstructionList.add(loadInstruction);

        final JInstruction returnInstruction = new ValueReturn(instructionType);
        mInstructionList.add(returnInstruction);

        return null;
    }

    @Override
    public Void visit(IrEquality equality) {
        final IrTemp left = equality.getLeft();
        final IrType leftIrType = left.getType();
        final JInstructionType operandType = getInstructionType(leftIrType);

        final IrTemp result = equality.getResult();
        final IrType resultIrType = result.getType();
        final JInstructionType resultType = getInstructionType(resultIrType);

        final int leftVar = equality.getLeft().getNumber();
        final JInstruction loadLeft = new JLoad(operandType, leftVar);
        mInstructionList.add(loadLeft);

        final int rightVar = equality.getRight().getNumber();
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

        final JInstruction ifZero = new JIfZero(zeroLabel);
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

        return null;
    }

    @Override
    public Void visit(IrLessThan lessThan) {
        return null;
    }

    @Override
    public Void visit(IrAdd add) {
        return null;
    }

    @Override
    public Void visit(IrSubtract subtract) {
        return null;
    }

    @Override
    public Void visit(IrMultiply multiply) {
        return null;
    }

    @Override
    public Void visit(IrInversion inversion) {
        return null;
    }

}
