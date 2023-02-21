package calculator;

import java.awt.*;

public enum Buttons {
    PARENTHESES("Parentheses", "()", Color.GRAY.brighter()),
    EMPTY2(null, null, null),
    C("Clear", "C", Color.GRAY.brighter()),
    DEL("Delete", "Del", Color.GRAY.brighter()),

    POWER2("PowerTwo", "x²", Color.GRAY.brighter()),
    POWERY("PowerY", "xⁿ", Color.GRAY.brighter()),
    SQUAREROOT("SquareRoot", "√", Color.GRAY.brighter()),
    DIVIDE("Divide", "÷", Color.GRAY.brighter()),


    SEVEN("Seven", "7", Color.LIGHT_GRAY.brighter()),
    EIGHT("Eight", "8", Color.LIGHT_GRAY.brighter()),
    NINE("Nine", "9", Color.LIGHT_GRAY.brighter()),
    MULTIPLY("Multiply", "×", Color.GRAY.brighter()),

    FOUR("Four", "4", Color.LIGHT_GRAY.brighter()),
    FIVE("Five", "5", Color.LIGHT_GRAY.brighter()),
    SIX("Six", "6", Color.LIGHT_GRAY.brighter()),
    SUBTRACT("Subtract", "-", Color.GRAY.brighter()),

    ONE("One", "1", Color.LIGHT_GRAY.brighter()),
    TWO("Two", "2", Color.LIGHT_GRAY.brighter()),
    THREE("Three", "3", Color.LIGHT_GRAY.brighter()),
    ADD("Add", "+", Color.GRAY.brighter()),

    PLUSMINUS("PlusMinus", "±", Color.LIGHT_GRAY.brighter()),
    ZERO("Zero", "0", Color.LIGHT_GRAY.brighter()),
    DOT("Dot", ".", Color.LIGHT_GRAY.brighter()),
    EQUALS("Equals", "=", Color.GRAY.brighter());


    final String name;
    final String symbol;
    final Color color;

    Buttons(String name, String symbol, Color color) {
        this.name = name;
        this.symbol = symbol;
        this.color = color;
    }
}
