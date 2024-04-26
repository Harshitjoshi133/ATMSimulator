import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ATMMainFrame extends Frame {
    private BufferedImage backgroundImage;
    public ATMMainFrame() {
        setTitle("ATM Main Menu");
        setSize(1295, 687); // Set the frame size
        setLayout(new FlowLayout(FlowLayout.CENTER, 200, 100)); // Centered FlowLayout with vertical gap
        
        // Create buttons with adjusted size
        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setPreferredSize(new Dimension(300, 80)); // Set preferred size for Withdraw button
        btnWithdraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new WithdrawFrame();
                System.out.println("Withdraw");
            }
        });
        
        Button btnDeposit = new Button("Deposit");
        btnDeposit.setPreferredSize(new Dimension(300, 80)); // Set preferred size for Deposit button
        btnDeposit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DepositFrame();
                System.out.println("Deposit");
            }
        });
        
        Button btnBalanceInquiry = new Button("Balance Inquiry");
        btnBalanceInquiry.setPreferredSize(new Dimension(300, 80)); // Set preferred size for Balance Inquiry button
        btnBalanceInquiry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BalanceEnquiry();
                System.out.println("Balance Inquiry");
            }
        });
        
        Button btnChangePIN = new Button("Change PIN");
        btnChangePIN.setPreferredSize(new Dimension(300, 80)); // Set preferred size for Change PIN button
        btnChangePIN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ChangePinFrame();
                System.out.println("Change PIN");
            }
        });
         try {
            // Load the background image from a URL
            @SuppressWarnings("deprecation")
            URL imageUrl = new URL("https://media.istockphoto.com/id/697417092/photo/hand-inserting-atm-credit-card.jpg?s=1024x1024&w=is&k=20&c=mfJOebmE48jfXJr_HZ2FKTErDqDhWTqaUesl-ODPjgg=");
            //@SuppressWarnings("deprecation")
            //URL imageurl = new URL("");
            backgroundImage = ImageIO.read(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
  
        Button btnLogout = new Button("Logout");
        btnLogout.setPreferredSize(new Dimension(300, 80)); // Set preferred size for Logout button
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform logout actions (e.g., close current frame)
                new ATMLogin();
                dispose(); // Close the current frame
                 // Terminate the application (in a real scenario, you would handle logout differently)
            }
        });
        
        // Add buttons to the frame
        add(btnWithdraw);
        add(btnDeposit);
        add(btnBalanceInquiry);
        add(btnChangePIN);
        add(btnLogout);
        
        // Display the frame
        setVisible(true);
        
        // Handle frame closing
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose(); // Close the frame when the close button is clicked
                System.exit(0); // Terminate the application
            }
        });
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
        new ATMMainFrame();
    }
}
