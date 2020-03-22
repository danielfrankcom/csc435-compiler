package ca.frankcom.csc435.compiler.jasmin.visit.print;

public class JasminBoilerplate {

    private static final String HEADER_PATTERN = "" +
            ".class public %s\n" +
            ".super java/lang/Object";

    private static final String MAIN_METHOD_PATTERN = "" +
            "; entry point method\n" +
            ".method public static main([Ljava/lang/String;)V\n" +
            "\t; set limits used by this method\n" +
            "\t.limit locals 1\n" +
            "\t.limit stack 4\n" +
            "\tinvokestatic %s/main()V\n" +
            "\treturn\n" +
            ".end method";

    private static final String INITIALIZER = "" +
            "; standard initializer\n" +
            ".method public <init>()V\n" +
            "\taload_0\n" +
            "\tinvokenonvirtual java/lang/Object/<init>()V\n" +
            "\treturn\n" +
            ".end method";

    public static String getHeader(String programName) {
        return "" +
                String.format(HEADER_PATTERN, programName) +
                "\n\n" +
                "; GENERATED METHODS";
    }

    public static String getFooter(String programName) {
        return "" +
                "; BOILERPLATE\n" +
                "\n" +
                String.format(MAIN_METHOD_PATTERN, programName) +
                "\n\n" +
                INITIALIZER;
    }

}
