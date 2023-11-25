import java.util.Date;

public class MatchResult
{
    private final int matchID;
    private Date matchDate;
    private int kills;
    private final int placement;

    public MatchResult(int matchID, Date matchDate, int kills, int placement)
    {
        this.matchID = matchID;
        this.matchDate= matchDate;
        this.placement = placement;
        this.kills=kills;
    }

    public int getMatchId()
    {
        return matchID;
    }

    public Date getMatchDate() {return matchDate;}


    public int getPlacement()
    {
        return placement;
    }
    public int getKills() {return kills;}
}