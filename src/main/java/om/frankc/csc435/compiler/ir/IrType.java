package om.frankc.csc435.compiler.ir;

import om.frankc.csc435.compiler.ir.visit.IIrVisitor;

public interface IrType extends IIrNode {

    enum Atomic implements IrType {
        Boolean,
        Character,
        FloatingPoint,
        Integer,
        Void;

        @Override
        public <T> T accept(IIrVisitor<T> visitor) {
            return visitor.visit(this);
        }

    }

    enum Reference implements IrType {
        String(null),
        BooleanArray(Atomic.Boolean),
        CharacterArray(Atomic.Character),
        FloatingPointArray(Atomic.FloatingPoint),
        IntegerArray(Atomic.Integer),
        StringArray(String);

        Reference(IrType underlyingType) {
            mUnderlyingType = underlyingType;
            mIsArray = underlyingType != null;
        }

        private final IrType mUnderlyingType;
        private final boolean mIsArray;

        public boolean isArray() {
            return mIsArray;
        }

        public IrType getUnderlyingType() {
            return mUnderlyingType;
        }

        @Override
        public <T> T accept(IIrVisitor<T> visitor) {
            return visitor.visit(this);
        }

    }

}
