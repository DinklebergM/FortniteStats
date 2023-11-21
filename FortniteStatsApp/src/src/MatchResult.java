public class MatchResult
{
    private final int matchID;
    private final int playerID;
    private final int placement;

    public MatchResult(int matchID, int playerID, int placement)
    {
        this.matchID = matchID;
        this.playerID = playerID;
        this.placement = placement;
    }

    public int getMatchID()
    {
        return matchID;
    }

    public int getPlayerID()
    {
        return playerID;
    }

    public int getPlacement()
    {
        return placement;
    }
}
