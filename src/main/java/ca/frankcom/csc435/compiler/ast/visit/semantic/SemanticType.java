package ca.frankcom.csc435.compiler.ast.visit.semantic;

import ca.frankcom.csc435.compiler.ast.Type;

import java.util.Objects;

@SuppressWarnings("StaticInitializerReferencesSubClass")
public abstract class SemanticType {

    public static final SemanticType BOOL = new SemanticName(Type.Name.Boolean);
    public static final SemanticType CHAR = new SemanticName(Type.Name.Character);
    public static final SemanticType FLOAT = new SemanticName(Type.Name.FloatingPoint);
    public static final SemanticType INT = new SemanticName(Type.Name.Integer);
    public static final SemanticType STRING = new SemanticName(Type.Name.String);
    public static final SemanticType VOID = new SemanticName(Type.Name.Void);

    public static SemanticType forName(Type.Name type) {
        assert type != null;
        return new SemanticName(type);
    }

    public static SemanticType forArray(Type.Name type, int numElements) {
        assert type != null;
        assert numElements >= 0;
        return new SemanticArray(type, numElements);
    }

    public abstract boolean isArray();

    public abstract Type.Name getName();

    private static class SemanticArray extends SemanticType {

        SemanticArray(Type.Name type, int numElements) {
            assert type != null;
            assert numElements >= 0;
            mType = type;
            mNumElements = numElements;
        }

        private final Type.Name mType;
        private final int mNumElements;

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other instanceof SemanticArray) {
                final SemanticArray known = (SemanticArray) other;
                return Objects.equals(mType, known.mType)
                        && Objects.equals(mNumElements, known.mNumElements);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(mType, mNumElements);
        }

        @Override
        public String toString() {
            return String.format("%s[%d]", mType.toString(), mNumElements);
        }

        @Override
        public boolean isArray() {
            return true;
        }

        @Override
        public Type.Name getName() {
            return mType;
        }

    }

    private static class SemanticName extends SemanticType {

        SemanticName(Type.Name type) {
            assert type != null;
            mType = type;
        }

        private final Type.Name mType;

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other instanceof SemanticName) {
                final SemanticName known = (SemanticName) other;
                return Objects.equals(mType, known.mType);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(mType);
        }

        @Override
        public String toString() {
            return mType.toString();
        }

        @Override
        public boolean isArray() {
            return false;
        }

        @Override
        public Type.Name getName() {
            return mType;
        }

    }

}
