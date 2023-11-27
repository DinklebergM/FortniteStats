import java.util.Date;

/**
 * Klasse für die MatchResult Objekte
 * @author Marco Wolff 7215191, Jonas Teiner 7216279
 */
public class MatchResult
{
    private final int matchID;
    private Date matchDate;
    private int kills;
    private final int placement;

    /**
     * Konstruktor für die Klasse MatchResult
     * @param matchID
     * @param matchDate
     * @param kills
     * @param placement
     */
    public MatchResult(int matchID, Date matchDate, int kills, int placement)
    {
        this.matchID = matchID;
        this.matchDate= matchDate;
        this.placement = placement;
        this.kills=kills;
    }

    /**
     * Gibt die ID des Matches zurück
     * @return ID des Matches
     */
    public int getMatchId()
    {
        return matchID;
    }

    /**
     * Gibt das Datum des Matches zurück
     * @return Datum des Matches
     */
    public Date getMatchDate() {return matchDate;}

    /**
     * Gibt die Platzierung des Matches zurück
     * @return Platzierung des Matches
     */
    public int getPlacement()
    {
        return placement;
    }

    /**
     * Gibt die Anzahl der Kills des Matches zurück
     * @return Anzahl der Kills des Matches
     */
    public int getKills() {return kills;}
}