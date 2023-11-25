import java.util.Date;

public class Player {

    private final int id;
    private final Date creationDate;
    private String username;
    private String email;
    private int wins;
    private int matchCount;
    private int kills;


    public Player(int id, String username, String email, int wins, int matchCount, int kills, Date date)
    {
        this.id = id;
        this. username = username;
        this. email = email;
        this.wins = wins;
        this.matchCount = matchCount;
        this.kills = kills;
        this.creationDate = date;
    }

    public int getID()
    {
        return id;
    }
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getEmail()
    {
        return email;
    }
    public Date getCreationDate()
    {
        return creationDate;
    }
}