import java.sql.*;
import java.util.Calendar;

public class Datenbank {

    Connection con;

    public Datenbank()
    {
        this.init();
    }

    private void init()
    {
        String url = "jdbc:oracle:thin:@172.22.160.22:1521:XE";
        try(Connection con = DriverManager.getConnection(url))
        {
            this.con = con;
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }

    public void addPlayer(Player player)
    {
        String sqlString = "UPDATE PLAYERS SET id=?, " +
                "username=?, email=?, wins=?, count_matches=?, kills=?,create_at=?";



        try(PreparedStatement stmt = con.prepareStatement(sqlString);)
        {



            stmt.setInt(1, player.getID());
            stmt.setString(2, player.getUsername());
            stmt.setString(3, player.getEmail());
            stmt.setInt(4, 0);
            stmt.setInt(5, 0);
            stmt.setInt(6, 0);
            stmt.setDate(7, new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH));
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

    }

}
