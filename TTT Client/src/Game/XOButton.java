package Game;


import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;


/**
 *
 * @author othmane
 */
public class XOButton extends JButton
{

    static ImageIcon X, O;
    byte value = 0;
    boolean used = false;
    int number;

    public XOButton(int number) 
    {
        this.number = number;
        X = new ImageIcon(this.getClass().getResource("X.png"));
        O = new ImageIcon(this.getClass().getResource("O.png"));
        setOpaque(true);
        setBackground(new Color(255, 255, 255));
         
        javax.swing.border.Border border = BorderFactory.createLineBorder(new Color(240, 240, 240));
        setBorder(border);
//        this.addActionListener(this);
    }

    public static ImageIcon getXIcon() {
        return X;
    }


    public static ImageIcon getOIcon() {
        return O;
    }


    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    
        
}
