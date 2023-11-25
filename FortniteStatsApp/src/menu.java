import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menu {
    public JPanel menuPanel;
    private JButton btnSearchPlayer;
    private JButton btnShowProfile;
    private JButton btnLastMatches;
    private JButton btnFriendMenu;

    public menu(Datenbank datenbank){

        btnSearchPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Search Player");
                searchPlayer searchplayer = new searchPlayer(datenbank);
                frame.setContentPane(searchplayer.searchPlayerPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        btnLastMatches.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Last Matches");
                LastMatches lastMatches = new LastMatches(datenbank,datenbank.getOwnUserID());
                frame.setContentPane(lastMatches.panelLastMatches);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        btnFriendMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Friend Menu");
                FriendAction friendAction = new FriendAction(datenbank);
                frame.setContentPane(friendAction.panelFriendAction);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
