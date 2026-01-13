package labki.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import labki.Swiat;

public class LogsPanel extends JPanel {
    private final Swiat world;
    private final JTextArea logsTextArea;
    private final JScrollPane scrollPane;
    private int lastLogCount = 0;

    public LogsPanel(Swiat world) {
        this.world = world;
        setLayout(new BorderLayout());
        setBackground(new Color(40, 40, 40));

        // Stworzenie text area dla logów
        logsTextArea = new JTextArea();
        logsTextArea.setEditable(false);
        logsTextArea.setBackground(new Color(20, 20, 20));
        logsTextArea.setForeground(Color.WHITE);
        logsTextArea.setFont(new java.awt.Font("Courier New", java.awt.Font.PLAIN, 11));
        logsTextArea.setLineWrap(true);
        logsTextArea.setWrapStyleWord(true);

        // Scroll pane
        scrollPane = new JScrollPane(logsTextArea);
        scrollPane.setBackground(new Color(40, 40, 40));
        add(scrollPane, BorderLayout.CENTER);

        // Timer do odświeżania logów
        Timer updateTimer = new Timer(100, e -> updateLogs());
        updateTimer.start();

        // Ustaw preferowaną szerokość panelu
        setPreferredSize(new java.awt.Dimension(250, 600));
    }

    private void updateLogs() {
        List<String> logs = world.getLogi();
        
        if (logs.size() != lastLogCount) {
            lastLogCount = logs.size();
            StringBuilder sb = new StringBuilder();
            
            // Pokazuj ostatnie 50 logów
            int startIndex = Math.max(0, logs.size() - 50);
            for (int i = startIndex; i < logs.size(); i++) {
                sb.append(logs.get(i)).append("\n");
            }
            
            logsTextArea.setText(sb.toString());
            // Scroll do dołu
            logsTextArea.setCaretPosition(logsTextArea.getDocument().getLength());
        }
    }
}
