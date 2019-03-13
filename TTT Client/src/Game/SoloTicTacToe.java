package Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author othmane
 */
public class SoloTicTacToe extends JFrame {

    JPanel p = new JPanel();
    XOButton buttons[] = new XOButton[9];
    private int turn = 0;
    private char grid[] = new char[10];

    public static void main(String[] args) {
        new SoloTicTacToe();
    }

    public SoloTicTacToe() {
        super("Tic Tac Toe");
        
        setSize(400, 400);
        setResizable(false);
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GridLayout g = new GridLayout(3, 3);
        
        p.setLayout(g);
        g.setHgap(3);
        g.setVgap(3);

        
        for (int i = 1; i <= 9; ++i) {
            grid[i] = '.';
        }

        for (int i = 1; i <= 9; i++) {
            XOButton btn = new XOButton(i);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    playMove(btn.getNumber());
                    printGrid();
                    checkGameState();
                }
            });
            p.add(btn);
        }
        add(p);
        setVisible(true);
    }

    private void nextTurn() {
        turn++;
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

    void playSound() {
        String soundName;
        if(turn % 2 == 0) soundName = "soundX.wav";
        else soundName = "soundO.wav";

        try {
            AudioInputStream audioInputStream;
            audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(SoloTicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SoloTicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(SoloTicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void showDialogMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private XOButton getButton(int n) {
        Object tmp = p.getComponent(n);
        XOButton Nv = (XOButton) tmp;
        return Nv;
    }

    private char getWinner() {
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

    private boolean checkDraw() {
        // checking if the grid is full
        for(int i = 1; i <= 9 ; ++i) {
            if(grid[i] == '.') return false;
        }
        return true;
    }

    private void resetGame() {
        for (int i = 1; i <= 9; ++i) {
            grid[i] = '.';
            XOButton Nv = getButton(i - 1);
            Nv.setIcon(null);
            Nv.setUsed(false);
            turn = 0;
        }

    }

    private void playMove(int x) {

        // select clicked button
        XOButton target = getButton(x - 1);

        // check if the button is already used
        if (target.isUsed()) {
            //playErrorSound();
            return;
        }
        playSound();
        target.setUsed(true);

        // update the GUI and the Grid
        switch (turn % 2) {
            case 0:
                target.setIcon(XOButton.getXIcon());
                grid[x] = 'X';
                break;
            case 1:
                target.setIcon(XOButton.getOIcon());
                grid[x] = '0';
                break;
        }

        // increment turn
        turn++;
    }

    private void checkGameState() {
        if (getWinner() != '.') {
            showDialogMessage("Result", "Winner is Player " + getWinner() + " !");
            resetGame();
        } else if (checkDraw()) {
            showDialogMessage("Result", "Draw !");
            resetGame();
        }
    }

}
