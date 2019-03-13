package Game;

import Application.Accueil;
import Application.Connection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author othmane
 */
public class TicTacToe extends JFrame {

    // socket variables
    private Connection con;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    // Graphics variables
    JPanel p = new JPanel(new GridLayout(3, 3));
    private JFrame window = this;
    XOButton buttons[] = new XOButton[9];
    JLabel playersNames;
    JLabel txtUserTurn;

    // Gameplay variables
    private int turn = 0;
    private char symbol = 'X';
    private int gameID;
    private String username;
    private String userTurn = "";

    // State of the game
    private char grid[] = new char[10];

    public TicTacToe(String title, char mySymbol, int gameID, String username) {
        super(title);

        this.symbol = mySymbol;
        this.gameID = gameID;
        this.username = username;

        con = new Connection();
        input = con.getInput();
        output = con.getOutput();

        window.setSize(400, 400);

        setResizable(false);
        
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.add(p, BorderLayout.CENTER);
        window.setLocationRelativeTo(null);

        playersNames = new JLabel("YOU ARE : " + symbol, JLabel.CENTER);
        playersNames.setHorizontalTextPosition(JLabel.CENTER);
        playersNames.setFont(new Font("Calibri", Font.PLAIN, 21));

        txtUserTurn = new JLabel(getUserTurn(), JLabel.CENTER);
        txtUserTurn.setHorizontalTextPosition(JLabel.CENTER);
        txtUserTurn.setFont(new Font("Calibri", Font.PLAIN, 21));

        window.add(playersNames, BorderLayout.NORTH);
        window.add(txtUserTurn, BorderLayout.SOUTH);

        for (int i = 0; i <= 9; ++i) {
            grid[i] = '.';
        }
        
        
        for (int i = 1; i <= 9; i++) {
            XOButton btn = new XOButton(i);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Btn Touched : " + btn.getNumber());

                    if (getUserTurn().equalsIgnoreCase(username)) {
                        System.out.println("Move Started : " + btn.getNumber());
                        if (playMove(btn.getNumber())) {
                            printGrid();
                            System.out.println("Move played : " + btn.getNumber());
                        }

                    } else {
                        System.out.println("userTurn : " + getUserTurn() + " username : " + username);
                        System.out.println("Move not played : " + btn.getNumber());
                    }

                }
            });
            p.add(btn);
        }
        
        System.out.println("I'm " + username + ", Symbol : " + symbol);
        window.setVisible(true);
        refreshDisplay();      
    }

    public String getUserTurn() {
        String userTurn = "";
        try {
            output.writeObject("12");
            output.writeObject(gameID);
            userTurn = (String) input.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return userTurn;
    }

    public void nextTurn() {
        turn++;
    }

    public String getGameState() {
        return String.valueOf(grid);
    }

    private void printGrid() {
        System.out.println("Turn #" + turn + ": ");
        for (int i = 1; i <= 9; ++i) {
            System.out.print(grid[i] + " ");
            if (i % 3 == 0) {
                System.out.print("\n");
            }
        }
        System.out.print("\n");
    }

    private void showDialogMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private XOButton getButton(int n) {
        Object tmp = p.getComponent(n);
        XOButton Nv = (XOButton) tmp;
        return Nv;
    }

    public char getWinner() {
        char winner = '.';

        // check Lines
        if (grid[1] == grid[2] && grid[2] == grid[3]) {
            if (winner == '.') {
                winner = grid[1];
            }
        }
        if (grid[4] == grid[5] && grid[5] == grid[6]) {
            if (winner == '.') {
                winner = grid[4];
            }
        }
        if (grid[7] == grid[8] && grid[8] == grid[9]) {
            if (winner == '.') {
                winner = grid[7];
            }
        }

        // check Columns
        if (grid[1] == grid[4] && grid[4] == grid[7]) {
            if (winner == '.') {
                winner = grid[1];
            }
        }
        if (grid[2] == grid[5] && grid[5] == grid[8]) {
            if (winner == '.') {
                winner = grid[2];
            }
        }
        if (grid[3] == grid[6] && grid[6] == grid[9]) {
            if (winner == '.') {
                winner = grid[3];
            }
        }

        // check Diagonals
        if (grid[1] == grid[5] && grid[5] == grid[9]) {
            if (winner == '.') {
                winner = grid[5];
            }
        }
        if (grid[3] == grid[5] && grid[5] == grid[7]) {
            if (winner == '.') {
                winner = grid[5];
            }
        }

        return winner;

    }

    public boolean checkDraw() {
        // checking if the grid is full
        for(int i = 1; i <= 9 ; ++i) {
            if(grid[i] == '.') return false;
        }
        return true;
    }

    public boolean isFinished() {
        return (getWinner() != '.' || checkDraw() == true);
    }

    public void resetGame() {
        for (int i = 1; i <= 9; ++i) {
            grid[i] = '.';
            XOButton Nv = getButton(i - 1);
            Nv.setIcon(null);
            Nv.setUsed(false);
            turn = 0;
        }

    }

    public void refreshDisplay() {
        ActionListener task = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    output.writeObject("10");
                    output.writeObject(gameID);
                    String currState = (String) input.readObject();
                    
                    updateDisplay(currState);

                    if (checkGameState() == true) {
                        ((Timer) evt.getSource()).stop();
                        dispose();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

            }
        };

        Timer timer = new Timer(200, task);

        timer.start();
    }

    public boolean playMove(int x) {
        
        // select clicked button
        XOButton target = getButton(x - 1);

        // check if the button is already used
        if (target.isUsed()) {
            return false;
        }
        
        playSound(symbol);

        target.setUsed(true);

        // update the GUI and the Grid
        switch (symbol) {
            case 'X':
                target.setIcon(XOButton.getXIcon());
                grid[x] = 'X';
                System.out.println("I played X");
                break;
            case '0':
                target.setIcon(XOButton.getOIcon());
                grid[x] = '0';
                System.out.println("I played O");
                break;
        }

        // switch userTurn on database
        try {
            output.writeObject("13");
            output.writeObject(gameID);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        turn++;

        try {
            output.writeObject("11");
            output.writeObject(gameID);
            output.writeObject(getGameState());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return true;

    }
    
    void playSound(char sign) {
        String soundName;
        if(sign == 'X') soundName = "soundX.wav";
        else soundName = "soundO.wav";

        try {
            AudioInputStream audioInputStream;
            audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }

    }

    public boolean checkGameState() {
        if (getWinner() != '.') {
            showDialogMessage("Result", "Winner is Player " + getWinner() + " !");
            if(symbol == 'X')  updateGameScore(getWinner());
            dispose();            
            return true;
            
        } else if (checkDraw()) {
            showDialogMessage("Result", "Draw !");
            if(symbol == 'X') updateGameScore(getWinner());
            dispose();
            return true;
        }
        return false;
    }

    private void updateGameScore(char winner) {

        String playerX = getPlayerX();
        String playerO = getPlayerO();

        if (winner == 'X') {
            setGameWinner(playerX);
            updateWins(playerX);
            updateLosses(playerO);
        } else if (winner == '0') {
            setGameWinner(playerO);
            updateWins(playerO);
            updateLosses(playerX);
        } else {
            updateDraws(playerO);
            updateDraws(playerX);
            setGameDraw();
        }

        setGameFinished();
    }

    public void setGameWinner(String winner) {
        try {
            output.writeObject("18");
            output.writeObject(gameID);
            output.writeObject(winner);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateWins(String username) {
        try {
            output.writeObject("19");
            output.writeObject(username);
        } catch (IOException ex) {
            ex.printStackTrace();;
        }
    }
    
    public void updateLosses(String username) {
        try {
            output.writeObject("20");
            output.writeObject(username);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateDraws(String username) {
        try {
            output.writeObject("21");
            output.writeObject(username);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setGameFinished() {
        try {
            output.writeObject("14");
            output.writeObject(gameID);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
     public void setGameDraw() {
        try {
            output.writeObject("15");
            output.writeObject(gameID);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void updateDisplay(String state) {

        System.out.println("State update : " + state);
        txtUserTurn.setText("Turn : " + getUserTurn());

        for (int i = 1; i <= 9; ++i) {

            XOButton target = getButton(i - 1);

            // if its already used or its my move
            if (target.used == true || state.charAt(i) == '.') {
                continue;
            }
            
            // Ennemy move
            target.setUsed(true);

            switch (symbol) {
                case '0':
                    grid[i] = 'X';
                    playSound('X');
                    target.setUsed(true);
                    target.setIcon(XOButton.getXIcon());
                    break;
                case 'X':
                    grid[i] = '0';
                    playSound('0');
                    target.setUsed(true);
                    target.setIcon(XOButton.getOIcon());
                    break;
            }

        }
    }

    private String getWinnerName() {
        String winner = "";
        try {
            output.writeObject("14");
            winner = (String) input.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return winner;
    }

    private String getPlayerX() {
        String playerX = "";
        try {
            output.writeObject("16");
            output.writeObject(gameID);
            playerX = (String) input.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return playerX;
    }

    private String getPlayerO() {
        String playerX = "";
        try {
            output.writeObject("17");
            output.writeObject(gameID);
            playerX = (String) input.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return playerX;
    }

}
