package labki.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import labki.Swiat;

public class LogsPanel extends JPanel {
    private final Swiat swiat;
    private final JTextArea obszarTekstu;

    public LogsPanel(Swiat swiat) {
        this.swiat = swiat;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 0));

        obszarTekstu = new JTextArea();
        obszarTekstu.setEditable(false);
        obszarTekstu.setBackground(new Color(50, 50, 50));
        obszarTekstu.setForeground(Color.WHITE);
        obszarTekstu.setLineWrap(true);
        obszarTekstu.setWrapStyleWord(true);

        add(new JScrollPane(obszarTekstu), BorderLayout.CENTER);

        
        new Timer(200, e -> odswiezLogi()).start();
    }

    private void odswiezLogi() {
        List<String> logi = swiat.getLogi();
        StringBuilder sb = new StringBuilder();


        int start = Math.max(0, logi.size() - 30);
        for (int i = start; i < logi.size(); i++) {
            sb.append(logi.get(i)).append("\n");
        }
        obszarTekstu.setText(sb.toString());
    }
}