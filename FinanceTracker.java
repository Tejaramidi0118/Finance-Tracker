package Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

class Transaction implements Serializable {
    private final String name;
    private final String date;
    private final double amount;
    private final String category;
    private final String description;

    public Transaction(String name, String date, double amount, String category, String description) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public ArrayList<String> toArrayList() {
        ArrayList<String> row = new ArrayList<>();
        row.add(name);
        row.add(date);
        row.add(String.valueOf(amount));
        row.add(category);
        row.add(description);
        return row;
    }
}

public class FinanceTracker extends JFrame {
    private final String userName;
    private final TransactionTableModel tableModel;
    private final JTable transactionTable;
    private final JTextField dateField, amountField, categoryField;
    private final JTextArea descriptionArea;
    private final JButton addButton, exitButton, showTotalButton, removeButton;

    public FinanceTracker(String userName) {
        this.userName = userName;
        setTitle("Personal Finance Tracker - " + userName);
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new TransactionTableModel();
        transactionTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(transactionTable);

        dateField = new JTextField();
        amountField = new JTextField();
        categoryField = new JTextField();
        descriptionArea = new JTextArea();
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        showTotalButton = new JButton("Show Total");
        exitButton = new JButton("Exit");

        JPanel formPanel = new JPanel();
        setupFormPanel(formPanel);

        setupAddButton();

        setupRemoveButton();

        setupExitButton();

        setupShowTotalButton();

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);

        loadTransactions();

        setVisible(true);
    }

    private void setupFormPanel(JPanel formPanel) {
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel dateLabel = new JLabel("Date:");
        gbc.gridx = 0;
        formPanel.add(dateLabel, gbc);
 
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        dateField.setPreferredSize(new Dimension(200, 25));
        formPanel.add(dateField, gbc);

        JLabel amountLabel = new JLabel("Amount:");
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(amountLabel, gbc);

        gbc.gridx = 1;
        amountField.setPreferredSize(new Dimension(200, 25));
        formPanel.add(amountField, gbc);

        JLabel categoryLabel = new JLabel("Category:");
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(categoryLabel, gbc);

        gbc.gridx = 1;
        categoryField.setPreferredSize(new Dimension(200, 25));
        formPanel.add(categoryField, gbc);

        JLabel descriptionLabel = new JLabel("Description:");
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(descriptionLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        descriptionArea.setPreferredSize(new Dimension(200, 40));
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        formPanel.add(descriptionScrollPane, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        addButton.setPreferredSize(new Dimension(120, 30));
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addButton.setBackground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addButton.setBackground(Color.WHITE);
            }
        });
        buttonPanel.add(addButton);

        removeButton.setPreferredSize(new Dimension(120, 30));
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                removeButton.setBackground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                removeButton.setBackground(Color.WHITE);
            }
        });
        buttonPanel.add(removeButton);

        showTotalButton.setPreferredSize(new Dimension(120, 30));
        showTotalButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showTotalButton.setBackground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                showTotalButton.setBackground(Color.WHITE);
            }
        });
        buttonPanel.add(showTotalButton);

        exitButton.setPreferredSize(new Dimension(120, 30));
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setBackground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(Color.WHITE);
            }
        });
        buttonPanel.add(exitButton);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(buttonPanel, gbc);
    }

    private void setupAddButton() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = userName;
                    String date = dateField.getText();
                    double amount = Double.parseDouble(amountField.getText());
                    String category = categoryField.getText();
                    String description = descriptionArea.getText();

                    Transaction transaction = new Transaction(name, date, amount, category, description);
                    tableModel.addTransaction(transaction);

                    clearFormFields();

                    saveTransactions();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(FinanceTracker.this, "Please enter a valid amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void setupRemoveButton() {
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = transactionTable.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                    saveTransactions();
                } else {
                    JOptionPane.showMessageDialog(FinanceTracker.this, "Please select a row to remove.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void setupExitButton() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new OpeningFrame());
            }
        });
    }

    private void setupShowTotalButton() {
        showTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double total = calculateTotal();
                JOptionPane.showMessageDialog(FinanceTracker.this, "Total Amount: ₹" + total, "Total", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }


    private double calculateTotal() {
        double total = 0.0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String amountStr = (String) tableModel.getValueAt(i, 2);
            if (amountStr != null && !amountStr.isEmpty()) {
                double amount = Double.parseDouble(amountStr);//static method call to the parseDouble()  method of Double class
                total += amount;
            }
        }
        return total;
    }

    private void clearFormFields() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateField.setText(dateFormat.format(new Date()));
        amountField.setText("");
        categoryField.setText("");
        descriptionArea.setText("");
    }

class TransactionTableModel extends DefaultTableModel {
    private static final String[] COLUMN_NAMES = {"Name", "Date", "Amount", "Category",
    		"Description"};

    public TransactionTableModel() {
        super(COLUMN_NAMES, 0);
    }

    public void addTransaction(Transaction transaction) {
        addRow(transaction.toArrayList().toArray(new String[0]));
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }
}


    private void loadTransactions() {
        try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(userName + "_transactions.ser"))) {
            List<Transaction> transactions = (List<Transaction>) read.readObject();
            for (int i = 0; i < transactions.size(); i++) {
                Transaction transaction = transactions.get(i);
                tableModel.addTransaction(transaction);
            }
        } catch (IOException | ClassNotFoundException e) {
        	//
        }
    }

    private void saveTransactions() {
        try (ObjectOutputStream load = new ObjectOutputStream(new FileOutputStream(userName + "_transactions.ser"))) {
            List<Transaction> transactions = new ArrayList<>();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String name = (String) tableModel.getValueAt(i, 0);
                String date = (String) tableModel.getValueAt(i, 1);
                double amount = Double.parseDouble((String)tableModel.getValueAt(i, 2));
                String category = (String) tableModel.getValueAt(i, 3);
                String description = (String) tableModel.getValueAt(i, 4);
                transactions.add(new Transaction(name, date, amount, category, description));
            }
            load.writeObject(transactions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FinanceTracker("default"));
    }
}
