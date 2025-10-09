import java.awt.*; 
import javax.swing.*; 

/**
 * displays the homescreen.
 */
public class Homescreen extends JFrame {

    public Homescreen() {
        frame();
    }

    /**
     * creates the frame of the homescreen, adds the different buttons
     * to access the game, controls and help.
     */
    void frame() { 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setResizable(false); 

        JPanel panelGeneral = panelGeneral(); 
        this.add(panelGeneral);

        this.setVisible(true);
    }

    private JPanel panelGeneral() {
        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(null); 
        panelGeneral.setBackground(Color.BLACK);
        return panelGeneral;
    }
}
