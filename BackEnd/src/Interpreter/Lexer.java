package Interpreter;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Lexer {
    public static List<String> getTokens(String code) {
        List<String> tokens = new LinkedList<String>();
        Scanner scan = new Scanner(code);
        while (scan.hasNext())
            tokens.add(scan.next());
        scan.close();
        return tokens;
    }
}
