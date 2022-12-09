package org.itmo.calculator;

import org.itmo.calculator.operations.*;

public final class SpecialCalculator {
    private String[] memory;
    private String lastOperation = "0";
    private final MathOperation[] OperationsList = new MathOperation[9];


    public SpecialCalculator(int memoryCellsSize) {
        this.memory = new String[ (memoryCellsSize < 0 || memoryCellsSize > 100) ? 0: memoryCellsSize ];
        for (int i = 0; i < this.memory.length; i++)
            this.memory[i] = "0";

        OperationsList[0] = new Addition();
        OperationsList[1] = new Division();
        OperationsList[2] = new DivisionRemainder();
        OperationsList[3] = new DoubleAddition();
        OperationsList[4] = new DoubleSubtraction();
        OperationsList[5] = new Exponentiation();
        OperationsList[6] = new Multiplication();
        OperationsList[7] = new MultiplicationDivision();
        OperationsList[8] = new Subtraction();
    }

    public String calculate(String expression) {
        expression = expression.replace("/", "\\") + " ";


        while (expression.split(" ").length > 2){
            String[] tmp = expression.split(" ", 4);
            String first_expression = String.format("%s %s %s", tmp[0], tmp[1], tmp[2]);
            String ans = calculateOneExpression(first_expression);
            if (!ans.contains("ERROR")) {
                expression = String.format("%s %s", ans, tmp[3]);
            }
            else {
                expression = ans;
                break;
            }
        }
        expression = expression.strip();
        this.lastOperation = expression;
        return expression; // не забудьте заменить "" на результат операции
    }

    private String getFromMemory(String tIndex){
        int index = Integer.parseInt(tIndex);
        if (0 <= index && index < memory.length) {
            return this.memory[index];
        }
        else{
            return "ERROR_1";
        }
    }

    private String getOperationNum(String address){
        if (!address.contains("M")) {
            return String.valueOf(Long.parseLong(address));
        }
        else{
            return getFromMemory(address.replace("M", ""));
        }
    }

    private String calculateOneExpression(String expression){

        String[] listWords = expression.split(" ", 3);
        long num1;
        long num2;

        //get num1
        {
            String tmp = getOperationNum(listWords[0]);
            if (!tmp.equals("ERROR_1")) {
                num1 = Long.parseLong(tmp);
            } else return tmp;
        }

        //get Sign
        String sign =  listWords[1];

        {
            String tmp = getOperationNum(listWords[2]);
            if (!tmp.equals("ERROR_1")) {
                num2 = Long.parseLong(tmp);
            } else return tmp;
        }

        String ans = "0";

        //Solve expression
        for (int i =0; i < OperationsList.length; i++){
            MathOperation operation = OperationsList[i];
            if (sign.equals(operation.getSign())){
                ans = operation.calculate(num1, num2);
            }
        }

        return ans;
    }

    public String putLastResultInMemory(int memoryCellIndex) {
        if (0 <= memoryCellIndex && memoryCellIndex < memory.length) {
            this.memory[memoryCellIndex] = this.lastOperation;
            return String.valueOf(lastOperation);
        }
        else{
            return "ERROR_1";
        }
    }

    public String resetMemory(int memoryCellIndex) {
        if (0 <= memoryCellIndex && memoryCellIndex < memory.length) {
            this.memory[memoryCellIndex] = "0";
            return "0";
        }
        else{
            return "ERROR_1";
        }
    }
}
