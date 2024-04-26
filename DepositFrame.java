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

public class DepositFrame extends Frame {
    
    private TextField amountField;
    private BufferedImage backgroundImage;
    private boolean imageLoaded = false;
    private Connection connection;

    public DepositFrame() {
        setTitle("Deposit");
        setSize(1287, 685); // Set frame size
        setLayout(new BorderLayout());
        
        // Other initialization code...
        
        // Initialize database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("Failed to connect to the database.");
        }
    }
    
    private void deposit() {
        String amountStr = amountField.getText();
        
        try {
            double amount = Double.parseDouble(amountStr);
            
            // Prepare an SQL query to update the account balance
            String updateQuery = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setDouble(1, amount);  // Set the deposit amount
            statement.setInt(2, 123);        // Replace '123' with the account ID
            
            // Execute the update query
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                showMessage("Deposit successful: $" + amount);
            } else {
                showMessage("Deposit failed. Please try again.");
            }
            
            // Close the statement
            statement.close();
            
            // Close the frame after successful deposit
            dispose();
        } catch (NumberFormatException | SQLException ex) {
            showMessage("Invalid amount or database error. Please try again.");
            ex.printStackTrace();
        }
    }
    
    private void showMessage(String message) {
        // Display a message dialog with the specified message
        JOptionPane.showMessageDialog(this, message, "Deposit Status", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void dispose() {
        super.dispose();
        
        // Close the database connection
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Other methods...

    public static void main(String[] args) {
        // Load JDBC driver (e.g., for MySQL)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        new DepositFrame();
    }
}
