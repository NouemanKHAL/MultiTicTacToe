package Model;

import Dao.DaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nouemankhal
 */
public class GestionGames {

    private Connection con;
    private Statement st;

    public GestionGames() {
        DaoBD dao = new DaoBD();
        dao.Connect();
        con = dao.getCon();
    }

    public void createGame(String userOne, String userTwo) {
        int res = 0;
        String sql = "INSERT INTO games(playerX, playerO, userTurn, created_at) VALUES(?, ?, ?, NOW());";
        try {
            PreparedStatement pst = this.con.prepareStatement(sql);
            pst.setString(1, userOne);
            pst.setString(2, userTwo);
            pst.setString(3, userOne);

            res = pst.executeUpdate();
            System.out.println("created a game (" + userOne + ", " + userTwo + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getUserXCurrentGameID(String username) {
        ResultSet rs = null;
        String sql = "SELECT id_game FROM games WHERE playerX = '" + username + "' and isFinished = false ORDER BY id_game DESC LIMIT 0,1";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("GameID for User : " + username + " Not found !!");
        return -1;
    }

    public int getUserOCurrentGameID(String username) {
        ResultSet rs = null;
        String sql = "SELECT id_game FROM games WHERE playerO= '" + username + "' and isFinished = false ORDER BY id_game DESC LIMIT 0,1";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public String getGameState(int gameID) {
        ResultSet rs = null;
        String sql = "SELECT state FROM games WHERE id_game = " + gameID;
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public void setGameState(int gameID, String state) {
        int res = 0;
        String sql = "UPDATE games SET state = ? WHERE id_game = ?";
        try {
            PreparedStatement pst = this.con.prepareStatement(sql);
            pst.setString(1, state);
            pst.setInt(2, gameID);

            res = pst.executeUpdate();
            System.out.println(res + " updated State of game : " + gameID + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUserTurn(int gameID) {
        ResultSet rs = null;
        String sql = "SELECT userTurn FROM games WHERE id_game = " + gameID;
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public void setGameWinner(int gameID, String winner) {
        int res = 0;
        String sql = "UPDATE games SET winner = ? WHERE id_game = ?";
        try {
            PreparedStatement pst = this.con.prepareStatement(sql);

            pst.setString(1, winner);
            pst.setInt(2, gameID);

            res = pst.executeUpdate();
            System.out.println(res + " updated Winner of game : " + gameID + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setGameDraw(int gameID) {
        int res = 0;
        String sql = "UPDATE games SET isDraw = true WHERE id_game = ?";
        try {
            PreparedStatement pst = this.con.prepareStatement(sql);

            pst.setInt(1, gameID);

            res = pst.executeUpdate();
            System.out.println(res + " updated Draws of game : " + gameID + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

    public String getPlayerX(int gameID) {
        ResultSet rs = null;
        String sql = "SELECT playerX FROM games WHERE id_game = " + gameID;
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public String getPlayerO(int gameID) {
        ResultSet rs = null;
        String sql = "SELECT playerO FROM games WHERE id_game = " + gameID;
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public void switchTurn(int gameID) {
        String userTurn = getUserTurn(gameID);
        String playerX = getPlayerX(gameID);
        String playerO = getPlayerO(gameID);
        int res = 0;
        String sql = "UPDATE games SET userTurn = ? WHERE id_game = ?";
        try {
            PreparedStatement pst = this.con.prepareStatement(sql);
            if (userTurn.equalsIgnoreCase(playerX)) {
                pst.setString(1, playerO);
            } else {
                pst.setString(1, playerX);
            }

            pst.setInt(2, gameID);

            res = pst.executeUpdate();
            System.out.println(res + " Switched Turns !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setUserIP(String username, String ipAddress) {
        int res = 0;
        String sql = "UPDATE users SET ipAddress = ? WHERE username = ?";
        try {
            PreparedStatement pst = this.con.prepareStatement(sql);
            pst.setString(1, ipAddress);
            pst.setString(2, username);

            res = pst.executeUpdate();
            System.out.println(res + " updated IP user.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setGameFinished(int gameID) {
        int res = 0;
        String sql = "UPDATE games SET isFinished = true WHERE id_game = ?";
        try {
            PreparedStatement pst = this.con.prepareStatement(sql);
            pst.setInt(1, gameID);
            res = pst.executeUpdate();
            System.out.println("Game ID : " + gameID + " finished.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
