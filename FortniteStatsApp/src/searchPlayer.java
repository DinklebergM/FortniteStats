import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * Klasse zum Suchen eines Spielers
 * @author Marco Wolff 7215191, Jonas Teiner 7216279
 */
public class searchPlayer {
    public JPanel searchPlayerPanel;
    private JButton btnSearch;
    private JComboBox<String> comboPlayer;
    private List<String> allUsernames;

    /**
     * Konstruktor für die Klasse searchPlayer
     * @param datenbank
     */
    public searchPlayer(Datenbank datenbank) {

        allUsernames = new ArrayList<>();

        List<Player> users = datenbank.getAllPlayer();
        for (Player user : users) {
            String username = user.getUsername();
            comboPlayer.addItem(username);
            allUsernames.add(username);
        }

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Player Stats");
                Playerstats playerstats = new Playerstats(datenbank,users.get(comboPlayer.getSelectedIndex()).getID());
                frame.setContentPane(playerstats.panelPlayerstats);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
