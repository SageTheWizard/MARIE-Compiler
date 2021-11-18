package MARIE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MARIEAssembler {
    public Parser p;
    public static void main(String[] args) throws Exception {
        if(args.length > 0) {
            MARIEAssembler assembler = new MARIEAssembler();
            System.out.println("Success");
            PrintWriter out = new PrintWriter(new File(args[0].split("\\.")[0] + '.' + MARIEValues.EXECUTABLE_EXTENSION));
            String[] output = assembler.assemble(new File(args[0]));
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

    public String[] assemble(File toAssemble) throws Exception {
        java.io.Reader r = new java.io.FileReader(toAssemble);
        p = new Parser(r);
            if (p.yyparse() == 0) {
                return p.outputArr;
            }
        return null;
    }
}
