package Apology;
import java.awt.*;
import java.awt.event.*;
 
public class apology extends Frame {
    //private String s;
    public apology(String str){
        setSize(300,100);
        setLayout(new GridLayout(3, 1)); 
        TextField t=new TextField();
        
        t.setText(str);
        add(t);
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose(); // Close the window
            }
        });
    }
    public static void main(String[]args){
        new apology("hello");
    }
}