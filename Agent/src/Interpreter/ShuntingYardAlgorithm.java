package Interpreter;

import Interpreter.Expressions.*;
import Interpreter.Expressions.Number;
import java.util.*;

public class ShuntingYardAlgorithm {
    public static double calc(List<String> exp){
        LinkedList<String> queue = new LinkedList<>();
        Stack<String> stack = new Stack<>();
        int size = exp.size();

        for(int i = 0; i<size; i++){
            switch (exp.get(i)) {
                case "+":
                case "-": {
                    while (!stack.isEmpty() && !stack.peek().equals("(")) {
                        queue.addLast(stack.pop());
                    }
                    stack.push(exp.get(i));
                    break;
                }
                case "(":
                case "/":
                case "*": {
                    stack.push(exp.get(i));
                    break;
                }
                case ")": {
                    while (!stack.isEmpty() && !stack.peek().equals("(")) queue.addLast(stack.pop());
                    stack.pop();
                    break;
                }
                default: {
                    queue.addLast(exp.get(i));
                    break;
                }
            }
        }
        while (!stack.isEmpty()){
            queue.addLast(stack.pop());
        }

        return CalculateExpTree(queue).calculate();
    }

    public static Expression CalculateExpTree(LinkedList<String> queue){
        Expression ret;
        if(queue.isEmpty())
            return new Number(0);
        String currentExp = queue.pollLast();

        switch (currentExp){
            case "+":{
                Expression right = CalculateExpTree(queue);
                Expression left = CalculateExpTree(queue);
                ret = new Plus(left, right);
                break;
            }
            case "-":{
                Expression right = CalculateExpTree(queue);
                Expression left = CalculateExpTree(queue);
                ret = new Minus(left, right);
                break;
            }
            case "*":{
                Expression right = CalculateExpTree(queue);
                Expression left = CalculateExpTree(queue);
                ret = new Mul(left, right);
                break;
            }
            case "/":{
                Expression right = CalculateExpTree(queue);
                Expression left = CalculateExpTree(queue);
                ret = new Div(left, right);
                break;
            }
            default:{
                ret = new Number(Double.parseDouble(currentExp));
                break;
            }
        }

        return ret;
    }

    public static double ConditionParser(List<String> condition) {

        LinkedList<String> queue = new LinkedList<>();
        Stack<String> stack = new Stack<>();
        int size = condition.size();
        HashSet<String> operators = new HashSet<>(Set.of(">","<",">=","<=","=="));

        for (int i = 0; i < size; i++) {
            switch (condition.get(i)) {
                case ">":
                case ">=":
                case "<":
                case "<=":
                case "==": {
                    stack.push(condition.get(i));
                    break;
                }
                case "&&":
                case "||": {
                    while (!stack.isEmpty() && operators.contains(stack.peek())) {
                        queue.addLast(stack.pop());
                    }
                    stack.push(condition.get(i));
                    break;
                }
                default: {
                    queue.addLast(condition.get(i));
                    break;
                }
            }
        }
        while (!stack.isEmpty()) {
            queue.addLast(stack.pop());
        }

        return CheckCondition(queue).calculate();
    }

    public static Expression CheckCondition(LinkedList<String> queue){
        Expression expression;
        if(queue.isEmpty())
            return new Number(0);
        String currentExp = queue.pollLast();

        switch (currentExp){
            case ">":
            case ">=":
            case "<":
            case "<=":
            case "==": {
                Expression right = CheckCondition(queue);
                Expression left = CheckCondition(queue);
                expression = new ConditionExpression(left, right, currentExp);
                break;
            }
            case "&&":{
                Expression right = CheckCondition(queue);
                Expression left = CheckCondition(queue);
                expression = new And(left, right);
                break;
            }
            case "||":{
                Expression right = CheckCondition(queue);
                Expression left = CheckCondition(queue);
                expression = new Or(left, right);
                break;
            }
            default:{
                expression = new Number(Double.parseDouble(currentExp));
                break;
            }
        }
        return expression;
    }
}
