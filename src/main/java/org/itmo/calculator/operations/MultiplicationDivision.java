package org.itmo.calculator.operations;
import org.itmo.calculator.MathOperation;
public class MultiplicationDivision extends MathOperation {
    private final String SIGN = "*\\";

    @Override
    public String getSign(){return this.SIGN;}

    @Override
    public String calculate(long num1, long num2){
        return (num2 != 0) ? String.valueOf(num1 * num2 / num2) : "ERROR_0";
    }
}
