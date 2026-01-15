package labki.ui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import labki.Swiat;

public class GamePanel extends JPanel {

    public GamePanel(Swiat swiat) {
        setLayout(new BorderLayout());

        GameCanvas planszaUI = new GameCanvas(swiat);

        swiat.setGameCanvas(planszaUI);

        add(planszaUI, BorderLayout.CENTER);

        LogsPanel panelLogow = new LogsPanel(swiat);
        add(panelLogow, BorderLayout.EAST);
    }
}