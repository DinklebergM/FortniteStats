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

    /**
     * Methode zum Überprüfen, ob der eingegebene Username existiert
     * @param username der im "login" eingebenen username
     * @return userId des Users, 0 wenn der Username nicht existiert
     */
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
            } catch (SQLException e) {
                System.out.println(e);
                return 0;
            }


        } catch (SQLException e) {
            System.out.println(e);
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
                userList.add(new Player( userId,username, null, 0, 0, 0, null,0));
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
            call = this.con.prepareCall("{call P_GET_FRIENDS(?,?)}");
            call.setInt(1, id);
            call.registerOutParameter(2, OracleTypes.CURSOR);
            call.execute();
            resultSet = (ResultSet) call.getObject(2);
            while (resultSet.next()) {
                int playerId = resultSet.getInt("ID");
                String username = resultSet.getString("USERNAME");
                player.add(new Player(playerId, username,  null, 0, 0, 0, null,0));
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
            call = this.con.prepareCall("{call P_GET_FRIENDREQUESTS(?,?)}");
            call.setInt(1, id);
            call.registerOutParameter(2, OracleTypes.CURSOR);
            call.execute();
            resultSet = (ResultSet) call.getObject(2);
            while (resultSet.next()) {
                int playerId = resultSet.getInt("ID");
                String username = resultSet.getString("USERNAME");
                player.add(new Player(playerId, username,  null, 0, 0, 0, null,0));
            }

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        ;
        return player;
    }

    public Player getPlayerProfile(int id){
        Player player = null;
        try {
            CallableStatement call ;
            call = this.con.prepareCall("{call P_GET_PLAYER_STATS_AND_TOP_WEAPON(?,?,?,?,?,?,?,?)}");
            call.setInt(1, id);
            call.registerOutParameter(2, Types.VARCHAR);//username
            call.registerOutParameter(3, Types.VARCHAR);//email
            call.registerOutParameter(4, Types.INTEGER);//wins
            call.registerOutParameter(5, Types.INTEGER);//kills
            call.registerOutParameter(6, Types.INTEGER);//matches
            call.registerOutParameter(7, Types.VARCHAR);//top weapon
            call.registerOutParameter(8, Types.INTEGER);//top weapon kills
            call.execute();

            player= new Player(id,call.getString(2),
                    call.getString(3),call.getInt(4),call.getInt(5),
                    call.getInt(6),call.getString(7),call.getInt(8));
            System.out.println(player);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }


        return player;
    }
    public void updatePlayerProfile(int id,String username,String email){
        try {
            CallableStatement call ;
            call = this.con.prepareCall("{call P_UPDATE_PROFILE(?,?,?)}");
            call.setInt(1, id);
            call.setString(2, username);
            call.setString(3, email);
            call.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}