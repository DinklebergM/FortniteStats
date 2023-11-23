import java.sql.*;
import java.util.Calendar;

public class Datenbank {

    Connection con;

    public Datenbank() {
        this.init();
    }

    private void init() {
        String url = "jdbc:oracle:thin:@172.22.160.22:1521:XE";
        String user = "C##FBPOOL211";
        String password = "oracle";
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void checkUserID() {
        try {
            CallableStatement call = this.con.prepareCall("{call P_CHECK_USERNAME(?,?,?)}");

            try {
                call.setString(1, "RandomPlayer5");
                call.registerOutParameter(2, Types.INTEGER);
                call.registerOutParameter(3, Types.INTEGER);
                call.execute();
                int exists = call.getInt(2);
                int userId = call.getInt(3);

                if (exists > 0) {
                    System.out.println("Username exists. User ID: " + userId);
                } else {
                    System.out.println("Username does not exist.");
                }

                call.close();
                this.con.close();
            } catch (Throwable var6) {
                if (call != null) {
                    try {
                        call.close();
                    } catch (Throwable var5) {
                        var6.addSuppressed(var5);
                    }
                }

                throw var6;
            }

            if (call != null) {
                call.close();
            }
        } catch (SQLException var7) {
            System.out.println(var7);
        }

    }

    /*
     * public void addPlayer(Player player) {
     * String sqlString = "UPDATE PLAYERS SET id=?, " +
     * "username=?, email=?, wins=?, count_matches=?, kills=?,create_at=?";
     * 
     * try (PreparedStatement stmt = con.prepareStatement(sqlString);) {
     * 
     * stmt.setInt(1, player.getID());
     * stmt.setString(2, player.getUsername());
     * stmt.setString(3, player.getEmail());
     * stmt.setInt(4, 0);
     * stmt.setInt(5, 0);
     * stmt.setInt(6, 0);
     * stmt.setDate(7, new Date(Calendar.YEAR, Calendar.MONTH,
     * Calendar.DAY_OF_MONTH));
     * } catch (SQLException e) {
     * System.out.println(e);
     * }
     * 
     * }
     */

}