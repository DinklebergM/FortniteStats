import java.sql.*;

import oracle.jdbc.OracleTypes;

import java.util.*;

/**
 * Klasse für die Datenbankverbindung
 * @author Marco Wolff 7215191, Jonas Teiner 7216279
 */
public class Datenbank {
    private int ownUserID;
    Connection con;

    /**
     * Konstruktor für die Klasse Datenbank
     */
    public Datenbank() {
        this.init();
    }

    /**
     * Gibt die eigene UserID zurück
     * @return eigene UserID
     */
    public int getOwnUserID() {
        return ownUserID;
    }

    /**
     * Methode zum Initialisieren der Datenbankverbindung
     */
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
     *
     * @param username der im "login" eingebenen username
     * @return userId des Users, 0 wenn der Username nicht existiert
     */
    public int checkUserID(String username) {
        try {
            CallableStatement call = this.con.prepareCall("{call P_CHECK_USERNAME(?,?,?)}");


            call.setString(1, username);
            call.registerOutParameter(2, Types.INTEGER);
            call.registerOutParameter(3, Types.INTEGER);
            call.execute();
            int exists = call.getInt(2);
            int userId = call.getInt(3);

            if (exists > 0) {
                call.close();

                this.ownUserID = userId;
                return userId;
            } else {
                call.close();

                return 0;
            }
        } catch (SQLException e) {
            System.out.println("SQL-Fehler: " + e.getMessage());
            return 0;
        }

    }

    /**
     * Methode um eine Freundschaftsanfrage anzunehemen, abzulehenen oder zu löschen
     *
     * @param playerId1 the player id 1
     * @param playerId2 the player id 2
     * @param status    the status
     */
    public void friendAction(int playerId1, int playerId2, int status) {

        try {
            CallableStatement call = this.con.prepareCall("{call P_FRIEND_ACTION(?,?,?)}");

                call.setString(1, String.valueOf(playerId1));
                call.setString(2, String.valueOf(playerId2));
                call.setString(3, String.valueOf(status));
                call.execute();
                call.close();

        } catch (SQLException e) {
            System.out.println("SQL-Fehler: " + e.getMessage());
        }
    }

    /**
     * Holt alle Spieler aus der Datenbank und gibt sie als Liste zurück
     *
     * @return Liste aller Spieler
     */
    public List<Player> getAllPlayer() {
        CallableStatement call = null;
        ResultSet resultSet = null;
        try {
            call = this.con.prepareCall("{call P_GET_ALL_USERNAMES_AND_IDS(?)}");
            call.registerOutParameter(1, OracleTypes.CURSOR);
            call.execute();
            resultSet = (ResultSet) call.getObject(1);


            List<Player> userList = new ArrayList<>();

            while (resultSet.next()) {

                String username = resultSet.getString("username");
                int userId = resultSet.getInt("id");
                userList.add(new Player(userId, username, null, 0, 0, 0, null, 0));
            }
            call.close();
            return userList;
        } catch (SQLException e) {

            System.out.println("SQL-Fehler: " + e.getMessage());
            return null;
        }
    }

    /**
     * Holt die letzten 10 Matches eines Spielers aus der Datenbank und gibt sie als Liste zurück
     * @param id
     * @return Liste der letzten 10 Matches
     */
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
            call.close();
        } catch (SQLException e) {
            System.out.println("SQL-Fehler: " + e.getMessage());

        }

        return matchResults;
    }

    /**
     * Holt alle Freunde eines Spielers aus der Datenbank und gibt sie als Liste zurück
     * @param id
     * @return Liste der Freunde
     */
    public List<Player> getFriends(int id) {
        List<Player> player = new ArrayList<>();
        CallableStatement call;
        ResultSet resultSet;
        try {
            call = this.con.prepareCall("{call P_GET_FRIENDS(?,?)}");
            call.setInt(1, id);
            call.registerOutParameter(2, OracleTypes.CURSOR);
            call.execute();
            resultSet = (ResultSet) call.getObject(2);
            while (resultSet.next()) {
                int playerId = resultSet.getInt("ID");
                String username = resultSet.getString("USERNAME");
                player.add(new Player(playerId, username, null, 0, 0, 0, null, 0));
            }
            call.close();
        } catch (SQLException e) {
            System.out.println("SQL-Fehler: " + e.getMessage());
            return null;
        }
        return player;
    }

    /**
     * Holt alle Freundschaftsanfragen eines Spielers aus der Datenbank und gibt sie als Liste zurück
     * @param id
     * @return Liste der Freundschaftsanfragen
     */
    public List<Player> getFriendrequest(int id) {
        List<Player> player = new ArrayList<>();
        CallableStatement call;
        ResultSet resultSet;
        try {
            call = this.con.prepareCall("{call P_GET_FRIENDREQUESTS(?,?)}");
            call.setInt(1, id);
            call.registerOutParameter(2, OracleTypes.CURSOR);
            call.execute();
            resultSet = (ResultSet) call.getObject(2);
            while (resultSet.next()) {
                int playerId = resultSet.getInt("ID");
                String username = resultSet.getString("USERNAME");
                player.add(new Player(playerId, username, null, 0, 0, 0, null, 0));
            }
            call.close();
        } catch (SQLException e) {
            System.out.println("SQL-Fehler: " + e.getMessage());
            return null;
        }
        return player;
    }

    /**
     * Holt das Profil eines Spielers aus der Datenbank und gibt es als Objekt Player zurück
     * @param id
     * @return Profil des Spielers
     */
    public Player getPlayerProfile(int id) {
        Player player = null;
        try {
            CallableStatement call;
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

            player = new Player(id, call.getString(2),
                    call.getString(3), call.getInt(4), call.getInt(5),
                    call.getInt(6), call.getString(7), call.getInt(8));
            call.close();
        } catch (SQLException e) {
            System.out.println("SQL-Fehler: " + e.getMessage());
            return null;
        }
        return player;
    }

    /**
     * updated das Profil (Username und Email) eines Spielers in der Datenbank
     * @param id
     * @param username
     * @param email
     */
    public void updatePlayerProfile(int id, String username, String email) {
        try {
            CallableStatement call;
            call = this.con.prepareCall("{call P_UPDATE_PROFILE(?,?,?)}");
            call.setInt(1, id);
            call.setString(2, username);
            call.setString(3, email);
            call.execute();
            call.close();
        } catch (SQLException e) {
            System.out.println("SQL-Fehler: " + e.getMessage());
        }
    }
}