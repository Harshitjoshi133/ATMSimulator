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

import Apology.apology;

public class ChangePinFrame extends Frame {

    private TextField currentPINField;
    private TextField newPINField;
    private TextField confirmPINField;
    private BufferedImage backgroundImage;
    private Connection connection;

    public ChangePinFrame() {
        // Initialization code...

        // Initialize database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
            new apology("Failed to connect to the database.");
        }
    }

    private void changePIN() {
        String currentPIN = currentPINField.getText();
        String newPIN = newPINField.getText();
        String confirmPIN = confirmPINField.getText();

        // Validate PIN format and match
        if (!newPIN.equals(confirmPIN)) {
            new apology("New PIN and Confirm PIN do not match. Please try again.");
            return;
        }

        // Update PIN in the database
        try {
            String updateQuery = "UPDATE accounts SET pin = ? WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, newPIN); // Set the new PIN
            statement.setInt(2, 123);        // Replace '123' with the account ID

            // Execute the update query
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                new apology("PIN change successful.");
            } else {
                new apology("PIN change failed. Please try again.");
            }

            // Close the statement
            statement.close();

            // Close the frame after successful PIN change
            dispose();
        } catch (SQLException ex) {
            new apology("Error updating PIN. Please try again.");
            ex.printStackTrace();
        }
    }

    // Other methods...

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

        new ChangePinFrame();
    }
}
