package Model.Interpreter.Expressions;

public class And extends BinaryExpression {
    public And(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double calculate() {
        return (left.calculate()==1&&right.calculate()==1)?1:0;
    }
}
