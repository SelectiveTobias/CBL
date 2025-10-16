import java.awt.*; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import javax.swing.*; 

/**
 * displays the homescreen.
 */
public class ScoreboardUI extends JFrame implements ActionListener {
    String command;
    int blinkingRate = 500;
    int screenheight = Toolkit.getDefaultToolkit().getScreenSize().height;
    int screenwidth = Toolkit.getDefaultToolkit().getScreenSize().width;

    JPanel panelGeneral;
    JLabel pressSpaceToStart;
    JButton buttonBack;
    JButton buttonExit;

    public ScoreboardUI() {
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

        buttonBack = createButtonBack();
        panelGeneral.add(buttonBack);

        buttonExit = createButtonExit();
        panelGeneral.add(buttonExit);

        JList<String> scoreboardList = scoreboardList();
        panelGeneral.add(scoreboardList);

        this.setVisible(true);
    }

    private JPanel panelGeneral() {
        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(null); 
        panelGeneral.setBackground(Color.BLACK);
        panelGeneral.setFocusable(true);
        panelGeneral.requestFocusInWindow();
        return panelGeneral;
    }

    private JButton createButtonBack() { 
        JButton buttonBack = new JButton("back"); 
        buttonBack.setFont(new Font("Times New Roman", Font.PLAIN, 35)); 
        buttonBack.setBorderPainted(true); 
        buttonBack.setBounds(395, 500, 150, 150); 
        buttonBack.setBackground(Color.GRAY); 
        buttonBack.setFocusPainted(false); 
        buttonBack.setActionCommand("back"); 
        buttonBack.addActionListener(this); 
        return buttonBack; 
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
    
    @Override 
    public void actionPerformed(ActionEvent e) { 
        command = e.getActionCommand(); 
        if (command.equals("back")) {
            this.dispose();
            new Homescreen();
        } else if (command.equals("exit")) {
            System.exit(0);
        }
    }

    private JList<String> scoreboardList() {
        JList<String> scoreboardList = new JList<>();
        scoreboardList.setBounds(300, 100, 500, 300);
        scoreboardList.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        scoreboardList.setBackground(Color.LIGHT_GRAY);
        scoreboardList.setForeground(Color.BLACK);
        scoreboardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        return scoreboardList;
    }

}

