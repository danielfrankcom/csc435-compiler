
package om.frankc.csc435.compiler;

import om.frankc.csc435.compiler.generated.csc435Lexer;
import om.frankc.csc435.compiler.generated.csc435Parser;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

import java.io.FileInputStream;

public class Compiler {

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            System.out.println("Usage: Compiler filename.ul");
            throw new IllegalArgumentException();
        }

        final ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(args[0]));

        // The name of the grammar here is "ulNoActions",
        // so ANTLR generates ulNoActionsLexer and ulNoActionsParser
        final csc435Lexer lexer = new csc435Lexer(input);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final csc435Parser parser = new csc435Parser(tokens);

        // This may throw an exception however
        // we want to fail fast so we let it go.
        parser.program();
    }

}
