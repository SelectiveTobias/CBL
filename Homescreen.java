import java.awt.*; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*; 

/**
 * displays the homescreen.
 */
public class Homescreen extends JFrame implements ActionListener, KeyListener {
    String command;
    int blinkingRate = 500;
    int screenheight = Toolkit.getDefaultToolkit().getScreenSize().height;
    int screenwidth = Toolkit.getDefaultToolkit().getScreenSize().width;

    JPanel panelGeneral;
    JLabel pressSpaceToStart;
    JButton buttonScoreboard;
    JButton buttonExit;

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
        this.setUndecorated(true);

        panelGeneral = panelGeneral(); 
        this.add(panelGeneral);

        buttonScoreboard = createbuttonScoreboard();
        panelGeneral.add(buttonScoreboard);

        pressSpaceToStart = createPressSpaceToStart();
        panelGeneral.add(pressSpaceToStart);

        buttonExit = createButtonExit();
        panelGeneral.add(buttonExit);

        this.setVisible(true);

        blinkingLabel();
    }

    private JPanel panelGeneral() {
        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(null); 
        panelGeneral.setBackground(Color.BLACK);
        panelGeneral.addKeyListener(this);
        panelGeneral.setFocusable(true);
        panelGeneral.requestFocusInWindow();
        return panelGeneral;
    }

    private JButton createbuttonScoreboard() { 
        JButton buttonScoreboard = new JButton("Scoreboard"); 
        buttonScoreboard.setFont(new Font("Times New Roman", Font.PLAIN, 35)); 
        buttonScoreboard.setBorderPainted(true); 
        buttonScoreboard.setBounds(screenwidth / 2 - 150, 500, 300, 150); 
        buttonScoreboard.setBackground(Color.GRAY); 
        buttonScoreboard.setFocusPainted(false); 
        buttonScoreboard.setActionCommand("scoreboard"); 
        buttonScoreboard.addActionListener(this); 
        return buttonScoreboard; 
    } 

    private JButton createButtonExit() { 
        JButton buttonExit = new JButton("Exit");
        buttonExit.setFont(new Font("Times New Roman", Font.PLAIN, 25)); 
        buttonExit.setBorderPainted(true); 
        buttonExit.setBounds(screenwidth - 95, 20, 75, 30); 
        buttonExit.setBackground(Color.red); 
        buttonExit.setForeground(Color.WHITE);
        buttonExit.setFocusPainted(false); 
        buttonExit.setBorder(null); 
        buttonExit.setActionCommand("exit"); 
        buttonExit.addActionListener(this);
        return buttonExit;
    }

    private JLabel createPressSpaceToStart() {
        JLabel pressSpaceToStart = new JLabel();
        int yPositioning = screenheight - 200;
        double xPositioningDouble = screenwidth / 2;
        int xPositioning = (int) xPositioningDouble;
        pressSpaceToStart.setBounds(xPositioning, yPositioning, 500, 100); 
        pressSpaceToStart.setText("Press space to start"); 
        pressSpaceToStart.setFont(new Font("Times new Roman", Font.BOLD, 50)); 
        pressSpaceToStart.setOpaque(true); 
        pressSpaceToStart.setBackground(Color.BLACK); 
        pressSpaceToStart.setForeground(Color.WHITE); 
        return pressSpaceToStart; 
    }

    private void blinkingLabel() {
        Timer timer = new Timer(blinkingRate, new ActionListener() {
            private boolean visible = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                visible = !visible;
                pressSpaceToStart.setVisible(visible);
            }
        });
        timer.start();
    }
    
    @Override 
    public void actionPerformed(ActionEvent e) { 
        command = e.getActionCommand(); 
        if (command.equals("scoreboard")) {
            this.dispose();
            new ScoreboardUI();
        } else if (command.equals("exit")) {
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(KeyEvent k) {
        if (k.getKeyCode() == KeyEvent.VK_SPACE) {
            this.dispose();
            new GameUI();;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // not used
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // not used
    }
}

