package om.frankc.csc435.compiler;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        final ImmutableMap<Integer, File> inputMap = identifyTestFiles(outputs);

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
    public void testCompilerAccepts() throws IOException {

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

            System.out.printf("Testing %s -> %s\n", inputPath, output.getAbsolutePath());

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

}