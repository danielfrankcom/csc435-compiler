
package om.frankc.csc435.compiler;

import om.frankc.csc435.compiler.ast.IAstVisitor;
import om.frankc.csc435.compiler.ast.PrintAstVisitor;
import om.frankc.csc435.compiler.ast.Program;
import om.frankc.csc435.compiler.generated.Csc435Lexer;
import om.frankc.csc435.compiler.generated.Csc435Parser;
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
        final Csc435Lexer lexer = new Csc435Lexer(input);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final Csc435Parser parser = new Csc435Parser(tokens);

        // This may throw an exception however
        // we want to fail fast so we let it go.
        final Program program = parser.program();
        final IAstVisitor visitor = new PrintAstVisitor();
        program.accept(visitor);
    }

}
