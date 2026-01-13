package labki.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import labki.organizmy.Organizm;
import labki.organizmy.rosliny.Barszcz;
import labki.organizmy.zwierzeta.Antylopa;
import labki.organizmy.zwierzeta.Cyber;
import labki.organizmy.zwierzeta.Czlowiek;
import labki.organizmy.zwierzeta.Lis;
import labki.organizmy.zwierzeta.Owca;
import labki.organizmy.zwierzeta.Wilk;
import labki.organizmy.zwierzeta.Zolw;

public class GameCanvas extends JPanel implements KeyListener, MouseListener {
    private final Swiat world;
    private final int cellSize = 40;
    private final Set<Integer> pressedKeys = new HashSet<>();

    public GameCanvas(Swiat world) {
        this.world = world;
        setBackground(Color.BLACK);
        setFocusable(true);
        SwingUtilities.invokeLater(() -> {
            addKeyListener(GameCanvas.this);
            addMouseListener(GameCanvas.this);
        });
    }

    public Set<Integer> getPressedKeys() {
        return pressedKeys;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawGrid(g2d);
        drawOrganisms(g2d);
        drawGameInfo(g2d);
    }

    private void drawGrid(Graphics2D g) {
        int gridWidth = world.getGridM() * cellSize;
        int gridHeight = world.getGridN() * cellSize;

        g.setColor(Color.DARK_GRAY);
        g.setStroke(new BasicStroke(1));

        // Rysowanie linii siatki
        for (int i = 0; i <= world.getGridM(); i++) {
            g.drawLine(i * cellSize, 0, i * cellSize, gridHeight);
        }
        for (int i = 0; i <= world.getGridN(); i++) {
            g.drawLine(0, i * cellSize, gridWidth, i * cellSize);
        }

        // Rysowanie pól
        g.setColor(new Color(20, 20, 20));
        for (int y = 0; y < world.getGridN(); y++) {
            for (int x = 0; x < world.getGridM(); x++) {
                int px = x * cellSize;
                int py = y * cellSize;
                g.fillRect(px, py, cellSize, cellSize);
            }
        }
    }

    private void drawOrganisms(Graphics2D g) {
        List<Organizm> organisms = world.getOrganizmy();
        
        for (Organizm org : organisms) {
            if (org.isZyje()) {
                Rys rys = org.rysowanie();
                int x = org.getPolozenie().x() * cellSize;
                int y = org.getPolozenie().y() * cellSize;

                // Rysowanie koloru
                g.setColor(rys.color);
                g.fillRect(x + 2, y + 2, cellSize - 4, cellSize - 4);

                // Rysowanie symbolu
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, cellSize - 10));
                FontMetrics fm = g.getFontMetrics();
                String symbol = String.valueOf(rys.symbol);
                int textX = x + (cellSize - fm.stringWidth(symbol)) / 2;
                int textY = y + ((cellSize - fm.getHeight()) / 2) + fm.getAscent();
                g.drawString(symbol, textX, textY);

                // Wyświetlenie informacji o organizmu
                g.setColor(Color.LIGHT_GRAY);
                g.setFont(new Font("Arial", Font.PLAIN, 8));
                g.drawString("W:" + org.getWiek(), x + 3, y + cellSize - 5);
            }
        }
    }

    private void drawGameInfo(Graphics2D g) {
        int gridHeight = world.getGridN() * cellSize;
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        
        int infoY = gridHeight + 20;
        g.drawString("Tura: " + world.getNumerTury(), 20, infoY);
        g.drawString("Organizmów: " + world.getOrganizmy().size(), 200, infoY);
        g.drawString("Kliknij na pole aby dodać organizm", 500, infoY);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int gridWidth = world.getGridM() * cellSize;
        int gridHeight = world.getGridN() * cellSize;
        
        // Sprawdzenie czy klik był w obrębie siatki
        if (e.getX() < gridWidth && e.getY() < gridHeight) {
            int gridX = e.getX() / cellSize;
            int gridY = e.getY() / cellSize;
            
            Punkt punkt = new Punkt(gridX, gridY);
            
            // Sprawdzenie czy pole jest puste
            if (world.ktoTutaj(punkt) == null) {
                showOrganismSelector(punkt);
            }
        }
    }

    private void showOrganismSelector(Punkt punkt) {
        String[] options = {
            "Wilk",
            "Lis",
            "Owca",
            "Zołw",
            "Antylopa",
            "Cyber",
            "Człowiek",
            "Barszcz"
        };
        
        int choice = javax.swing.JOptionPane.showOptionDialog(
            this,
            "Wybierz organizm do dodania:",
            "Dodaj organizm",
            javax.swing.JOptionPane.DEFAULT_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (choice >= 0) {
            Organizm nowyOrganizm = null;
            
            switch (choice) {
                case 0: // Wilk
                    nowyOrganizm = new Wilk(world, punkt);
                    break;
                case 1: // Lis
                    nowyOrganizm = new Lis(world, punkt);
                    break;
                case 2: // Owca
                    nowyOrganizm = new Owca(world, punkt);
                    break;
                case 3: // Zołw
                    nowyOrganizm = new Zolw(world, punkt);
                    break;
                case 4: // Antylopa
                    nowyOrganizm = new Antylopa(world, punkt);
                    break;
                case 5: // Cyber
                    nowyOrganizm = new Cyber(world, punkt);
                    break;
                case 6: // Człowiek
                    nowyOrganizm = new Czlowiek(world, punkt);
                    break;
                case 7: // Barszcz
                    nowyOrganizm = new Barszcz(world, punkt);
                    break;
            }
            
            if (nowyOrganizm != null) {
                world.dodajOrganizm(nowyOrganizm);
                repaint();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
        
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            world.wykonajTure();
            repaint();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
