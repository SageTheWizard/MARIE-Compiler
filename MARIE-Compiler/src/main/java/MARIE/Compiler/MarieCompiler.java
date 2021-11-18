package MARIE.Compiler;

import java.io.FileNotFoundException;
import java.io.FileWriter;

public class MarieCompiler {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Must provide file name.");
            System.err.println("Type \"java -jar MarieCompiler.jar -h\" for help.");
            return;
        }
        if (args[0].equals("-h")) {
            System.out.println("Usage: java -jar MarieCompiler.jar filename.minc");
            System.out.println("Compiler Written by: Jacob Gallucci");
            System.out.println("Assembler and Simulator Written by: Richard Bowser");
            System.out.println("MARIE Created by: Dr. Linda Null");
            return;
        }
        java.io.Reader r = null;
        try {
            r = new java.io.FileReader(args[0]);
        } catch (FileNotFoundException e) {
            System.err.println("Error: Cannot find file: " + args[0]);
            return;
        }

        int semanticCheck = parseForSemantics(r);
        if (semanticCheck != 0) {
            return;
        }
        System.out.println("Generating Code: Intermediate Code will be in MRE_OUT.mre");
        try {
            generateCode(args[0]);
        } catch (Exception e) {
            System.err.println("ERROR OCCURRED GENERATING INTERMEDIATE CODE!!!");
            System.err.println("This should not have failed, please send the stack trace and code to Jacob Gallucci at contact@jacobgallucci.us or jag6248@psu.edu");
            e.printStackTrace();
            return;
        }

        System.out.println("Code Generated Successfully!");

    }
    private static int parseForSemantics(java.io.Reader r) {
        ParserICGN semantics = new ParserICGN(r);
        int result = 0;
        try {
            result = semantics.yyparse();
        } catch (Exception e) {
            System.err.println("ERROR OCCURRED! Please see the message");
            System.err.println("If the error is a non-compiler error, please contact Jacob Gallucci at contact@jacobgallucci.us or jag6248@psu.edu");
            e.printStackTrace();
            result = -1;
        }

        if (result != 0) {
            System.err.println("Exiting Compiler");
            return -1;
        }
        return 0;
    }

    private static void generateCode(String file) throws Exception {
        java.io.Reader r = new java.io.FileReader(file);
        ParserICGN marieParserICGN = new ParserICGN(r);
        marieParserICGN.yyparse();
        FileWriter writer = new FileWriter("MRE_OUT.mre");
        writer.write(ParserICGN.tree.getCode() + "END");
        writer.close();
    }
}
