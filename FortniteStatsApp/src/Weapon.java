public class Weapon
{

    private final int id;
    private final String name;
    private final String category;
    private final String dps;

    public Weapon(int id, String name, String category, String dps)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.dps = dps;
    }

    public int getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public String getCategory()
    {
        return category;
    }
    public String getDps()
    {
        return dps;
    }
}