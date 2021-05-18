package MARIE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MARIEAssembler {

    public static void main(String[] args) throws FileNotFoundException {
        if(args.length > 0) {
            System.out.println("Success");
            PrintWriter out = new PrintWriter(new File(args[0].split("\\.")[0] + '.' + MARIEValues.EXECUTABLE_EXTENSION));
            String[] output = assemble(new File(args[0]));
            if(output != null) {
                for (String s : output) {
                    out.println(s);
                }
                out.close();
            }
            else {
                System.err.println("Error while assembling file");
            }
        }
        else {
            System.err.println("Error: you must provide a file as an argument to use the command line assembler");
        }
    }

    public static String[] assemble(File toAssemble) throws FileNotFoundException {
        java.io.Reader r = new java.io.FileReader(toAssemble);
        Parser p = new Parser(r);
        try {
            if (p.yyparse() == 0) {
                return p.outputArr;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
