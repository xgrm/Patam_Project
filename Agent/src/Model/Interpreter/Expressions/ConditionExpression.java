package Model.Interpreter.Expressions;
public class ConditionExpression extends BinaryExpression{

    String operator;

    public ConditionExpression(Expression left, Expression right,String operator) {
        super(left, right);
        this.operator = operator;
    }

    @Override
    public double calculate() {
        switch (operator){
            case "<":
                return (left.calculate() < right.calculate())?1:0;

            case "<=":{
                return (left.calculate() <= right.calculate())?1:0;
            }
            case ">":{
                return (left.calculate() > right.calculate())?1:0;
            }
            case ">=":{
                return (left.calculate() >= right.calculate())?1:0;
            }
            case "==":{
                return (left.calculate() == right.calculate())?1:0;
            }
        }
        return -1;
    }
}