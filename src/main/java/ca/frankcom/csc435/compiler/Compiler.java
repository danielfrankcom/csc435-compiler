
package ca.frankcom.csc435.compiler;

import ca.frankcom.csc435.compiler.ast.Program;
import ca.frankcom.csc435.compiler.ast.visit.IAstVisitor;
import ca.frankcom.csc435.compiler.ast.visit.irgen.IrGenVisitor;
import ca.frankcom.csc435.compiler.ast.visit.print.PrettyPrintAstVisitor;
import ca.frankcom.csc435.compiler.ast.visit.semantic.SemanticCheckVisitor;
import ca.frankcom.csc435.compiler.ast.visit.semantic.SemanticException;
import ca.frankcom.csc435.compiler.generated.Csc435Lexer;
import ca.frankcom.csc435.compiler.generated.Csc435Parser;
import ca.frankcom.csc435.compiler.ir.IrProgram;
import ca.frankcom.csc435.compiler.ir.visit.IIrVisitor;
import ca.frankcom.csc435.compiler.ir.visit.jasmingen.JasminGenVisitor;
import ca.frankcom.csc435.compiler.ir.visit.print.PrettyPrintIrVisitor;
import ca.frankcom.csc435.compiler.jasmin.JProgram;
import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;
import ca.frankcom.csc435.compiler.jasmin.visit.print.PrettyPrintJasminVisitor;
import jasmin.Main;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.apache.commons.cli.*;
import org.apache.commons.text.WordUtils;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Compiler {

    private static void printHelp(Options options) {
        new HelpFormatter().printHelp("compile [OPTIONS] <FILE>", options);
    }

    /**
     * Generate an {@link OutputStream} for the specified option.
     *
     * @param commandLine     A {@link CommandLine} object that provides access to the specified options.
     * @param option          A {@link String} that identifies the option to examine.
     * @param toDeleteIfError A {@link List} that contains {@link File} objects to be deleted in the case of a
     *                        compile failure.
     * @return The generated {@link OutputStream}, or {@code null} to indicate that the option was not requested.
     */
    private static OutputStream getOutput(CommandLine commandLine, String option, List<File> toDeleteIfError)
            throws FileNotFoundException {
        assert commandLine != null;
        assert option != null;
        assert toDeleteIfError != null;

        final OutputStream outputStream;
        if (commandLine.hasOption(option)) {
            final String outputPath = commandLine.getOptionValue(option);
            final File output = new File(outputPath);
            toDeleteIfError.add(output);
            outputStream = new FileOutputStream(output);
        } else {
            // Special value that indicates no output.
            outputStream = null;
        }

        return outputStream;
    }

    public static void main(String[] args) throws Exception {
        assert args != null;

        final Options options = new Options();
        options.addOption("h", "help", false, "Print help for command line arguments.");
        options.addOption("p", "print", true, "Output path of pretty-print file.");
        options.addOption("i", "ir", true, "Output path of IR generation.");
        options.addOption("j", "jasmin", true, "Output path of Jasmin generation.");
        options.addOption("c", "class", true, "Output directory of Java class file generation.");

        final CommandLineParser argParser = new DefaultParser();
        final CommandLine commandLine;
        commandLine = argParser.parse(options, args);

        if (commandLine.hasOption("help")) {
            printHelp(options);
            return;
        }

        final List<File> toDeleteIfError = new LinkedList<>();

        final OutputStream prettyPrintOutput = getOutput(commandLine, "print", toDeleteIfError);
        final OutputStream irGenOutput = getOutput(commandLine, "ir", toDeleteIfError);
        final OutputStream jasminGenOutput = getOutput(commandLine, "jasmin", toDeleteIfError);

        final Path classGenDirectory;
        if (commandLine.hasOption("class")) {
            classGenDirectory = Paths.get(commandLine.getOptionValue("class"));
        } else {
            // Special value that indicates no output.
            classGenDirectory = null;
        }

        final List<String> remainingArgs = commandLine.getArgList();
        if (remainingArgs.size() <= 0) {
            printHelp(options);
            return;
        } else if (remainingArgs.size() != 1) {
            System.out.println("Too many arguments provided.\n");
            printHelp(options);
            return;
        }

        final String inputPath = remainingArgs.get(0);
        final File inputFile = new File(inputPath);
        final String inputFileName = inputFile.getName();
        final InputStream input = new FileInputStream(inputFile);

        String programName = inputFileName.replaceAll(".ul|[^a-zA-Z]", "");
        if (programName.length() == 0) {
            programName = "program";
        }
        programName = WordUtils.capitalize(programName);

        // This may throw other exceptions, but we want to fail fast so we let them go.
        try {
            compile(programName, input, prettyPrintOutput, irGenOutput, jasminGenOutput, classGenDirectory, true);
        } catch (SemanticException e) {
            System.out.println(e.toString());

            for (File file : toDeleteIfError) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }

            System.exit(1);
        }
    }

    /**
     * This method provides separation of compilation logic while doubling as a convenience for unit tests.
     */
    public static void compile(String programName, InputStream input, OutputStream prettyPrintOutput,
                               OutputStream irGenOutput, OutputStream jasminGenOutput,
                               Path classGenDirectory, boolean semanticCheck)
            throws Exception {
        assert programName != null;
        assert input != null;

        final ANTLRInputStream antlrStream = new ANTLRInputStream(input);

        // The name of the grammar here is "Csc435",
        // so ANTLR generates Csc435Lexer and Csc435Parser
        final Csc435Lexer lexer = new Csc435Lexer(antlrStream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final Csc435Parser parser = new Csc435Parser(tokens);

        final Program program = parser.program();

        // This flag supports legacy tests but does not make sense
        // in practice. The external compiler flags do not allow a
        // false value here.
        if (semanticCheck) {
            final SemanticCheckVisitor semanticVisitor = new SemanticCheckVisitor();
            program.accept(semanticVisitor);
        }

        if (prettyPrintOutput != null) {
            final Consumer<String> consumer = (string) -> {
                try {
                    prettyPrintOutput.write(string.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };

            final IAstVisitor<Void> printVisitor = new PrettyPrintAstVisitor(consumer);
            program.accept(printVisitor);

            prettyPrintOutput.flush();
            prettyPrintOutput.close();
        }

        if (irGenOutput != null) {
            final IrGenVisitor irVisitor = new IrGenVisitor(programName);
            program.accept(irVisitor);
            final IrProgram irProgram = irVisitor.getProgram();

            final Consumer<String> consumer = (string) -> {
                try {
                    irGenOutput.write(string.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };

            final IIrVisitor<Void> printVisitor = new PrettyPrintIrVisitor(consumer);
            irProgram.accept(printVisitor);

            irGenOutput.flush();
            irGenOutput.close();
        }

        if (jasminGenOutput != null) {
            final IrGenVisitor irVisitor = new IrGenVisitor(programName);
            program.accept(irVisitor);
            final IrProgram irProgram = irVisitor.getProgram();

            final JasminGenVisitor genVisitor = new JasminGenVisitor();
            irProgram.accept(genVisitor);
            final JProgram jasminProgram = genVisitor.getProgram();

            final Consumer<String> consumer = (string) -> {
                try {
                    jasminGenOutput.write(string.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };

            final IJasminVisitor<Void> printVisitor = new PrettyPrintJasminVisitor(consumer);
            jasminProgram.accept(printVisitor);

            jasminGenOutput.flush();
            jasminGenOutput.close();
        }

        if (classGenDirectory != null) {
            final IrGenVisitor irVisitor = new IrGenVisitor(programName);
            program.accept(irVisitor);
            final IrProgram irProgram = irVisitor.getProgram();

            final JasminGenVisitor genVisitor = new JasminGenVisitor();
            irProgram.accept(genVisitor);
            final JProgram jasminProgram = genVisitor.getProgram();

            final File tempJasminFile = File.createTempFile("compiler", null);
            tempJasminFile.deleteOnExit();
            final OutputStream tempJasminOutput = new FileOutputStream(tempJasminFile);

            final Consumer<String> consumer = (string) -> {
                try {
                    tempJasminOutput.write(string.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };

            final IJasminVisitor<Void> printVisitor = new PrettyPrintJasminVisitor(consumer);
            jasminProgram.accept(printVisitor);

            tempJasminOutput.flush();
            tempJasminOutput.close();

            // Convert from Jasmin code to class file.
            final String[] jasminArgs = {tempJasminFile.getAbsolutePath(), "-d", classGenDirectory.toString()};
            Main.main(jasminArgs);
        }

    }

}
