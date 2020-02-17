package om.frankc.csc435.compiler;

import com.google.common.collect.ImmutableMap;
import om.frankc.csc435.compiler.ast.Program;
import om.frankc.csc435.compiler.generated.Csc435Lexer;
import om.frankc.csc435.compiler.generated.Csc435Parser;
import om.frankc.csc435.compiler.visit.semantic.SemanticCheckVisitor;
import om.frankc.csc435.compiler.visit.semantic.SemanticException;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
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
        final ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(file));

        final Csc435Lexer lexer = new Csc435Lexer(input);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final Csc435Parser parser = new Csc435Parser(tokens);

        final Program program = parser.program();
        final SemanticCheckVisitor visitor = new SemanticCheckVisitor();
        program.accept(visitor);
    }

    @Test
    public void testAccept() throws Exception {
        final File[] files = getTestFilesForGroup(Group.accept);
        for (File file : files) {
            System.out.printf("Testing '%s'\n", file.getName());
            compile(file);
        }
    }

    private static final ImmutableMap<String, String> REJECT_REASONS = new ImmutableMap.Builder<String, String>()
            .put("arrayAssignmentNonArray.ul", "Type 'int' may not be indexed at 6:8")
            .put("arrayAssignmentNonIntIndex.ul", "Array index must be integer at 6:8")
            .put("arrayAssignmentWrongType.ul", "Type 'string' cannot be assigned to 'int[10]' at 6:13")
            .put("assignmentFromUndeclaredArray.ul", "Cannot access undeclared variable 'bar' at 6:10")
            .put("assignmentFromUndeclaredLiteral.ul", "Cannot access undeclared variable 'bar' at 6:10")
            .put("assignmentToWrongTypeArray.ul", "Type 'string' cannot be assigned to 'string[5]' at 6:10")
            .put("assignmentToWrongTypeLiteral.ul", "Type 'string' cannot be assigned to 'int' at 6:10")
            .put("assignmentWithoutDeclArray.ul", "Variable 'bar' not declared prior to assignment at 6:4")
            .put("assignmentWithoutDeclLiteral.ul", "Variable 'bar' not declared prior to assignment at 6:4")
            .put("duplicateFunctionDifferentTypes.ul", "Duplicate function 'foo' found at 7:8")
            .put("duplicateFunctionSameTypes.ul", "Duplicate function 'foo' found at 7:4")
            .put("duplicateParamDifferentTypes.ul", "Duplicate parameter name 'param' found at 4:26")
            .put("duplicateParamSameTypes.ul", "Duplicate parameter name 'duplicate' found at 4:34")
            .put("duplicateVariableDeclDifferentTypes.ul", "Duplicate variable named 'var' declared at 6:8")
            .put("duplicateVariableDeclSameTypes.ul", "Duplicate variable named 'var' declared at 6:8")
            .put("empty.ul", "No function 'main' found at 1:0")
            .put("emptyReturnNonVoidFunction.ul", "Function 'foo' requires return type 'int' at 5:4")
            .put("intReturnMain.ul", "Function 'main' must be of type 'void' at 1:0")
            .put("mainWithParams.ul", "Function 'main' may not have parameters at 1:9")
            .put("multipleMain.ul", "Duplicate function 'main' found at 4:5")
            .put("noMain.ul", "No function 'main' found at 1:0")
            .put("variableHideParam.ul", "Variable 'two' may not hide parameter at 5:8")
            .put("voidParamBeginning.ul", "Parameter type may not be 'void' at 4:9")
            .put("voidParamMiddle.ul", "Parameter type may not be 'void' at 4:29")
            .put("voidVariableDecl.ul", "Variable 'bar' may not use 'void' type at 5:4")
            .put("wrongReturnType.ul", "Function 'foo' requires return type 'int' at 5:4")
            .build();

    @Test
    public void testReject() {
        final File[] files = getTestFilesForGroup(Group.reject);
        assertEquals(REJECT_REASONS.size(), files.length);

        for (File file : files) {

            final String name = file.getName();
            assertTrue(REJECT_REASONS.containsKey(name));
            System.out.printf("Testing '%s'\n", name);

            final String reason = REJECT_REASONS.get(name);
            try {
                compile(file);
            } catch (SemanticException e) {
                assertEquals(reason, e.toString());
            } catch (Exception e) {
                fail(e.getMessage());
            }

        }
    }

}
