package calculator;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculations {

    private static final EnumSet<Buttons> operators =
            EnumSet.of(Buttons.DIVIDE, Buttons.MULTIPLY, Buttons.ADD, Buttons.SUBTRACT, Buttons.EQUALS);
    private static final EnumSet<Buttons> numbers = EnumSet.of(Buttons.ZERO, Buttons.ONE, Buttons.TWO,
            Buttons.THREE, Buttons.FOUR, Buttons.FIVE, Buttons.SIX, Buttons.SEVEN, Buttons.EIGHT, Buttons.NINE);

    public static String editField(String input, Buttons button) {
        input = formatInput(input, button);
        return processInput(input, button);
    }

    public static String getResult(String input) {
        return calculate(input);
    }

    public static boolean isEquationCorrect(String equation) {
        return !equation.matches(".*÷0(\\.0)?([)+×÷-].*)?|.*[+×÷.-]|.*√\\(\\(-.*")
                && !hasUnclosedParentheses(equation);
    }

    private static String formatInput(String input, Buttons button) {
        if (input.matches("0|.*[×÷+-]0") && numbers.contains(button)) {
            input = input.substring(0, input.length() - 1);
        }
        if (input.matches("\\.[0-9]+") && operators.contains(button)) {
            input = "0" + input;
        }
        if (input.matches(".*[0-9]+\\.") && operators.contains(button)) {
            input = input + "0";
        }
        return input;
    }

    private static String processInput(String input, Buttons button) {
        if (button.equals(Buttons.EQUALS)) return input;
        if (button.equals(Buttons.C)) return "";
        if (button.equals(Buttons.DEL)) {
            if (input.matches(".*(\\^\\(|√\\(|\\(-)")) {
                return getSubstring(input, 2);
            }
            return (input.isEmpty() ? "" : getSubstring(input, 1));
        }
        if (button.equals(Buttons.DOT) && !input.matches(".*\\.[0-9]*|.*\\)")) {
            return input + button.symbol;
        }
        if (button.equals(Buttons.PARENTHESES)) {
            if (input.matches(".*[(×÷+-]|")) {
                return input + "(";
            } else if (hasUnclosedParentheses(input)) {
                return input + ")";
            }
        }
        if (button.equals(Buttons.SQUAREROOT) && input.matches(".*[(×÷+-]|")) {
            return input + "√(";
        }
        if ((button.equals(Buttons.POWER2) || button.equals(Buttons.POWERY)) && input.matches(".*[0-9)]")) {
            return input + (button.equals(Buttons.POWER2) ? "^(2)" : "^(");
        }
        if (button.equals(Buttons.PLUSMINUS)) {
            if (input.matches(".*\\(-")) {
                return getSubstring(input, 2);
            } else if (input.matches(".*[(×÷+-]|")) {
                return input + "(-";
            } else if (input.matches("[0-9]+")){
                return "(-" + input;
            } else if (input.matches("\\(-[0-9]+")) {
                return input.substring(2);
            }
        }
        if (numbers.contains(button)) {
            if (input.matches(".*[)(×÷+-]\\.")) {
                return getSubstring(input, 1) + "0." + button.symbol;
            } else if (!input.matches(".*\\)")) {
                return input + button.symbol;
            }
        }
        if (operators.contains(button)) {
            if (input.matches(".*[×÷+-]")) {
                return getSubstring(input, 1) + button.symbol;
            } else if (input.matches(".*[0-9)]")) {
                return input + button.symbol;
            }
        }
        return input;
    }

    private static String calculate(String input) {
        String equation = input;

        Pattern pattern = Pattern.compile("\\([^()]*\\)");
        Matcher matcher = pattern.matcher(equation);
        while (matcher.find()) {
            equation = equation.replace(matcher.group(), simplify(matcher.group()));
            matcher = pattern.matcher(equation);
        }
        return simplify(equation);
    }

    private static String simplify(String equation) {
        List<Double> numbers = new ArrayList<>();
        List<String> operators = new ArrayList<>();

        Pattern pattern = Pattern.compile("([(+×÷^√-]-)?[0-9]+(\\.[0-9]+)?|[+×÷^√-]");
        Matcher matcher = pattern.matcher(equation);
        while(matcher.find()) {
            String part = matcher.group();
            if (part.matches("[(+×÷^√-]-[0-9]+(\\.[0-9]+)?")) {
                numbers.add(Double.parseDouble(part.substring(1)));
                if (part.substring(0, 1).matches("[+×÷^√-]")) {
                    operators.add(part.substring(0, 1));
                }
            } else if (part.matches("[+×÷^√-]")) {
                operators.add(matcher.group());
            } else {
                numbers.add(Double.parseDouble(part));
            }
        }

        String[] patterns = {"√", "\\^", "[×÷]", "[+-]"};
        for (String p : patterns) {
            int i = 0;
            while (i < operators.size()) {
                if (operators.get(i).matches(p)) {
                    switch (operators.get(i)) {
                        case "√" -> {
                            int index = numbers.size() < operators.size() ? i - 1 : i;
                            numbers.set(index, Math.sqrt(numbers.get(index)));
                            operators.remove(i);
                            continue;
                        }
                        case "×" -> numbers.set(i, numbers.get(i) * numbers.get(i + 1));
                        case "÷" -> numbers.set(i, numbers.get(i) / numbers.get(i + 1));
                        case "^" -> numbers.set(i, Math.pow(numbers.get(i), numbers.get(i + 1)));
                        case "+" -> numbers.set(i, numbers.get(i) + numbers.get(i + 1));
                        case "-" -> {
                            if (numbers.size() == 1) {
                                numbers.set(i, - numbers.get(i));
                                operators.remove(i);
                                continue;
                            } else {
                                numbers.set(i, numbers.get(i) - numbers.get(i + 1));
                            }
                        }
                    }
                    numbers.remove(i + 1);
                    operators.remove(i);
                    continue;
                }
                i++;
            }
        }

        String result = numbers.get(0).toString();
        return result.endsWith(".0") ? getSubstring(result, 2) : result;
    }

    private static boolean hasUnclosedParentheses(String input) {
        int left = 0;
        int right = 0;
        for (char c : input.toCharArray()) {
            if (c == '(') left++;
            if (c == ')') right++;
        }
        return left > right;
    }

    private static String getSubstring(String input, int charsToCut) {
        return input.substring(0, input.length() - charsToCut);
    }
}
