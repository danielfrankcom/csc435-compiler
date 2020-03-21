
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
import ca.frankcom.csc435.compiler.ir.visit.print.PrettyPrintIrVisitor;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.apache.commons.cli.*;
import org.apache.commons.text.WordUtils;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Compiler {

    private static void printHelp(Options options) {
        new HelpFormatter().printHelp("compile [OPTIONS] <FILE>", options);
    }

    public static void main(String[] args) throws Exception {

        final Options options = new Options();
        options.addOption("h", "help", false, "Print help for command line arguments.");
        options.addOption("p", "print", true, "Output path of pretty-print file.");
        options.addOption("i", "ir", true, "Output path of IR generation.");

        final CommandLineParser argParser = new DefaultParser();
        final CommandLine commandLine;
        commandLine = argParser.parse(options, args);

        if (commandLine.hasOption("help")) {
            printHelp(options);
            return;
        }

        final List<File> toDeleteIfError = new LinkedList<>();

        final OutputStream prettyPrintOutput;
        if (commandLine.hasOption("print")) {
            final String outputPath = commandLine.getOptionValue("print");
            final File output = new File(outputPath);
            toDeleteIfError.add(output);
            prettyPrintOutput = new FileOutputStream(output);
        } else {
            // Special value to disable pretty-printing.
            prettyPrintOutput = null;
        }

        final OutputStream irGenOutput;
        if (commandLine.hasOption("ir")) {
            final String outputPath = commandLine.getOptionValue("ir");
            final File output = new File(outputPath);
            toDeleteIfError.add(output);
            irGenOutput = new FileOutputStream(output);
        } else {
            // Special value to disable ir generation.
            irGenOutput = null;
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
            compile(programName, input, prettyPrintOutput, irGenOutput, true);
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
    public static void compile(String inputFileName, InputStream input, OutputStream prettyPrintOutput,
                               OutputStream irGenOutput, boolean semanticCheck) throws Exception {

        final ANTLRInputStream antlrStream = new ANTLRInputStream(input);

        // The name of the grammar here is "Csc435",
        // so ANTLR generates Csc435Lexer and Csc435Parser
        final Csc435Lexer lexer = new Csc435Lexer(antlrStream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final Csc435Parser parser = new Csc435Parser(tokens);

        final Program program = parser.program();

        if (semanticCheck) {
            final SemanticCheckVisitor semanticVisitor = new SemanticCheckVisitor();
            program.accept(semanticVisitor);
        }

        if (irGenOutput != null) {
            final IrGenVisitor irVisitor = new IrGenVisitor(inputFileName);
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

    }

}
