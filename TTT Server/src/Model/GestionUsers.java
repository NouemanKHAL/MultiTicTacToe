package Model;

import Cryptography.CryptoRSA;
import Dao.DaoBD;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
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
public class GestionUsers {

    private Connection con;
    private Statement st;

    public GestionUsers() {
        DaoBD dao = new DaoBD();
        dao.Connect();
        con = dao.getCon();
    }

    public int Login(String username, String password) {
        ResultSet rs = null;
        byte[] cryptedPassword = getUserCryptedPassword(username);
        System.out.println("Crypted Password length is : " + cryptedPassword.length);
        System.out.println("Bytes Password length is : " + cryptedPassword.length);
        if (CryptoRSA.decrypt(cryptedPassword).equals(password)) {
            if (isUserConnected(username)) {
                return 0;
            }
            updateUserConnected(username, true);
            return 1;
        }
        return -1;
    }

    public boolean Register(String username, String password) {
        int res = 0;
        String sql = "INSERT INTO users(username, password, registered) VALUES(?, ?, NOW())";
        try {
            PreparedStatement pst = this.con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setBytes(2, CryptoRSA.encrypt(password));
            res = pst.executeUpdate();
            System.out.println(res + " created user.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (res != 0);
    }

    // make waiting = true
    public ResultSet getUsersWaiting(String username) {
        ResultSet rs = null;
        String sql = "SELECT username, win as Wins, draw as Draws, loss as Losses FROM users Where waiting = true and connected = true and username != '" + username + "'";

        try {
            this.st = this.con.createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public ResultSet getUserInfo(String username) {
        ResultSet rs = null;
        String sql = "SELECT win, draw, loss FROM users WHERE username = '" + username + "'";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            return rs;
        }
    }

    public byte[] getUserCryptedPassword(String username) {
        byte[] res = {'0'};
        ResultSet rs = null;
        String sql = "SELECT password FROM users WHERE username = '" + username + "'";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                return rs.getBytes(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    public String getUserIP(String username) {
        ResultSet rs = null;
        String sql = "SELECT ipAddress FROM users WHERE username = '" + username + "'";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                return rs.getString(0);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public boolean isUserConnected(String username) {
        ResultSet rs = null;
        String sql = "SELECT connected FROM users WHERE username = '" + username + "'";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void updateUserConnected(String username, boolean value) {
        int res = 0;
        String sql = "UPDATE users SET connected = ? WHERE username = ?";
        try {
            PreparedStatement pst = this.con.prepareStatement(sql);
            pst.setBoolean(1, value);
            pst.setString(2, username);

            res = pst.executeUpdate();
            System.out.println(res + " updated user.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserWaiting(String username, boolean value) {
        int res = 0;
        String sql = "UPDATE users SET waiting = ? WHERE username = ?";
        try {
            PreparedStatement pst = this.con.prepareStatement(sql);
            pst.setBoolean(1, value);
            pst.setString(2, username);

            res = pst.executeUpdate();
            System.out.println(res + " updated user.");
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

    public void updateWins(String username) {
        int res = 0;
        String sql = "UPDATE users SET win = win + 1, waiting = false WHERE username = ?";
        try {
            PreparedStatement pst = this.con.prepareStatement(sql);

            pst.setString(1, username);

            res = pst.executeUpdate();
            System.out.println(res + " updated Wins of user : " + username + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLosses(String username) {
        int res = 0;
        String sql = "UPDATE users SET loss = loss + 1, waiting = false WHERE username = ?";
        try {
            PreparedStatement pst = this.con.prepareStatement(sql);

            pst.setString(1, username);

            res = pst.executeUpdate();
            System.out.println(res + " updated Wins of user : " + username + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDraws(String username) {
        int res = 0;
        String sql = "UPDATE users SET draw = draw + 1, waiting = false WHERE username = ?";
        try {
            PreparedStatement pst = this.con.prepareStatement(sql);

            pst.setString(1, username);

            res = pst.executeUpdate();
            System.out.println(res + " updated Wins of user : " + username + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
