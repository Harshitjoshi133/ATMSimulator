import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class BalanceEnquiry extends Frame {

    private Label balanceLabel;
    private BufferedImage backgroundImage;
    private Connection connection;

    public BalanceEnquiry() {
        setTitle("Balance Enquiry");
        setSize(1287, 685); // Set frame size
        setLayout(new BorderLayout());

        // Initialize database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("Failed to connect to the database.");
        }

        // Panel for holding components in the center
        Panel centerPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 600, 100));

        balanceLabel = new Label("Current Balance: $0.00");
        Button refreshButton = new Button("Refresh");
        Button closeButton = new Button("Close");

        // Add components to the center panel
        centerPanel.add(balanceLabel);
        centerPanel.add(refreshButton);
        centerPanel.add(closeButton);

        // Add the center panel to the frame
        add(centerPanel, BorderLayout.CENTER);

        // Add action listeners
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshBalance();
            }
        });

        closeButton.addActionListener(new ActionListener() {
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

        // Initialize balance
        refreshBalance();
    }

    private void refreshBalance() {
        try {
            // Prepare an SQL query to fetch the current balance from the database
            String query = "SELECT balance FROM accounts WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, 123); // Replace '123' with the account ID

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double currentBalance = resultSet.getDouble("balance");
                balanceLabel.setText("Current Balance: $" + String.format("%.2f", currentBalance));
            } else {
                showMessage("Account not found.");
            }

            // Close the result set and statement
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("Error fetching balance. Please try again.");
        }
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

    private void showMessage(String message) {
        // Display a message dialog with the specified message
        JOptionPane.showMessageDialog(this, message, "Balance Enquiry", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        // Load JDBC driver (e.g., for MySQL)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        new BalanceEnquiry();
    }
}
