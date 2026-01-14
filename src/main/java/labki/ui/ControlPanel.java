package labki.ui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import labki.Swiat;

public class ControlPanel extends JPanel {
    private final Swiat world;
    private final GameCanvas gameCanvas;
    private final JLabel infoLabel;

    public ControlPanel(Swiat world, GameCanvas gameCanvas) {
        this.world = world;
        this.gameCanvas = gameCanvas;

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setBackground(new Color(40, 40, 40));
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.DARK_GRAY));

        JButton nextTurnButton = new JButton("Następna Tura (SPACJA)");
        nextTurnButton.addActionListener(e -> executeNextTurn());
        add(nextTurnButton);

        infoLabel = new JLabel("Tura: 0 | Organizmów: 0");
        infoLabel.setForeground(Color.WHITE);
        add(infoLabel);

        Timer updateTimer = new Timer(100, e -> updateInfo());
        updateTimer.start();
    }

    private void executeNextTurn() {
        world.wykonajTure();
        gameCanvas.repaint();
    }

    private void updateInfo() {
        infoLabel.setText("Tura: " + world.getNumerTury() + " | Organizmów: " + world.getOrganizmy().size());
    }
}
