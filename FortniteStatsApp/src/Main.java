import java.util.Date;
import javax.swing.*;

public class Main {

    public static void main (String[] args)
    {
        Datenbank datenbank = new Datenbank();
        JFrame frame = new JFrame("Login");
        login loginForm = new login(datenbank);
        frame.setContentPane(loginForm.loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}