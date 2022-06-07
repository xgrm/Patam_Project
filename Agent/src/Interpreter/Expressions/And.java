package Interpreter.Expressions;

import java.util.function.BinaryOperator;

public class And extends BinaryExpression {


    public And(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double calculate() {
        return (left.calculate()==1&&right.calculate()==1)?1:0;
    }
}
