package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OpeningFrame extends JFrame {
    private JTextField nameField;
    private JButton submitButton;
    private JButton exitButton;

    public OpeningFrame() {
        setTitle("Enter Your Name");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        nameField = new JTextField();
        submitButton = new JButton("Submit");
        exitButton = new JButton("Exit");

        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                submitButton.setBackground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                submitButton.setBackground(Color.LIGHT_GRAY);
            }
        });

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setBackground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(Color.LIGHT_GRAY);
            }
        });

        setLayout(new BorderLayout());
        add(new JLabel("Name:"), BorderLayout.WEST);
        add(nameField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(submitButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = nameField.getText();
                dispose();
                SwingUtilities.invokeLater(() -> new FinanceTracker(userName));
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OpeningFrame::new);
    }
}
