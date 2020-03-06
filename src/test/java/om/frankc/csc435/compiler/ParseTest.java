package om.frankc.csc435.compiler;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ParseTest {

    private static final ClassLoader CLASS_LOADER = ParseTest.class.getClassLoader();
    private static final String TEST_FOLDER = "parse";

    private enum Group {
        accept,
        reject
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

    private static void compile(File inputFile) throws Exception {
        final InputStream input = new FileInputStream(inputFile);
        Compiler.compile("", input, null, null, false);
    }

    @Test
    public void testCompilerAccepts() {
        final File[] acceptFiles = getTestFilesForGroup(Group.accept);

        for (File file : acceptFiles) {

            System.out.printf("Testing '%s'\n", file.getName());

            boolean success = false;
            try {
                compile(file);

                // The above line will not throw an exception if successful,
                // but will also not return a result. We double check that
                // we get past that line for the sake of the unit test.
                success = true;

            } catch (Throwable e) {
                throw new RuntimeException(e);
            }

            assertTrue(success);
        }
    }

    @Test
    public void testCompilerRejects() {
        final File[] rejectFiles = getTestFilesForGroup(Group.reject);

        for (File file : rejectFiles) {

            System.out.printf("Testing %s\n", file.getName());

            Throwable exception = null;
            try {
                compile(file);
            } catch (Throwable e) {
                exception = e;
            }

            assertNotNull(exception);
        }
    }

}