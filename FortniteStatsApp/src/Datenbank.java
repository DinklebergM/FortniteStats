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
            CallableStatement call = this.con.prepareCall("{call P_FRIEND_ACTION(?,?,?)}");
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
    public List<Player> getAllPlayer(){
        CallableStatement call = null;
        ResultSet resultSet = null;
        try {
            call = this.con.prepareCall("{call P_GET_ALL_USERNAMES_AND_IDS(?)}");
            call.registerOutParameter(1, OracleTypes.CURSOR);
            call.execute();
            resultSet = (ResultSet) call.getObject(1);

            // Erstellen Sie eine Liste oder ein Array, um die Spielerdaten zu speichern
            List<Player> userList = new ArrayList<>();

            while (resultSet.next()) {

                String username = resultSet.getString("username");
                int userId = resultSet.getInt("id");
                userList.add(new Player( userId,username, null, 0, 0, 0, null));
            }


            return userList;
        } catch (SQLException e) {

            System.out.println("SQL-Fehler: " + e.getMessage());
            return null;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (call != null) call.close();
            } catch (SQLException e) {
                System.out.println("Fehler beim Schließen der Ressourcen: " + e.getMessage());
            }
        }
    }

    public List<MatchResult> getLastTenMatches(int id) {
        List<MatchResult> matchResults = new ArrayList<>();
        CallableStatement call = null;
        ResultSet resultSet = null;

        try {
            call = this.con.prepareCall("{call P_GET_LAST_10_MATCHES(?,?)}");
            call.setInt(1, id);
            call.registerOutParameter(2, OracleTypes.CURSOR);
            call.execute();
            resultSet = (ResultSet) call.getObject(2);

            while (resultSet.next()) {
                int matchID = resultSet.getInt("MATCHID");
                java.util.Date matchDate = resultSet.getDate("MATCH_DATE");
                int placement = resultSet.getInt("PLACEMENT");
                int kills = resultSet.getInt("KILLS");
                matchResults.add(new MatchResult(matchID, matchDate, placement, kills));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Schließen der Ressourcen
            if (resultSet != null) {
                try { resultSet.close(); } catch (SQLException e) { /* Handhabung */ }
            }
            if (call != null) {
                try { call.close(); } catch (SQLException e) { /* Handhabung */ }
            }
        }
        return matchResults;
    }

    public List<Player> getFriends(int id){
        List<Player> player = new ArrayList<>();
        CallableStatement call ;
        ResultSet resultSet ;
        try {
            call = this.con.prepareCall("{call P_GET_FRIENDS(?)}");
            call.setInt(1, id);
            call.registerOutParameter(2, OracleTypes.CURSOR);
            call.execute();
            resultSet = (ResultSet) call.getObject(2);
            while (resultSet.next()) {
                int playerId = resultSet.getInt("ID");
                String username = resultSet.getString("USERNAME");
                player.add(new Player(playerId, username, null, 0, 0, 0, null));
            }

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        ;
        return player;
    }

    public List<Player> getFriendrequest(int id){
        List<Player> player = new ArrayList<>();
        CallableStatement call ;
        ResultSet resultSet ;
        try {
            call = this.con.prepareCall("{call P_GET_FRIENDREQUESTS(?)}");
            call.setInt(1, id);
            call.registerOutParameter(2, OracleTypes.CURSOR);
            call.execute();
            resultSet = (ResultSet) call.getObject(2);
            while (resultSet.next()) {
                int playerId = resultSet.getInt("ID");
                String username = resultSet.getString("USERNAME");
                player.add(new Player(playerId, username, null, 0, 0, 0, null));
            }

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        ;
        return player;
    }
}