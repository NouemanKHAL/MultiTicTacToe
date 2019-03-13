package App;

import Cryptography.CryptoBlowfish;
import Model.GestionChatG;
import Model.GestionGames;
import Model.GestionUsers;
import Model.MonModele;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nouemankhal
 */
public class ClientHandler extends Thread {

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private GestionUsers GU;
    private GestionChatG GCG;
    private GestionGames GG;

    public ClientHandler(Socket s, ObjectInputStream Input, ObjectOutputStream Output) {
        this.socket = s;
        this.input = Input;
        this.output = Output;
        GU = new GestionUsers();
        GCG = new GestionChatG();
        GG = new GestionGames();
    }

    @Override
    public void run() {
        String received;
        String answer;
        while (true) {
            try {
                // receive the request from client 
                received = (String) input.readObject();
                System.out.println(Thread.currentThread().getName() + " - Received request of type : " + received);

                // write on output stream based on the 
                 
                switch (received) {
                    case "0":
                        Register();
                        break;
                    case "1":
                        Login();
                        break;
                    case "2":
                        getUsersWaiting();
                        break;
                    case "3":
                        getUserInfo();
                        break;
                    case "4":
                        Disconnect();
                        break;
                    case "5":
                        Play();
                        break;
                    case "6":
                        getGeneralChatMessages();
                        break;
                    case "7":
                        addGeneralChatMessages();
                        break;
                    case "8":
                        startGame();
                        break;
                    case "9":
                        findGame();
                        break;
                    case "10":
                        getGameState();
                        break;
                    case "11":
                        playMove();
                        break;
                    case "12":
                        getUserTurn();
                        break;
                    case "13":
                        switchTurn();
                        break;
                    case "14":
                        finishGame();
                        break;
                    case "15":
                        setGameDraw();
                        break;
                    case "16":
                        getPlayerX();
                        break;
                    case "17":
                        getPlayerO();
                        break;
                    case "18":
                        setGameWinner();
                        break;
                    case "19":
                        updateWins();
                        break;
                    case "20":
                        updateLosses();
                        break;
                    case "21":
                        updateDraws();
                        break;
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }

    }

    void startGame() {
        try {
            String username = (String) input.readObject();
            String target = (String) input.readObject();

            GU.updateUserWaiting(username, false);
            GU.updateUserWaiting(target, false);
            
            GG.createGame(target, username);
            

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    void findGame() {
        try {
            String username = (String) input.readObject();
            String type = (String) input.readObject();
            if (type.equalsIgnoreCase("X")) {
                output.writeObject((GG.getUserXCurrentGameID(username)));
                return;
            }
            if (type.equalsIgnoreCase("O")) {
                output.writeObject((GG.getUserOCurrentGameID(username)));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    void getGameState() {
        try {
            int gameID = (int) input.readObject();
            String state = GG.getGameState(gameID);
            output.writeObject(state);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    void playMove() {
        try {
            int gameID = (int) input.readObject();
            String state = (String) input.readObject();
            GG.setGameState(gameID, state);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    void switchTurn() {
        try {
            int gameID = (int) input.readObject();
            GG.switchTurn(gameID);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    void finishGame() {
        try {
            int gameID = (int) input.readObject();
            GG.setGameFinished(gameID);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    void setGameDraw() {
        try {
            int gameID = (int) input.readObject();
            GG.setGameDraw(gameID);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    void getUserTurn() {
        try {
            int gameID = (int) input.readObject();
            String userTurn = GG.getUserTurn(gameID);
            output.writeObject(userTurn);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    void setGameWinner() {
        try {
            int gameID = (int) input.readObject();
            String winner = (String) input.readObject();
            GG.setGameWinner(gameID, winner);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    void updateWins() {
        try {
            String username = (String) input.readObject();
            GU.updateWins(username);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    void updateLosses() {
        try {
            String username = (String) input.readObject();
            GU.updateLosses(username);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    void updateDraws() {
        try {
            String username = (String) input.readObject();
            GU.updateDraws(username);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    void Register() {
        try {
            String username = CryptoBlowfish.decryptBlowfish((byte[]) input.readObject());
            String password = CryptoBlowfish.decryptBlowfish((byte[]) input.readObject());
            if (GU.Register(username, password)) {
                output.writeObject("YES");
            } else {
                output.writeObject("NO");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    void Login() {
        try {
            String username = CryptoBlowfish.decryptBlowfish((byte[]) input.readObject());
            String password = CryptoBlowfish.decryptBlowfish((byte[]) input.readObject());

            int result = GU.Login(username, password);
            if (result == 1) {
                output.writeObject("YES");
                GU.setUserIP(username, this.socket.getRemoteSocketAddress().toString());
            } else if (result == -1) {
                output.writeObject("NO");
            } else {
                output.writeObject("MULTI");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    void getUsersWaiting() {
        try {
            String username = (String) input.readObject();
            ResultSet rs = GU.getUsersWaiting(username);

            MonModele md = new MonModele(rs);

            output.writeObject(md.getTitres());
            output.writeObject(md.getMesLignes());

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    void getUserInfo() {
        try {
            String username = (String) input.readObject();
            System.out.println("Users Info asked by :" + username);
            ResultSet rs = GU.getUserInfo(username);

            if (rs.next()) {
                String wins = rs.getInt(1) + "";
                output.writeObject(wins);

                String draws = rs.getInt(2) + "";
                output.writeObject(draws);

                String losses = rs.getInt(3) + "";
                output.writeObject(losses);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void Disconnect() {
        String username;
        try {
            username = (String) input.readObject();
            
            GU.updateUserConnected(username, false);
            GU.updateUserWaiting(username, false);
            
            System.out.println("Client " + this.socket + " sends exit...");
            System.out.println("Closing this connection.");
            
            this.socket.close();
            this.input.close();
            this.output.close();
            
            System.out.println("Connection closed");

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    void Play() {
        String username;
        try {
            username = (String) input.readObject();
            GU.updateUserWaiting(username, true);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    void getGeneralChatMessages() {
        try {
            ResultSet rs = GCG.getMessages();

            MonModele md = new MonModele(rs);

            output.writeObject(md.getTitres());
            output.writeObject(md.getMesLignes());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void addGeneralChatMessages() {
        try {
            String username = (String) input.readObject();
            String message = (String) input.readObject();
            GCG.addMessage(username, message);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void getPlayerX() {
        try {
            int gameID = (int) input.readObject();
            output.writeObject(GG.getPlayerX(gameID));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void getPlayerO() {

        try {
            int gameID = (int) input.readObject();
            output.writeObject(GG.getPlayerO(gameID));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

}
