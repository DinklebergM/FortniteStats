import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
/**
 * Klasse für die letzten 10 Matches eines Spielers
 * @author Marco Wolff 7215191, Jonas Teiner 7216279
 */
public class LastMatches {
    public JPanel panelLastMatches;
    private JTable tableMatches;
    private DefaultTableModel model;

    /**
     * Konstruktor für die Klasse LastMatches
     * @param datenbank
     * @param id
     */
    public LastMatches(Datenbank datenbank, int id) {

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.setColumnIdentifiers(new String[]{ "Match_ID","Match_Date", "Placement", "Kills"});
        tableMatches.setModel(model);
        List<MatchResult> matches = datenbank.getLastTenMatches(id);
        model.addRow(new Object[]{"Match ID","Match Date", "Placement", "Kills"});
        for (MatchResult match : matches) {
            model.addRow(new Object[]{match.getMatchId(), match.getMatchDate(), match.getPlacement(), match.getKills()});
        }
    }
}
