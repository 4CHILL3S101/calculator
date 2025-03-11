package com.example.my_calculator;

import java.util.Stack;

public class CalculateProcesses {

    public static double evaluateExpression(String expression) {

        // i check niya if naay + sa first before ang number tas if naa i convert into into a positive value
        if (expression.startsWith("+")) {
            expression = expression.substring(1);
        }
        // Check if the expression starts with an invalid operator
        if (expression.startsWith("/") || expression.startsWith("*") || expression.endsWith("+") ||
                expression.endsWith("-") || expression.endsWith("*") || expression.endsWith("/") ||
                expression.endsWith("%")) {
            return Double.NaN;
        }


        // i check niya if naay double to operator like **
        if (expression.matches(".*[+\\-*/]{2,}.*") && !expression.matches(".*\\d[-]\\d.*") && !expression.matches(".*[+\\-*/]\\-\\d.*")) {
            return Double.NaN; // Invalid expression (except for negative numbers)
        }



        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();
        StringBuilder numBuilder = new StringBuilder();

        boolean hasNumber = false;
        boolean isNegative = false;



        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            // i check niya if naay - symbol before ang number tas i consider niya as negative number
            if (ch == '-' && (i == 0 || (!Character.isDigit(expression.charAt(i - 1)) && expression.charAt(i - 1) != ')'))) {
                numBuilder.append(ch);
                isNegative = true;
            }

            // i check niya if naay decimal number tas append niya sa numBuilder para mahimong  whole number
            else if (Character.isDigit(ch) || ch == '.') {
                numBuilder.append(ch);
                hasNumber = true;
            }

            // kwaon niya ang value sa stacks and i convert niya into Double tas i push sa numbers
            else {
                if (hasNumber || isNegative) {
                    numbers.push(Double.parseDouble(numBuilder.toString()));
                    numBuilder.setLength(0);
                    hasNumber = false;
                    isNegative = false;
                }

                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(ch);
            }
        }

        if (hasNumber || isNegative) {
            numbers.push(Double.parseDouble(numBuilder.toString()));
        }

        while (!operators.isEmpty()) {
            numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }


    // i check niya if unsa na operator ang una para ma calculate as pemdas
    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') return 1; // i return niya as low priority
        if (operator == '*' || operator == '/') return 2; // i return niya as high priority para ma accomodate ug una
        return 0;
    }


    // gina check niya ang operator tas gina process niya based saiyang operator

    private static double applyOperation(char operator, double b, double a) {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return (b != 0) ? a / b : Double.NaN;
            case '%': return a * (b / 100.0);
            default: return 0;
        }
    }

// gina convert niya ang inputted value into percentage
    public static double getPercentage(double value) {
        return value / 100.0;
    }
}
