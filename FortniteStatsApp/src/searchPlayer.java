import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class searchPlayer {
    public JPanel searchPlayerPanel;
    private JButton btnSearch;
    private JComboBox<String> comboPlayer;
    private List<String> allUsernames; // Eine Liste zum Speichern aller Benutzernamen

    public searchPlayer(Datenbank datenbank) {
        comboPlayer.setEditable(true);
        allUsernames = new ArrayList<>(); // Initialisiere die Liste

        List<User> users = datenbank.getAllPlayer();
        for (User user : users) {
            String username = user.getUsername();
            comboPlayer.addItem(username);
            allUsernames.add(username);
        }

        // Restliche Event-Listener
        // ...

        comboPlayer.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                comboFilter(textField.getText());
            }
        });
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

    private void comboFilter(String enteredText) {
        List<String> filterArray = new ArrayList<>();
        for (String username : allUsernames) {
            if (username.toLowerCase().contains(enteredText.toLowerCase())) {
                filterArray.add(username);
            }
        }
        if (filterArray.size() > 0) {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(filterArray.toArray(new String[0]));
            comboPlayer.setModel(model);
            comboPlayer.setSelectedItem(enteredText);
            comboPlayer.showPopup();
        } else {
            comboPlayer.hidePopup();
        }
    }
}
