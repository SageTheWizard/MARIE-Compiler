import java.io.FileNotFoundException;

public class MarieAssemblerTester {
    static String file = "test.mas";

    public static void main(String[] args) throws FileNotFoundException {
        java.io.Reader r = new java.io.FileReader(file);
        Parser p = new Parser(r);

        try {
            if (p.yyparse() == 0) {
                System.out.println("Success");
                System.out.println(p.output);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
