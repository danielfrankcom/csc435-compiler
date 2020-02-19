
package om.frankc.csc435.compiler;

import om.frankc.csc435.compiler.ast.Program;
import om.frankc.csc435.compiler.generated.Csc435Lexer;
import om.frankc.csc435.compiler.generated.Csc435Parser;
import om.frankc.csc435.compiler.visit.IAstVisitor;
import om.frankc.csc435.compiler.visit.print.PrettyPrintAstVisitor;
import om.frankc.csc435.compiler.visit.semantic.SemanticCheckVisitor;
import om.frankc.csc435.compiler.visit.semantic.SemanticException;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.List;
import java.util.function.Consumer;

public class Compiler {

    private static void printHelp(Options options) {
        new HelpFormatter().printHelp("commandName [OPTIONS] <FILE>", options);
    }

    public static void main(String[] args) throws Exception {

        final Options options = new Options();
        options.addOption("h", "help", false, "Print help for command line arguments.");
        options.addOption("p", "print", true, "Output path of pretty-print file.");

        final CommandLineParser argParser = new DefaultParser();
        final CommandLine commandLine;
        commandLine = argParser.parse(options, args);

        if (commandLine.hasOption("help")) {
            printHelp(options);
            return;
        }

        final OutputStream output;
        final File outputFile;
        if (commandLine.hasOption("print")) {
            final String outputPath = commandLine.getOptionValue("print");
            outputFile = new File(outputPath);
            output = new FileOutputStream(outputFile);
        } else {
            // Special value to disable pretty-printing.
            output = null;
            outputFile = null;
        }

        final List<String> remainingArgs = commandLine.getArgList();
        if (remainingArgs.size() != 1) {
            System.out.println("Too many arguments provided.\n");
            printHelp(options);
            return;
        }

        final String inputPath = remainingArgs.get(0);
        final File inputFile = new File(inputPath);
        final InputStream input = new FileInputStream(inputFile);

        // This may throw other exceptions, but
        // we want to fail fast so we let them go.
        try {
            compile(input, output, true);
        } catch (SemanticException e) {
            System.out.println(e.toString());

            if (outputFile != null) {
                //noinspection ResultOfMethodCallIgnored
                outputFile.delete();
            }
            System.exit(1);
        }
    }

    /**
     * This method provides separation of compilation logic while doubling as a convenience for unit tests.
     */
    public static void compile(InputStream input, OutputStream output, boolean semanticCheck) throws Exception {

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

        if (output != null) {
            final Consumer<String> consumer = (string) -> {
                try {
                    output.write(string.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };

            final IAstVisitor<Void> printVisitor = new PrettyPrintAstVisitor(consumer);
            program.accept(printVisitor);

            output.flush();
            output.close();
        }

    }

}
