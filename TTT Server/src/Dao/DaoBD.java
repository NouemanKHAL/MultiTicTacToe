package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author nouemankhal
 */
public class DaoBD {

    private static String Driver = "com.mysql.jdbc.Driver";
    private static String Url = "jdbc:mysql://localhost:3306/TicTacToe";
    private static String Login = "root";
    private static String Password = "Test9876#";
    private static Connection Con;
    private static boolean isConnected = false;

    public void Connect() {
        if(this.isConnected) return ;
        try {
            System.out.println("Chargement du pilote...");
            Class.forName(Driver);
            System.out.println("Chargement du pilote réussi !");
            Con = DriverManager.getConnection(Url, Login, Password);
            System.out.println("Connexion Etablie !");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur Pilote : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erreur Connection : " + e.getMessage());
        }
        
        isConnected = true;
    }

    public static String getDriver() {
        return Driver;
    }

    public static void setDriver(String Driver) {
        DaoBD.Driver = Driver;
    }

    public static String getUrl() {
        return Url;
    }

    public static void setUrl(String Url) {
        DaoBD.Url = Url;
    }

    public static String getLogin() {
        return Login;
    }

    public static void setLogin(String Login) {
        DaoBD.Login = Login;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setPassword(String Password) {
        DaoBD.Password = Password;
    }

    public static Connection getCon() {
        return Con;
    }

    public static void setCon(Connection Con) {
        DaoBD.Con = Con;
    }

}
