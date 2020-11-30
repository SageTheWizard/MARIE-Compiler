import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MarieAssemblerTester {
    static String extension = ".mas";
    static String executableExtension = ".mex";
    static String file = "test" + extension;

    public static void main(String[] args) throws FileNotFoundException {
        java.io.Reader r = new java.io.FileReader(args[0]);
        Parser p = new Parser(r);

        try {
            if (p.yyparse() == 0) {
                System.out.println("Success");
                PrintWriter out = new PrintWriter(new File(args[0].split("\\.")[0] + executableExtension));
                for(String s : p.outputArr) {
                    out.println(s);
                }
                out.close();
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
