package MARIE.Compiler;

import java.util.HashMap;

public class Scope {
    private HashMap<String, String> symbolTable;
    private Scope prev;

    public Scope() {
        this.symbolTable = new HashMap<>();
        this.prev = null;
    }

    public Scope(Scope prev) {
        this.symbolTable = new HashMap<>();
        this.prev = prev;
    }

    public String get(String name) {
        if (!this.symbolTable.containsKey(name) && this.prev != null) {
            return this.prev.get(name);
        }

        return this.symbolTable.getOrDefault(name, null);
    }

    public boolean put(String name, String val) {
        if (this.symbolTable.containsKey(name)) {
            return false;
        }
        this.symbolTable.put(name, val);
        return true;
    }
}
