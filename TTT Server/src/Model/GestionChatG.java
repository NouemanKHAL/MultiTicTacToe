package Model;

import Dao.DaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author nouemankhal
 */
public class GestionChatG {

    private Connection con;
    private Statement st;

    public GestionChatG() {
        DaoBD dao = new DaoBD();
        dao.Connect();
        con = dao.getCon();
    }

    public ResultSet getMessages() {
        ResultSet rs = null;
        String sql = "SELECT id_user, content, DATE_FORMAT(sent_at, \"%H:%i\") as time FROM generalChat  LIMIT 0,100";
        try {
           st = con.createStatement();
           rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }
    
    public boolean addMessage(String username, String message) {
        int res = 0;
        String sql = "INSERT INTO generalChat(id_user, content, sent_at) VALUES(?, ?, NOW())";
        try {
            PreparedStatement pst = this.con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, message);
            res = pst.executeUpdate();
            System.out.println(res + " inserted message.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (res > 0);
    }

}
