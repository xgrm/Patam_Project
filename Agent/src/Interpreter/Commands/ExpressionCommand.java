package Interpreter.Commands;

import java.util.ArrayList;
import Interpreter.Expressions.Expression;

public abstract class ExpressionCommand implements Expression {
   protected Command c;

   public ExpressionCommand(Command c) {
       this.c = c;
   }

   public double calculate(ArrayList<String> args, int index) {
       return (double) c.execute(args, index);
   }
}
