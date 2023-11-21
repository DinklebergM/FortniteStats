public class WeaponStatistic
{
    private final int playerId;
    private final int weaponId;
    private int kills;
    private int hits;
    private int damage;

    public WeaponStatistic(int playerId, int weaponId, int kills, int hits, int damage)
    {
        this.playerId = playerId;
        this.weaponId = weaponId;
        this.kills = kills;
        this.hits = hits;
        this.damage = damage;
    }
    public int getPlayerId()
    {
        return  playerId;
    }
    public int getWeaponId()
    {
        return weaponId;
    }
    public void addKills(int additionalKills)
    {
        kills += additionalKills;
    }
    public void addHits(int additionalHits)
    {
        hits += additionalHits;
    }
    public void addDamage(int addiotionalDamage)
    {
        damage += addiotionalDamage;
    }


}
