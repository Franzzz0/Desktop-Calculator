package calculator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Calculator extends JFrame {

    private final JLabel equationLabel;
    private final JLabel resultLabel;

    public Calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(315, 500);
        setLayout(new BorderLayout());
        equationLabel = new JLabel("");
        resultLabel = new JLabel("0");
        try {
            SwingUtilities.invokeAndWait(this::initComponents);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.LIGHT_GRAY);

        equationLabel.setName("EquationLabel");
        equationLabel.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
        equationLabel.setForeground(Color.DARK_GRAY);
        equationLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        resultLabel.setName("ResultLabel");
        resultLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        resultLabel.setBorder(new EmptyBorder(0, 0, 18, 0));
        resultLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));

        topPanel.add(resultLabel);
        topPanel.add(equationLabel);

        topPanel.setBorder(new EmptyBorder(20, 5, 20, 5));
        this.add(topPanel, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(6, 4, 5, 5));
        buttons.setBorder(new EmptyBorder(10, 5, 5, 5));
        buttons.setBackground(Color.LIGHT_GRAY);

        for (Buttons button : Buttons.values()) {
            if (button.name == null) {
                buttons.add(new JLabel());
            } else {
                JButton b = new JButton(button.symbol);
                b.setName(button.name);
                b.setBackground(button.color);
                b.addActionListener(e -> buttonAction(button));
                buttons.add(b);
            }
        }
        add(buttons);
    }

    private void buttonAction(Buttons button) {
        equationLabel.setForeground(Color.DARK_GRAY);
        equationLabel.setText(Calculations.editField(equationLabel.getText(), button));
        if (button.equals(Buttons.EQUALS)) {
            if (!Calculations.isEquationCorrect(equationLabel.getText())) {
                equationLabel.setForeground(Color.RED.darker());
            } else {
                resultLabel.setText(Calculations.getResult(equationLabel.getText()));
            }
        }
    }
}
