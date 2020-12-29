package assign2;

import java.util.Stack;
import assign1.PostfixCalculator;

/**
 * Evaluates infix expressions by converting them to postfix expressions first.
 * 
 * @author Kyle Kazemini
 * @version 27 June 2020
 */
public class InfixCalculator {

    static Stack<Character> stack = new Stack<>();
    static String postfix = "";

    /**
     * This method returns the evaluation of a postfix expression.
     * 
     * @param String s
     * @return int value
     */
    public static int evaluate(String s) {

        for (int i = 0; i < s.length(); i++) {
            char character = s.charAt(i);

            if (Character.isLetterOrDigit(character))
                postfix += " " + character;

            else if (character == '(')
                stack.push(character);

            else if (character == ')') {
                while (!stack.isEmpty() && stack.peek() != '(')
                    postfix += " " + stack.pop();

                stack.pop();
            }

            else {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix += stack.pop();
                }
                stack.push(character);
            }

        }

        while (!stack.isEmpty() && stack.peek() != '(')
            postfix += " " + stack.pop();

        System.out.println(postfix);

        return PostfixCalculator.evaluate(postfix);
    }

}