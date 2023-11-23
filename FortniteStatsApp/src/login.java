
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class login {
    private JTextField tbUsername;
    public JPanel loginPanel;
    private JButton loginButton;
private Datenbank datenbank;
    public login(Datenbank datenbank) {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int userID = datenbank.checkUserID(tbUsername.getText());
                System.out.println(datenbank.getOwnUserID());
                if (userID>0){
                    System.out.println("DONE");

                    JFrame frame = new JFrame("Menu" + userID);
                    menu menuForm = new menu(datenbank);
                    frame.setContentPane(menuForm.menuPanel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setLocationRelativeTo(null);

                    frame.setVisible(true);
                }else{
                    System.out.println("NOT DONE");
                }
            }
        });
    }
}
