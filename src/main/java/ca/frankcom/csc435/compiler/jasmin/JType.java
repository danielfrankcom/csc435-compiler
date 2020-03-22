package ca.frankcom.csc435.compiler.jasmin;

public interface JType {

    interface ReturnType extends JType {
    }

    interface UniversalType extends ReturnType {
    }

    enum Return implements ReturnType {
        Void
    }

    enum Universal implements UniversalType {
        Boolean,
        BooleanArray,
        Character,
        CharacterArray,
        FloatingPoint,
        FloatingPointArray,
        Integer,
        IntegerArray,
        String,
        StringArray
    }

}
