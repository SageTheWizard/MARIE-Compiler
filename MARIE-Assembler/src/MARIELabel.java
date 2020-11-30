public class MARIELabel {
    public int lineno;
    public String name;

    public MARIELabel(int lineno, String name) {
        this.lineno = lineno;
        this.name = name;
    }

    @Override
    public String toString() {
        return "" + lineno;
    }
}
