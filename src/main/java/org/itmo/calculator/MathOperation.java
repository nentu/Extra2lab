package org.itmo.calculator;

public abstract class MathOperation {
    public abstract String getSign();
    public abstract String calculate(long num1, long num2);
}
