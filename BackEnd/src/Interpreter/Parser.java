package Interpreter;

import java.util.HashMap;

public class Parser {
    private HashMap<String, Double> symTable;
    private HashMap<String, String> bindMap;

    public Parser() {
        symTable = new HashMap<>();
        bindMap = new HashMap<>();
    }
}
