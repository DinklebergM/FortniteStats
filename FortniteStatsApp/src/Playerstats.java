import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Playerstats {

    private JLabel lblUsername;
    private JLabel lblMatches;
    private JLabel lblKills;
    private JLabel lblWins;
    private JLabel lblKpD;
    private JButton btnAddPlayer;
    public JPanel panelPlayerstats;

    public Playerstats(Datenbank datenbank, int id) {
        btnAddPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datenbank.friendAction(datenbank.getOwnUserID(),id,1);
            }
        });
    }
}
