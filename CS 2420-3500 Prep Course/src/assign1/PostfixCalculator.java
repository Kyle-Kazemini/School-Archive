package assign1;

import java.util.*;

/**
 * Evaluates post fix expressions using a Stack.
 * 
 * @author Kyle Kazemini
 * @version 8 June 2020
 */
public class PostfixCalculator {

    public static Stack<Integer> stack = new Stack<>();

    /**
     * Takes in postfix expression as a String and returns the evaluation of the
     * expression.
     * 
     * @param String s
     * @return int
     */
    public static int evaluate(String s) {
        Scanner scn = new Scanner(s);

        while (scn.hasNext()) {
            if (scn.hasNextInt())
                stack.push(scn.nextInt());
            else {
                int right = stack.pop();
                int left = stack.pop();
                String operator = scn.next();

                switch (operator) {
                    case "+":
                        stack.push(left + right);
                        break;
                    case "-":
                        stack.push(left - right);
                        break;
                    case "*":
                        stack.push(left * right);
                        break;
                    case "/":
                        stack.push(left / right);
                        break;
                    case "^":
                        stack.push((int) Math.pow(left, right));
                    case "%":
                        stack.push(left % right);
                        break;
                    default:
                        break;
                }
            }
        }

        if (stack.isEmpty()) {
            scn.close();
            throw new RuntimeException();
        }

        scn.close();
        return stack.pop();
    }

    public static void main(String[] args) {
        System.out.println(evaluate("2 + 3 - 6"));
    }
}
