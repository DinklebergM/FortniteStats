import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Klasse für das eigene Profil und die Profilaktualisierung
 * @author Marco Wolff 7215191, Jonas Teiner 7216279
 */
public class OwnProfile {
    public JPanel panelOwnProfile;
    private JButton btnUpdateProfile;
    private JTextField tbUsername;
    private JTextField tbEmail;

    /**
     * Konstruktor für die Klasse OwnProfile
     * @param datenbank
     */
    public OwnProfile(Datenbank datenbank) {

        Player player = datenbank.getPlayerProfile(datenbank.getOwnUserID());
        tbUsername.setText(player.getUsername());
        tbEmail.setText(player.getEmail());
        btnUpdateProfile.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                datenbank.updatePlayerProfile(datenbank.getOwnUserID(),tbUsername.getText(),tbEmail.getText());
            }
        });
    }
}
