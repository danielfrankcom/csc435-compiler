package om.frankc.csc435.compiler;

import com.google.common.collect.ImmutableMap;
import om.frankc.csc435.compiler.ast.*;
import om.frankc.csc435.compiler.generated.Csc435Lexer;
import om.frankc.csc435.compiler.generated.Csc435Parser;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class AstTest {

    private static final ClassLoader CLASS_LOADER = AstTest.class.getClassLoader();

    private static final String TEST_FOLDER = "ast";

    private enum Group {
        input,
        output
    }

    /**
     * Get an array of {@link File} objects representing the set of files within the specified testing group
     *
     * @param group A {@link Group} that uniquely identifies the set of files to address.
     * @return The generated {@link File[]}.
     */
    private static File[] getTestFilesForGroup(Group group) {
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

    /**
     * Uses the first three characters of the filename to produce an {@link ImmutableMap} that links the test number
     * to the {@link File}.
     *
     * @return The generated {@link ImmutableMap}.
     */
    private static ImmutableMap<Integer, File> identifyTestFiles(File[] files) {
        final Map<Integer, File> map = new HashMap<>();

        for (File file : files) {
            final String name = file.getName();
            final String numeric = name.substring(0, 3);
            final int testNumber = Integer.parseInt(numeric);

            map.put(testNumber, file);
        }

        return ImmutableMap.copyOf(map);
    }

    /**
     * Get an {@link ImmutableMap} that links input {@link File} objects to their corresponding output.
     *
     * @return The generated {@link ImmutableMap}.
     */
    private static ImmutableMap<File, File> getTestFiles() {
        final Map<File, File> result = new HashMap<>();

        final File[] outputs = getTestFilesForGroup(Group.output);
        final ImmutableMap<Integer, File> outputMap = identifyTestFiles(outputs);

        final File[] inputs = getTestFilesForGroup(Group.input);
        final ImmutableMap<Integer, File> inputMap = identifyTestFiles(inputs);

        assertEquals(inputMap.size(), outputMap.size());

        for (Integer inputNumber : inputMap.keySet()) {
            assertTrue(outputMap.containsKey(inputNumber));
            final File input = inputMap.get(inputNumber);
            final File output = outputMap.get(inputNumber);

            result.put(input, output);
        }

        return ImmutableMap.copyOf(result);
    }

    private static void printFile(File file) throws IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = reader.readLine();
        while (line != null) {
            System.out.println(line);
            line = reader.readLine();
        }
    }

    @Test
    public void testPrettyPrint() throws IOException {

        final ImmutableMap<File, File> testFiles = getTestFiles();


        for (Map.Entry<File, File> entry : testFiles.entrySet()) {
            final File input = entry.getKey();
            final File output = File.createTempFile("compilerTest", null);
            output.deleteOnExit();
            final File expected = entry.getValue();

            final String inputPath = input.getAbsolutePath();
            final String outputPath = output.getAbsolutePath();
            final String expectedPath = expected.getAbsolutePath();

            final String[] arguments = {inputPath, "-p", outputPath};

            System.out.printf("Testing %s -> %s -> %s\n", inputPath, outputPath, expectedPath);

            boolean success = false;
            try {
                Compiler.main(arguments);

                // The above line will not throw an exception if successful,
                // but will also not return a result. We double check that
                // we get past that line for the sake of the unit test.
                success = true;

            } catch (Throwable e) {
                throw new RuntimeException(e);
            }

            assertTrue(success);
            final boolean filesEqual = FileUtils.contentEquals(expected, output);

            if (filesEqual == false) {
                System.out.println("Expected:");
                printFile(expected);
                System.out.println("Actual:");
                printFile(output);
                fail();
            }
        }
    }

    private static Program getProgram(URL inputLocation) throws Exception {
        final ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(inputLocation.getPath()));

        final Csc435Lexer lexer = new Csc435Lexer(input);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final Csc435Parser parser = new Csc435Parser(tokens);

        return parser.program();
    }

    @Test
    public void testAstConstruction() throws Exception {
        final URL input = CLASS_LOADER.getResource("ast/input/012-operatorPrecedence.ul");
        final Program program = getProgram(input);

        final Program expected = new Program(
                new FunctionList(
                        new ArrayList<Function>() {{
                            add(
                                    new Function(
                                            new FunctionDeclaration(
                                                    new Type(Type.Name.Integer, 1, 0),
                                                    new Identifier("bar", 1, 4),
                                                    new FormalParameterList(Collections.emptyList(), 1, 8),
                                                    1,
                                                    0
                                            ),
                                            new FunctionBody(
                                                    new VariableDeclarationList(Collections.emptyList(), 2, 0),
                                                    new StatementList(
                                                            new ArrayList<Statement>() {{
                                                                add(
                                                                        new ArrayAssignment(
                                                                                new Identifier("id", 3, 4),
                                                                                new IntegerLiteral(5, 3, 7),
                                                                                new MultiplyExpression(
                                                                                        new ParenExpression(
                                                                                                new EqualityExpression(
                                                                                                        new ParenExpression(
                                                                                                                new AddExpression(
                                                                                                                        new IntegerLiteral(6, 3, 14),
                                                                                                                        new MultiplyExpression(
                                                                                                                                new IntegerLiteral(6, 3, 18),
                                                                                                                                new IntegerLiteral(4, 3, 22),
                                                                                                                                3,
                                                                                                                                20
                                                                                                                        ),
                                                                                                                        3,
                                                                                                                        16
                                                                                                                ),
                                                                                                                3,
                                                                                                                13
                                                                                                        ),
                                                                                                        new IntegerLiteral(5, 3, 28),
                                                                                                        3,
                                                                                                        25
                                                                                                ),
                                                                                                3,
                                                                                                12
                                                                                        ),
                                                                                        new ParenExpression(
                                                                                                new SubtractExpression(
                                                                                                        new IntegerLiteral(5, 3, 34),
                                                                                                        new IntegerLiteral(6, 3, 38),
                                                                                                        3,
                                                                                                        36
                                                                                                ),
                                                                                                3,
                                                                                                33
                                                                                        ),
                                                                                        3,
                                                                                        31
                                                                                ),
                                                                                3,
                                                                                4
                                                                        )
                                                                );
                                                                add(
                                                                        new AssignmentStatement(
                                                                                new Identifier("id", 4, 4),
                                                                                new AddExpression(
                                                                                        new IntegerLiteral(5, 4, 9),
                                                                                        new MultiplyExpression(
                                                                                                new IntegerLiteral(4, 4, 13),
                                                                                                new IntegerLiteral(3, 4, 17),
                                                                                                4,
                                                                                                15
                                                                                        ),
                                                                                        4,
                                                                                        11
                                                                                ),
                                                                                4,
                                                                                4
                                                                        )
                                                                );
                                                                add(
                                                                        new ArrayAssignment(
                                                                                new Identifier("id", 5, 4),
                                                                                new IntegerLiteral(3, 5, 7),
                                                                                new AddExpression(
                                                                                        new MultiplyExpression(
                                                                                                new IntegerLiteral(5, 5, 12),
                                                                                                new IntegerLiteral(4, 5, 16),
                                                                                                5,
                                                                                                14
                                                                                        ),
                                                                                        new IntegerLiteral(3, 5, 20),
                                                                                        5,
                                                                                        18
                                                                                ),
                                                                                5,
                                                                                4
                                                                        )
                                                                );
                                                            }},
                                                            2,
                                                            0
                                                    ),
                                                    2,
                                                    0
                                            ),
                                            1,
                                            0
                                    )
                            );
                        }},
                        1,
                        0
                ),
                1,
                0
        );

        assertEquals(expected, program);
    }

}