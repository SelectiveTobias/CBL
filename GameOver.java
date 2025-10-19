import javax.swing.JPanel;

/**
 * keeps track of when the player is game over and let's the player know by stopping the game.
 */
public class GameOver {
    private GameUI gameUI;

    public void setGameUI(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    /**
     * displays the Game Over in the GameUI.
     */
    public void gameEnder() {
        JPanel gameOverPanel = gameUI.createGameOverPanel();
        gameUI.gamePanel.revalidate();
        gameUI.gamePanel.add(gameOverPanel);
        gameUI.gamePanel.repaint();
    }
}
