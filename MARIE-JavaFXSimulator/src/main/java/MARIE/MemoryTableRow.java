package MARIE;

public class MemoryTableRow {
    private String zero;
    private String one;
    private String two;
    private String three;
    private String four;
    private String five;
    private String six;
    private String seven;
    private String eight;
    private String nine;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;

    private String rowHexString;

    private int row;

    private int[] memory;

    public MemoryTableRow(int row, int[] memory) {
        this.row = row;
        this.memory = memory;

        rowHexString = Integer.toHexString(row).toUpperCase() + "X";
    }

    public void update() {
        int memOffset = row * 16;
        zero = padLeft(Integer.toHexString(memory[memOffset]).toUpperCase());
        one = padLeft(Integer.toHexString(memory[memOffset + 1]).toUpperCase());
        two = padLeft(Integer.toHexString(memory[memOffset + 2]).toUpperCase());
        three = padLeft(Integer.toHexString(memory[memOffset + 3]).toUpperCase());
        four = padLeft(Integer.toHexString(memory[memOffset + 4]).toUpperCase());
        five = padLeft(Integer.toHexString(memory[memOffset + 5]).toUpperCase());
        six = padLeft(Integer.toHexString(memory[memOffset + 6]).toUpperCase());
        seven = padLeft(Integer.toHexString(memory[memOffset + 7]).toUpperCase());
        eight = padLeft(Integer.toHexString(memory[memOffset + 8]).toUpperCase());
        nine = padLeft(Integer.toHexString(memory[memOffset + 9]).toUpperCase());
        a = padLeft(Integer.toHexString(memory[memOffset + 10]).toUpperCase());
        b = padLeft(Integer.toHexString(memory[memOffset + 11]).toUpperCase());
        c = padLeft(Integer.toHexString(memory[memOffset + 12]).toUpperCase());
        d = padLeft(Integer.toHexString(memory[memOffset + 13]).toUpperCase());
        e = padLeft(Integer.toHexString(memory[memOffset + 14]).toUpperCase());
        f = padLeft(Integer.toHexString(memory[memOffset + 15]).toUpperCase());
    }

    public String getZero() {
        return zero;
    }

    public String getOne() {
        return one;
    }

    public String getTwo() {
        return two;
    }

    public String getThree() {
        return three;
    }

    public String getFour() {
        return four;
    }

    public String getFive() {
        return five;
    }

    public String getSix() {
        return six;
    }

    public String getSeven() {
        return seven;
    }

    public String getEight() {
        return eight;
    }

    public String getNine() {
        return nine;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    public String getD() {
        return d;
    }

    public String getE() {
        return e;
    }

    public String getF() {
        return f;
    }

    public String getRowHexString() {
        return rowHexString;
    }

    private String padLeft(String toPad) {
        StringBuilder toPadBuilder = new StringBuilder(toPad);
        for(int i = toPadBuilder.length(); i < 4; i++) {
            toPadBuilder.insert(0, '0');
        }
        return toPadBuilder.toString();
    }
}
