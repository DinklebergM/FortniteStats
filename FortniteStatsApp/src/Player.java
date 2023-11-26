import java.util.Date;

public class Player {

    private final int id;

    private String username;
    private String email;
    private int wins;
    private int matchCount;
    private int kills;
    private String topWeapon;
    private int topWeaponKills;


    public Player(int id, String username, String email, int wins, int matchCount, int kills,String topWeapon,int topWeaponKills)
    {
        this.id = id;
        this. username = username;
        this. email = email;
        this.wins = wins;
        this.matchCount = matchCount;
        this.kills = kills;
        this.topWeapon = topWeapon;
        this.topWeaponKills = topWeaponKills;

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
    public int getWins() {return wins;}
    public int getKills() {

        return kills;

    }
    public int getMatchCount()
    {
        return matchCount;
    }
    public String getTopWeapon()
    {
        return topWeapon;
    }
    public int getTopWeaponKills()
    {
        return topWeaponKills;
    }

}