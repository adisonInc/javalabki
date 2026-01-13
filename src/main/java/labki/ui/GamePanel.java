package labki.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import labki.Swiat;

public class GamePanel extends JPanel {
    private final GameCanvas gameCanvas;
    private final ControlPanel controlPanel;
    private final LogsPanel logsPanel;

    public GamePanel(Swiat world) {
        setLayout(new BorderLayout());

        // Panel z grą
        gameCanvas = new GameCanvas(world);
        world.setGameCanvas(gameCanvas);
        add(gameCanvas, BorderLayout.CENTER);

        // Panel sterowania
        controlPanel = new ControlPanel(world, gameCanvas);
        add(controlPanel, BorderLayout.SOUTH);
        
        // Panel z logami
        logsPanel = new LogsPanel(world);
        add(logsPanel, BorderLayout.EAST);
    }
}
