import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
/**
 * Klasse für die Freundesliste und Freundesaktionen
 * @author Marco Wolff 7215191, Jonas Teiner 7216279
 */
public class FriendAction {
    public JPanel panelFriendAction;
    private JComboBox comboPlayerlist;
    private JButton btnAddFriend;
    private JComboBox comboFriendrequests;
    private JButton btnacceptFriendrequest;
    private JComboBox comboFriends;
    private JButton btnDeleteFriend;
    private JButton btnDeleteFriendrequest;

    /**
     * Konstruktor für die Klasse FriendAction
     * @param datenbank
     */
    public FriendAction(Datenbank datenbank) {
      // Part 1 Alle Benutzer zum adden

        comboPlayerlist.setEditable(true);
        List<Player> users = datenbank.getAllPlayer();
        for (Player user : users) {
            String username = user.getUsername();
            comboPlayerlist.addItem(username);
        }
        btnAddFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            datenbank.friendAction(datenbank.getOwnUserID(),users.get(comboPlayerlist.getSelectedIndex()).getID(),1);
            }
        });
        // Part 2 Alle Freundschaftsanfragen


        comboFriendrequests.setEditable(true);
        List<Player> allFriendrequest = datenbank.getFriendrequest(datenbank.getOwnUserID());
        for (Player player : allFriendrequest) {
            String username = player.getUsername();
            comboFriendrequests.addItem(username);
        }
        btnacceptFriendrequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datenbank.friendAction(datenbank.getOwnUserID(), allFriendrequest.get(comboFriendrequests.getSelectedIndex()).getID(),1);
            }
        });
        btnDeleteFriendrequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datenbank.friendAction(datenbank.getOwnUserID(), allFriendrequest.get(comboFriendrequests.getSelectedIndex()).getID(),0);
            }
        });

        // Part 3 Alle Freunde
        comboFriends.setEditable(true);
        List<Player> allFriends = datenbank.getFriends(datenbank.getOwnUserID());
        for (Player player : allFriends) {
            String username = player.getUsername();
            comboFriends.addItem(username);
        }
        btnDeleteFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datenbank.friendAction(datenbank.getOwnUserID(), allFriends.get(comboFriends.getSelectedIndex()).getID(),0);
            }
        });
    }
}
