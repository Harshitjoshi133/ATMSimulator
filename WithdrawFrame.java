import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class WithdrawFrame extends Frame {

    private TextField amountField;
    private BufferedImage backgroundImage;
    private Connection connection;

    public WithdrawFrame() {
        setTitle("Withdraw");
        setSize(1285, 687); // Set frame size
        setLayout(new FlowLayout(FlowLayout.CENTER, 600, 10));

        Label amountLabel = new Label("Enter Amount:");
        amountField = new TextField(20); // Text field for amount input

        Button withdrawButton = new Button("Withdraw");
        Button cancelButton = new Button("Cancel");

        try {
            // Load the background image from a URL
            URL imageUrl = new URL("https://media.istockphoto.com/id/697417092/photo/hand-inserting-atm-credit-card.jpg?s=1024x1024&w=is&k=20&c=mfJOebmE48jfXJr_HZ2FKTErDqDhWTqaUesl-ODPjgg=");
            backgroundImage = ImageIO.read(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add components to the frame
        add(amountLabel);
        add(amountField);
        add(withdrawButton);
        add(cancelButton);

        // Add action listeners
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the frame
            }
        });

        // Handle frame closing
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose(); // Close the frame
            }
        });

        // Display the frame
        setVisible(true);

        // Initialize database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "username", "password");
        } catch (SQLException ex) {
            ex.printStackTrace();
            showMessage("Failed to connect to the database.");
        }
    }

    private void withdraw() {
        String amountStr = amountField.getText();

        try {
            double amount = Double.parseDouble(amountStr);

            // Update account balance in the database (example: deduct the withdrawal amount)
            String updateQuery = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setDouble(1, amount);
            statement.setInt(2, 12345); // Replace '12345' with the actual account ID
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                // Withdrawal successful
                showMessage("Withdrawal successful: $" + amount);
            } else {
                showMessage("Withdrawal failed. Insufficient funds.");
            }

            // Close the frame after handling withdrawal
            dispose();
        } catch (NumberFormatException | SQLException ex) {
            showMessage("Invalid amount or database error. Please try again.");
            ex.printStackTrace();
        }
    }

    private void showMessage(String message) {
        // Display a message dialog with the specified message
        JOptionPane.showMessageDialog(this, message, "Withdrawal Status", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw the background image
        if (backgroundImage != null) {
            // Scale the image to fill the frame
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        // Load JDBC driver (e.g., for MySQL)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        new WithdrawFrame();
    }
}
