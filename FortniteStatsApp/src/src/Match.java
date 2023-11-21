import java.util.Date;

public class Match {

    private final int id;
    private final Date date;
    private final int duration;

    public Match(int id, Date date, int duration)
    {
        this.id = id;
        this.date = date;
        this.duration = duration;
    }

    public int getID()
    {
        return id;
    }

    public Date getDate()
    {
        return date;
    }

    public int getDuration()
    {
        return duration;
    }
}
