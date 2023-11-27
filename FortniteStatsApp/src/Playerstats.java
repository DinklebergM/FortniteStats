import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Klasse für die Playerstats und als Freund adden
 * @author Marco Wolff 7215191, Jonas Teiner 7216279
 */
public class Playerstats {

    private JLabel lblUsername;
    private JLabel lblMatches;
    private JLabel lblKills;
    private JLabel lblWins;
    private JLabel lblKpD;
    private JButton btnAddPlayer;
    public JPanel panelPlayerstats;
    private JLabel lblTopWeapon;
    private JLabel lblTopWeaponKills;

    /**
     * Konstruktor für die Klasse Playerstats
     * @param datenbank
     * @param id
     */
    public Playerstats(Datenbank datenbank, int id) {

        Player player = datenbank.getPlayerProfile(id);
        lblUsername.setText("Username: "+ player.getUsername());
        lblMatches.setText("Matches: "+ player.getMatchCount());
        lblKills.setText("Kills: "+ player.getKills());
        lblWins.setText("Wins: "+ player.getWins());
        lblKpD.setText("KpD: "+ (double)player.getKills()/player.getMatchCount());
        lblTopWeapon.setText("Top Weapon: "+ player.getTopWeapon());
        lblTopWeaponKills.setText("Top Weapon Kills: "+ player.getTopWeaponKills());


        btnAddPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datenbank.friendAction(datenbank.getOwnUserID(),id,1);
            }
        });
    }
}
