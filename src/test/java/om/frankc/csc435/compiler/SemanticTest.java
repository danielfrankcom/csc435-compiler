package om.frankc.csc435.compiler;

import com.google.common.collect.ImmutableMap;
import om.frankc.csc435.compiler.visit.semantic.SemanticException;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class SemanticTest {

    private static final ClassLoader CLASS_LOADER = SemanticTest.class.getClassLoader();

    private static final String TEST_FOLDER = "semantic";

    private enum Group {
        accept,
        reject
    }

    /**
     * Get an array of {@link File} objects representing the set of files within the specified testing group
     *
     * @param group A {@link SemanticTest.Group} that uniquely identifies the set of files to address.
     * @return The generated {@link File[]}.
     */
    private static File[] getTestFilesForGroup(SemanticTest.Group group) {
        final Path combinedPath = Paths.get(TEST_FOLDER, group.name());
        final String relativePath = combinedPath.toFile().getPath();
        final URL rootPath = CLASS_LOADER.getResource(relativePath);
        assertNotNull(rootPath);

        final File root = new File(rootPath.getFile());
        final File[] children = root.listFiles();
        assertNotNull(children);
        assertNotEquals(0, children.length);

        return children;
    }

    private static void compile(File file) throws Exception {
        final InputStream input = new FileInputStream(file);

        System.out.printf("Testing '%s'\n", file.getName());

        Compiler.compile(input, null, true);
    }

    @Test
    public void testAccept() throws Exception {
        final File[] files = getTestFilesForGroup(Group.accept);
        for (File file : files) {

            // Any exception will fail the test.
            compile(file);
        }
    }

    private static final ImmutableMap<String, String> REJECT_REASONS = new ImmutableMap.Builder<String, String>()
            .put("addArrays.ul", "May not perform operation with type 'boolean[2]' at 9:20")
            .put("addBoolean.ul", "May not perform operation with type 'boolean' at 6:18")
            .put("addInvalidTypesArray1.ul", "Attempt to operate on non-identical types 'boolean' and 'boolean[2]' at 7:18")
            .put("addInvalidTypesArray2.ul", "Attempt to operate on non-identical types 'boolean[3]' and 'int[3]' at 9:20")
            .put("addInvalidTypesPrimitive1.ul", "Attempt to operate on non-identical types 'int' and 'string' at 6:15")
            .put("addInvalidTypesPrimitive2.ul", "Attempt to operate on non-identical types 'boolean' and 'char' at 6:19")
            .put("addInvalidTypesPrimitive3.ul", "Attempt to operate on non-identical types 'int' and 'float' at 5:13")
            .put("addInvalidTypesVoid.ul", "Attempt to operate on non-identical types 'int' and 'void' at 6:15")
            .put("addVoid.ul", "May not perform operation with type 'void' at 6:20")
            .put("arrayAssignmentNonArray.ul", "Type 'int' may not be indexed at 6:8")
            .put("arrayAssignmentNonIntIndex.ul", "Array index must be integer at 6:8")
            .put("arrayAssignmentWrongType.ul", "Type 'string' cannot be assigned to 'int[10]' at 6:11")
            .put("arrayReturnWrongSize.ul", "Function 'foo' requires return type 'int[5]' at 6:4")
            .put("assignmentFromUndeclaredArray.ul", "Cannot access undeclared variable 'bar' at 6:10")
            .put("assignmentFromUndeclaredLiteral.ul", "Cannot access undeclared variable 'bar' at 6:10")
            .put("assignmentToWrongTypeArray.ul", "Type 'string' cannot be assigned to 'string[5]' at 6:8")
            .put("assignmentToWrongTypeLiteral.ul", "Type 'string' cannot be assigned to 'int' at 6:8")
            .put("assignmentToWrongTypeVoid.ul", "Type 'void' cannot be assigned to 'int' at 6:8")
            .put("assignmentWithoutDeclArray.ul", "Variable 'bar' not declared prior to assignment at 6:11")
            .put("assignmentWithoutDeclLiteral.ul", "Variable 'bar' not declared prior to assignment at 6:8")
            .put("duplicateFunctionDifferentTypes.ul", "Duplicate function 'foo' found at 7:8")
            .put("duplicateFunctionSameTypes.ul", "Duplicate function 'foo' found at 7:4")
            .put("duplicateParamDifferentTypes.ul", "Duplicate parameter name 'param' found at 4:26")
            .put("duplicateParamSameTypes.ul", "Duplicate parameter name 'duplicate' found at 4:34")
            .put("duplicateVariableDeclDifferentTypes.ul", "Duplicate variable named 'var' declared at 6:8")
            .put("duplicateVariableDeclSameTypes.ul", "Duplicate variable named 'var' declared at 6:8")
            .put("elseStatementArrayType.ul", "Non-boolean condition ('int[5]') provided to statement at 7:8")
            .put("elseStatementCharType.ul", "Non-boolean condition ('char') provided to statement at 7:8")
            .put("elseStatementFloatType.ul", "Non-boolean condition ('float') provided to statement at 7:8")
            .put("elseStatementIntType.ul", "Non-boolean condition ('int') provided to statement at 7:18")
            .put("elseStatementStringType.ul", "Non-boolean condition ('string') provided to statement at 7:8")
            .put("elseStatementVoidType.ul", "Non-boolean condition ('void') provided to statement at 5:8")
            .put("empty.ul", "No function 'main' found at 1:0")
            .put("emptyReturnNonVoidFunction.ul", "Function 'foo' requires return type 'int' at 5:4")
            .put("equalCompareArrays.ul", "May not perform comparison with type 'boolean[2]' at 9:20")
            .put("equalCompareVoid.ul", "May not perform comparison with type 'void' at 6:20")
            .put("equalInvalidTypesArray1.ul", "Attempt to compare incomparable types 'boolean' and 'boolean[2]' at 7:18")
            .put("equalInvalidTypesArray2.ul", "Attempt to compare incomparable types 'boolean[3]' and 'int[3]' at 9:20")
            .put("equalInvalidTypesPrimitive1.ul", "Attempt to compare incomparable types 'int' and 'string' at 6:15")
            .put("equalInvalidTypesPrimitive2.ul", "Attempt to compare incomparable types 'boolean' and 'char' at 6:19")
            .put("equalInvalidTypesPrimitive3.ul", "Attempt to compare incomparable types 'int' and 'float' at 5:13")
            .put("equalInvalidTypesVoid.ul", "Attempt to compare incomparable types 'int' and 'void' at 6:15")
            .put("ifStatementArrayType.ul", "Non-boolean condition ('int[5]') provided to statement at 7:8")
            .put("ifStatementCharType.ul", "Non-boolean condition ('char') provided to statement at 7:8")
            .put("ifStatementFloatType.ul", "Non-boolean condition ('float') provided to statement at 7:8")
            .put("ifStatementIntType.ul", "Non-boolean condition ('int') provided to statement at 7:18")
            .put("ifStatementStringType.ul", "Non-boolean condition ('string') provided to statement at 7:8")
            .put("ifStatementVoidType.ul", "Non-boolean condition ('void') provided to statement at 5:8")
            .put("indexNonArray.ul", "Cannot index non-array type 'int' at 6:4")
            .put("indexUndefinedArray.ul", "Attempt to access undefined array 'foo' at 5:4")
            .put("intReturnMain.ul", "Function 'main' must be of type 'void' at 1:0")
            .put("lessThanCompareArrays.ul", "May not perform comparison with type 'boolean[2]' at 9:20")
            .put("lessThanCompareVoid.ul", "May not perform comparison with type 'void' at 6:20")
            .put("lessThanInvalidTypesArray1.ul", "Attempt to compare incomparable types 'boolean' and 'boolean[2]' at 7:18")
            .put("lessThanInvalidTypesArray2.ul", "Attempt to compare incomparable types 'boolean[3]' and 'int[3]' at 9:20")
            .put("lessThanInvalidTypesPrimitive1.ul", "Attempt to compare incomparable types 'int' and 'string' at 6:15")
            .put("lessThanInvalidTypesPrimitive2.ul", "Attempt to compare incomparable types 'boolean' and 'char' at 6:19")
            .put("lessThanInvalidTypesPrimitive3.ul", "Attempt to compare incomparable types 'int' and 'float' at 5:13")
            .put("lessThanInvalidTypesVoid.ul", "Attempt to compare incomparable types 'int' and 'void' at 6:15")
            .put("mainWithParams.ul", "Function 'main' may not have parameters at 1:9")
            .put("mistakeInElseBlock.ul", "Function 'foo' requires return type 'void' at 11:8")
            .put("mistakeInIfBlock.ul", "Function 'foo' requires return type 'void' at 9:8")
            .put("mistakeInWhileBlock.ul", "Function 'foo' requires return type 'void' at 9:8")
            .put("multipleMain.ul", "Duplicate function 'main' found at 4:5")
            .put("multiplyArrays.ul", "May not perform operation with type 'boolean[2]' at 9:20")
            .put("multiplyBoolean.ul", "May not perform operation with type 'boolean' at 6:18")
            .put("multiplyChar.ul", "May not perform operation with type 'char' at 6:17")
            .put("multiplyInvalidTypesArray1.ul", "Attempt to operate on non-identical types 'boolean' and 'boolean[2]' at 7:18")
            .put("multiplyInvalidTypesArray2.ul", "Attempt to operate on non-identical types 'boolean[3]' and 'int[3]' at 9:20")
            .put("multiplyInvalidTypesPrimitive1.ul", "Attempt to operate on non-identical types 'int' and 'string' at 6:15")
            .put("multiplyInvalidTypesPrimitive2.ul", "Attempt to operate on non-identical types 'boolean' and 'char' at 6:19")
            .put("multiplyInvalidTypesPrimitive3.ul", "Attempt to operate on non-identical types 'int' and 'float' at 5:13")
            .put("multiplyInvalidTypesVoid.ul", "Attempt to operate on non-identical types 'int' and 'void' at 6:15")
            .put("multiplyString.ul", "May not perform operation with type 'string' at 6:23")
            .put("multiplyVoid.ul", "May not perform operation with type 'void' at 6:20")
            .put("noMain.ul", "No function 'main' found at 1:0")
            .put("nonIntArrayIndex.ul", "Array index must be of type 'int' at 6:8")
            .put("printArrayType.ul", "Invalid type 'int[5]' provided to print statement at 6:10")
            .put("printlnArrayType.ul", "Invalid type 'int[5]' provided to println statement at 6:12")
            .put("printlnVoidType.ul", "Invalid type 'void' provided to println statement at 5:12")
            .put("printVoidType.ul", "Invalid type 'void' provided to print statement at 5:10")
            .put("subtractArrays.ul", "May not perform operation with type 'boolean[2]' at 9:20")
            .put("subtractBoolean.ul", "May not perform operation with type 'boolean' at 6:18")
            .put("subtractInvalidTypesArray1.ul", "Attempt to operate on non-identical types 'boolean' and 'boolean[2]' at 7:18")
            .put("subtractInvalidTypesArray2.ul", "Attempt to operate on non-identical types 'boolean[3]' and 'int[3]' at 9:20")
            .put("subtractInvalidTypesPrimitive1.ul", "Attempt to operate on non-identical types 'int' and 'string' at 6:15")
            .put("subtractInvalidTypesPrimitive2.ul", "Attempt to operate on non-identical types 'boolean' and 'char' at 6:19")
            .put("subtractInvalidTypesPrimitive3.ul", "Attempt to operate on non-identical types 'int' and 'float' at 5:13")
            .put("subtractInvalidTypesVoid.ul", "Attempt to operate on non-identical types 'int' and 'void' at 6:15")
            .put("subtractString.ul", "May not perform operation with type 'string' at 6:23")
            .put("subtractVoid.ul", "May not perform operation with type 'void' at 6:20")
            .put("tooFewFunctionCallParams1.ul", "Parameter 'two' (#2) missing at 2:4")
            .put("tooFewFunctionCallParams2.ul", "Parameter 'one' (#1) missing at 2:4")
            .put("tooFewFunctionCallParams3.ul", "Parameter 'three' (#3) missing at 9:4")
            .put("tooManyFunctionCallParams1.ul", "Parameter of type 'float' unexpected (too many) at 2:11")
            .put("tooManyFunctionCallParams2.ul", "Parameter of type 'int' unexpected (too many) at 2:8")
            .put("tooManyFunctionCallParams3.ul", "Parameter of type 'string' unexpected (too many) at 9:39")
            .put("undefinedFunctionCall.ul", "Attempt to call undefined function 'bar' at 5:4")
            .put("undefinedFunctionReturn.ul", "Attempt to call undefined function 'bar' at 5:11")
            .put("variableHideParam.ul", "Variable 'two' may not hide parameter at 5:8")
            .put("voidArrayDecl.ul", "Array type 'void' invalid at 5:4")
            .put("voidArrayParam.ul", "Array type 'void' invalid at 4:9")
            .put("voidArrayReturn.ul", "Array type 'void' invalid at 4:0")
            .put("voidParamBeginning.ul", "Parameter type may not be 'void' at 4:9")
            .put("voidParamMiddle.ul", "Parameter type may not be 'void' at 4:29")
            .put("voidVariableDecl.ul", "Variable 'bar' may not use 'void' type at 5:4")
            .put("whileStatementArrayType.ul", "Non-boolean condition ('int[5]') provided to statement at 7:11")
            .put("whileStatementCharType.ul", "Non-boolean condition ('char') provided to statement at 7:11")
            .put("whileStatementFloatType.ul", "Non-boolean condition ('float') provided to statement at 7:11")
            .put("whileStatementIntType.ul", "Non-boolean condition ('int') provided to statement at 7:21")
            .put("whileStatementStringType.ul", "Non-boolean condition ('string') provided to statement at 7:11")
            .put("whileStatementVoidType.ul", "Non-boolean condition ('void') provided to statement at 5:11")
            .put("wrongFunctionCallParameterType1.ul", "Parameter 'one' (#1) expects type 'float', not 'int' at 2:4")
            .put("wrongFunctionCallParameterType2.ul", "Parameter 'two' (#2) expects type 'char', not 'string' at 2:4")
            .put("wrongFunctionCallParameterType3.ul", "Parameter 'two' (#2) expects type 'char', not 'string' at 2:4")
            .put("wrongReturnType.ul", "Function 'foo' requires return type 'int' at 5:4")
            .put("wrongTypeInParens.ul", "Type 'float' cannot be assigned to 'int' at 6:8")
            .build();

    @Test
    public void testReject() {
        final File[] files = getTestFilesForGroup(Group.reject);
        assertEquals(REJECT_REASONS.size(), files.length);

        for (File file : files) {

            final String name = file.getName();
            assertTrue(REJECT_REASONS.containsKey(name));

            final String reason = REJECT_REASONS.get(name);
            boolean exceptionThrown = false;
            try {
                compile(file);
            } catch (SemanticException e) {
                assertEquals(reason, e.toString());
                exceptionThrown = true;
            } catch (Exception e) {
                fail(e.getMessage());
            }

            if (exceptionThrown == false) {
                fail(String.format("SemanticException expected for %s.", name));
            }

        }
    }

}
