import java.sql.*;
import oracle.jdbc.OracleTypes;
import java.util.*;


public class Datenbank {
private int ownUserID;
    Connection con;

    public Datenbank() {
        this.init();
    }

    public int getOwnUserID() {
        return ownUserID;
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

    public int checkUserID(String username) {
        try {
            CallableStatement call = this.con.prepareCall("{call P_CHECK_USERNAME(?,?,?)}");

            try {
                call.setString(1, username);
                call.registerOutParameter(2, Types.INTEGER);
                call.registerOutParameter(3, Types.INTEGER);
                call.execute();
                int exists = call.getInt(2);
                int userId = call.getInt(3);

                if (exists > 0) {
                    call.close();

                    this.ownUserID=userId;
                  return userId;
                } else {
                    call.close();

                    return 0;
                }



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


        } catch (SQLException var7) {
            System.out.println(var7);
            return 0;
        }

    }

    public void friendAction(int playerId1, int playerId2, int status){

        try {
            CallableStatement call = this.con.prepareCall("{call P_FRIENDACTION(?,?,?)}");
            try {
                call.setString(1, String.valueOf(playerId1));
                call.setString(2, String.valueOf(playerId2));
                call.setString(3, String.valueOf(status));
                call.execute();
            }finally {
                if (call != null) {
                    call.close();
                }
            }
        } catch (SQLException e) {
            // Hier können Sie die Fehlermeldung ausgeben oder anderweitig reagieren
            System.err.println("SQL-Fehler: " + e.getMessage());
        }
    }
    public List<User> getAllPlayer(){
        CallableStatement call = null;
        ResultSet resultSet = null;
        try {
            call = this.con.prepareCall("{call P_GET_ALL_USERNAMES_AND_IDS(?)}");
            call.registerOutParameter(1, OracleTypes.CURSOR);
            call.execute();
            resultSet = (ResultSet) call.getObject(1);

            // Erstellen Sie eine Liste oder ein Array, um die Spielerdaten zu speichern
            List<User> userList = new ArrayList<>();

            while (resultSet.next()) {

                String username = resultSet.getString("username");
                int userId = resultSet.getInt("id");
                userList.add(new User( userId,username));
            }


            return userList;
        } catch (SQLException e) {
            // Hier könnten Sie prüfen, ob es sich um einen bestimmten SQL-Fehler handelt
            // und entsprechend reagieren
            System.out.println("SQL-Fehler: " + e.getMessage());
            return null; // oder werfen Sie eine benutzerdefinierte Ausnahme
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (call != null) call.close();
            } catch (SQLException e) {
                System.out.println("Fehler beim Schließen der Ressourcen: " + e.getMessage());
            }
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