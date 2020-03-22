package ca.frankcom.csc435.compiler;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CompilerTest {

    private static final ClassLoader CLASS_LOADER = CompilerTest.class.getClassLoader();
    private static final String TEST_FOLDER = "execution";

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

        final File[] inputs = getTestFilesForGroup(Group.input);
        final ImmutableMap<Integer, File> inputMap = identifyTestFiles(inputs);

        final File[] outputs = getTestFilesForGroup(Group.output);
        final ImmutableMap<Integer, File> outputMap = identifyTestFiles(outputs);

        assertEquals(inputMap.size(), outputMap.size());

        for (Integer inputNumber : inputMap.keySet()) {
            assertTrue(outputMap.containsKey(inputNumber));
            final File input = inputMap.get(inputNumber);
            final File output = outputMap.get(inputNumber);

            result.put(input, output);
        }

        return ImmutableMap.copyOf(result);
    }

    @Test
    public void testCodeExecution() throws Exception {

        final ImmutableMap<File, File> testFiles = getTestFiles();

        for (Map.Entry<File, File> entry : testFiles.entrySet()) {
            final File input = entry.getKey();
            final File expected = entry.getValue();

            final InputStream inputStream = new FileInputStream(input);

            final String inputPath = input.getAbsolutePath();
            final String expectedPath = expected.getAbsolutePath();

            final Path classDirPath = Files.createTempDirectory("compilerTest");
            try {

                final File outFile = File.createTempFile("compilerTest", "");
                outFile.deleteOnExit();
                final String outPath = outFile.getAbsolutePath();

                System.out.printf("Testing '%s' -> '%s' -> '%s'\n", inputPath, classDirPath.toString(), expectedPath);

                boolean success = false;
                try {
                    Compiler.compile("Test", inputStream, null, null,
                            null, classDirPath, true);

                    // The above line will not throw an exception if successful,
                    // but will also not return a result. We double check that
                    // we get past that line for the sake of the unit test.
                    success = true;

                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }

                assertTrue(success);

                final String javaCommand = String.format("java -cp %s Test", classDirPath.toString());
                System.out.println("Executing java.");
                final Process javaProcess = Runtime.getRuntime().exec(javaCommand);
                writeOutputTo(javaProcess, outPath);

                final String expectedText = FileUtils.readFileToString(expected, Charset.defaultCharset());
                final String actualText = FileUtils.readFileToString(outFile, Charset.defaultCharset());
                assertEquals(expectedText, actualText);
            } finally {
                final File[] files = classDirPath.toFile().listFiles();
                for (File file : files) {
                    Files.deleteIfExists(file.toPath());
                }
                Files.deleteIfExists(classDirPath);
            }

        }
    }

    private static void writeOutputTo(Process process, String filePath) throws Exception {
        final BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        final File codeGenFile = new File(filePath);
        final BufferedWriter outputWriter = new BufferedWriter(new FileWriter(codeGenFile));
        String line;
        while ((line = outputReader.readLine()) != null) {
            outputWriter.write(line + "\n");
        }
        outputWriter.close();
        process.waitFor();
    }

    @Ignore
    @Test
    public void compileCodeGen() throws Exception {
        final String PREFIX = "/home/daniel/docs/csc435/compiler/src/test/resources/execution/input/";
        final String INPUT = "001-helloWorld.ul";

        final InputStream inputStream = new FileInputStream(PREFIX + INPUT);
        final OutputStream irStream = new FileOutputStream("/tmp/output.ir");
        Compiler.compile("test", inputStream, null, irStream,
                null, null, true);

        // This file is a black-box test tool provided by the instructor, so cannot
        // be pulled from external resources. The binary is stored in the repo.
        final URL codeGenUrl = CLASS_LOADER.getResource("ir/codegen");
        final String codeGenExecutable = codeGenUrl.getPath();

        System.out.println(PREFIX + INPUT);
        final String codeGenCommand = String.format("%s --file=%s", codeGenExecutable, "/tmp/output.ir");
        System.out.println("Executing codegen.");
        final Process codeGenProcess = Runtime.getRuntime().exec(codeGenCommand);
        writeOutputTo(codeGenProcess, "/tmp/output.j");
    }

}
