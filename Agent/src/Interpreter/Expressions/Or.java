package Interpreter.Expressions;

public class Or extends BinaryExpression{
    public Or(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double calculate() {
        return (left.calculate()==1 || right.calculate()==1)?1:0;
    }
}
