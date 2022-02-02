package com.tsystems.javaschool.tasks.calculator;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */

    public String evaluate(String statement) {

        if (!isStatementValid(statement)) {
            return null;
        }

        String result;

        try {
            result = "" + calculate(statement);
        } catch (RuntimeException exc) {
            return null;
        }
        if (result.equals("Infinity")) {
            return null;
        }
        if (result.endsWith(".0")) {
            return result.replace(".0", "");
        }
        return result;

    }

    private boolean isStatementValid(String statement) {
        if (statement == null || statement.equals("")) {
            return false;
        }

        String validChars = "+-*/().1234567890";

        for (char c : statement.toCharArray()) {
            if (validChars.indexOf(c) == -1) {
                return false;
            }
        }

        String validOperators = "+-*/.";

        char[] chars = statement.toCharArray();
        for (int i = 0; i < chars.length - 2; i++) {
            if (validOperators.indexOf(chars[i]) != -1 && validOperators.indexOf(chars[i + 1]) != -1) {
                return false;
            }
        }
        return true;
    }

    public double calculate(String str) {
        Service service = new Service(str);
        return service.parse();
    }

    class Service {
        int currentPosition = -1, currentCharacter;

        String statement;

        public Service(String str) {
            this.statement = str;
        }

        void nextChar() {
            currentCharacter = (++currentPosition < statement.length()) ? statement.charAt(currentPosition) : -1;
        }

        boolean isCharEquals(int charToCompare) {
            if (currentCharacter == charToCompare) {
                nextChar();
                return true;
            }
            return false;
        }

        double parse() {
            nextChar();
            return parseExpression();
        }

        double parseExpression() {
            double x = parseTerm();
            for (; ; ) {
                if (isCharEquals('+')) x += parseTerm();
                else if (isCharEquals('-')) x -= parseTerm();
                else return x;
            }
        }

        double parseTerm() {
            double x = parseFactor();
            for (; ; ) {
                if (isCharEquals('*')) x *= parseFactor();
                else if (isCharEquals('/')) x /= parseFactor();
                else return x;
            }
        }

        double parseFactor() {
            double x = 0;
            int startPosition = this.currentPosition;
            if (isCharEquals('(')) { // parentheses
                x = parseExpression();
                if (!isCharEquals(')')) throw new RuntimeException("Missing ')'");
            } else if ((currentCharacter >= '0' && currentCharacter <= '9') || currentCharacter == '.') { // numbers
                while ((currentCharacter >= '0' && currentCharacter <= '9') || currentCharacter == '.') nextChar();
                x = Double.parseDouble(statement.substring(startPosition, this.currentPosition));
            }
            return x;
        }
    }
}
