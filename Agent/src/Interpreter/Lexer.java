package Interpreter;

import java.util.*;

public class Lexer {
    public static ArrayList<String> getTokens(String code) {
        ArrayList<String> tokens = new ArrayList<String>();
        Scanner scan = new Scanner(code);
        String line =" ";
        while (scan.hasNext()) {
            line = scan.nextLine();
            tokens.addAll(Arrays.stream(line.split(" ")).toList());
            tokens.add("\n");
        }
        scan.close();
        return tokens;
    }
}
