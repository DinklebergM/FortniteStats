import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FriendAction {
    public JPanel panelFriendAction;
    private JComboBox comboPlayerlist;
    private JButton btnAddFriend;
    private JComboBox comboFriendrequests;
    private JButton btnacceptFriendrequest;
    private JComboBox comboBox1;
    private JButton btnDeleteFriend;

    public FriendAction(Datenbank datenbank) {
        List<String> allUsernames; // Eine Liste zum Speichern aller Benutzernamen
        comboPlayerlist.setEditable(true);


        List<User> users = datenbank.getAllPlayer();
        for (User user : users) {
            String username = user.getUsername();
            comboPlayerlist.addItem(username);
        }


        btnAddFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            datenbank.friendAction(datenbank.getOwnUserID(),users.get(comboPlayerlist.getSelectedIndex()).getID(),1);
            }
        });
    }
}
