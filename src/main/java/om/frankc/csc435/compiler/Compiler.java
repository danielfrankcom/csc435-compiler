
package om.frankc.csc435.compiler;

import om.frankc.csc435.compiler.visit.IAstVisitor;
import om.frankc.csc435.compiler.visit.PrettyPrintAstVisitor;
import om.frankc.csc435.compiler.ast.Program;
import om.frankc.csc435.compiler.generated.Csc435Lexer;
import om.frankc.csc435.compiler.generated.Csc435Parser;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

import java.io.*;
import java.util.function.Consumer;

public class Compiler {

    public static void main(String[] args) throws Exception {

        if (args.length != 1 && args.length != 3) {
            System.out.println("Usage: Compiler filename.ul [-p output/prettyPrintFile.ul]");
            throw new IllegalArgumentException();
        }

        final File prettyPrintOutput;
        if (args.length > 1) {
            assert "-p".equals(args[1]);
            prettyPrintOutput = new File(args[2]);
        } else {
            prettyPrintOutput = null;
        }

        final ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(args[0]));

        // The name of the grammar here is "Csc435",
        // so ANTLR generates Csc435Lexer and Csc435Parser
        final Csc435Lexer lexer = new Csc435Lexer(input);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final Csc435Parser parser = new Csc435Parser(tokens);

        // This may throw an exception however
        // we want to fail fast so we let it go.
        final Program program = parser.program();

        // Optional pretty-print functionality.
        if (prettyPrintOutput != null) {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(prettyPrintOutput));
            final Consumer<String> output = (string) -> {
                try {
                    writer.write(string);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };

            final IAstVisitor<Void> visitor = new PrettyPrintAstVisitor(output);
            program.accept(visitor);

            writer.flush();
            writer.close();
        }
    }

}
