import java.util.Date;
import javax.swing.*;

/**
 * Main Klasse
 * @author Marco Wolff 7215191, Jonas Teiner 7216279
 */
public class Main {
    /**
     * Main Methode
     * @param args
     */
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