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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import labki.organizmy.Organizm;
import labki.organizmy.rosliny.*;
import labki.organizmy.zwierzeta.*;

public class GameCanvas extends JPanel implements KeyListener, MouseListener {
    private final Swiat world;
    private final int cellSize = 35;
    private final Set<Integer> pressedKeys = new HashSet<>();
    private final List<Character> moveQueue = new ArrayList<>();

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

    public Character pollNextMove() {
        synchronized (moveQueue) {
            if (moveQueue.isEmpty()) return null;
            return moveQueue.remove(0);
        }
    }

    private void enqueueMove(char c) {
        synchronized (moveQueue) {
            moveQueue.add(c);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawGrid(g2d);
        drawOrganisms(g2d);
        drawGameInfo(g2d);
    }

    private void drawGrid(Graphics2D g) {
        int gridWidth = world.getGridM() * cellSize;
        int gridHeight = world.getGridN() * cellSize;

        g.setColor(Color.DARK_GRAY);
        g.setStroke(new BasicStroke(1));

        for (int i = 0; i <= world.getGridM(); i++) {
            g.drawLine(i * cellSize, 0, i * cellSize, gridHeight);
        }
        for (int i = 0; i <= world.getGridN(); i++) {
            g.drawLine(0, i * cellSize, gridWidth, i * cellSize);
        }


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
        java.util.Map<Punkt, java.util.List<Organizm>> claims = new java.util.HashMap<>();
        for (Organizm o : world.getOrganizmy()) {
            claims.computeIfAbsent(o.getPolozenie(), k -> new java.util.ArrayList<>()).add(o);
        }
        for (java.util.Map.Entry<Punkt, java.util.List<Organizm>> e : claims.entrySet()) {
            Punkt p = e.getKey();
            java.util.List<Organizm> list = e.getValue();
            Organizm gridOcc = world.sprawdzCzyWGrid(p) ? world.ktoTutaj(p) : null;
            if (list.size() > 1) {
                System.err.println("Multiple list claims at " + p + " count=" + list.size() + " grid contains: " + gridOcc);
            } else {
                Organizm o = list.get(0);
                if (!world.sprawdzCzyWGrid(p) || gridOcc != o) {
                    System.err.println("Render mismatch for " + o.getClass().getSimpleName() + " at " + p + " grid contains: " + gridOcc);
                }
            }
        }

        for (int y = 0; y < world.getGridN(); y++) {
            for (int x = 0; x < world.getGridM(); x++) {
                Organizm org = world.ktoTutaj(x, y);
                if (org != null && org.isZyje()) {
                    Rys rys = org.rysowanie();
                    int px = x * cellSize;
                    int py = y * cellSize;


                    java.awt.Color textColor = Color.BLACK;
                    java.awt.Color fill = (rys.color != null) ? rys.color : java.awt.Color.PINK;

                    g.setColor(fill);
                    g.fillRect(px + 2, py + 2, Math.max(1, cellSize - 4), Math.max(1, cellSize - 4));


                    g.setColor(textColor);
                    int fontSize = Math.max(6, cellSize - 10);
                    g.setFont(new Font("Arial", Font.BOLD, fontSize));
                    FontMetrics fm = g.getFontMetrics();
                    String symbol = String.valueOf(rys.symbol);
                    int textX = px + (cellSize - fm.stringWidth(symbol)) / 2;
                    int textY = py + ((cellSize - fm.getHeight()) / 2) + fm.getAscent();
                    g.drawString(symbol, textX, textY);

                    g.setColor(java.awt.Color.BLACK);

                    g.setFont(new Font("Arial", Font.PLAIN, 8));
                    g.drawString("" + org.getWiek(), px + 3, py + cellSize - 5);
                }
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


        if (e.getX() < gridWidth && e.getY() < gridHeight) {
            int gridX = e.getX() / cellSize;
            int gridY = e.getY() / cellSize;
        if (e.getX() < gridWidth && e.getY() < gridHeight) {
            int gridX = e.getX() / cellSize;
            int gridY = e.getY() / cellSize;

            Punkt punkt = new Punkt(gridX, gridY);            }
        }
    }

    private void showOrganismSelector(Punkt punkt) {
        String[] options = {
                "Wilk", "Lis", "Owca", "Zołw", "Antylopa",
                "Cyber", "Człowiek", "Barszcz Sosnowskiego",
                "Guarana", "Mlecz", "Trawa", "Wilcza Jagoda"
        };


        String selected = (String) javax.swing.JOptionPane.showInputDialog(
                this,
                "Wybierz organizm do dodania:",
                "Dodaj organizm",
                javax.swing.JOptionPane.PLAIN_MESSAGE,

                options,
                options[0]
        );

        
        if (selected != null) {
            Organizm nowyOrganizm = null;


            switch (selected) {
                case "Wilk":
                    nowyOrganizm = new Wilk(world, punkt);
                    break;
                case "Lis":
                    nowyOrganizm = new Lis(world, punkt);
                    break;
                case "Owca":
                    nowyOrganizm = new Owca(world, punkt);
                    break;
                case "Zołw":
                    nowyOrganizm = new Zolw(world, punkt);
                    break;
                case "Antylopa":
                    nowyOrganizm = new Antylopa(world, punkt);
                    break;
                case "Cyber":
                    nowyOrganizm = new Cyber(world, punkt);
                    break;
                case "Człowiek":
                    nowyOrganizm = new Czlowiek(world, punkt);
                    break;
                case "Barszcz Sosnowskiego": 
                    nowyOrganizm = new Barszcz(world, punkt);
                    break;
                case "Guarana":
                    nowyOrganizm = new Guarana(world, punkt);
                    break;
                case "Mlecz":
                    nowyOrganizm = new Mlecz(world, punkt);
                    break;
                case "Trawa":
                    nowyOrganizm = new Trawa(world, punkt);
                    break;
                case "Wilcza Jagoda":
                    nowyOrganizm = new Jagoda(world, punkt);
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


        if (e.getKeyCode() == KeyEvent.VK_W) enqueueMove('W');
        else if (e.getKeyCode() == KeyEvent.VK_S) enqueueMove('S');
        else if (e.getKeyCode() == KeyEvent.VK_A) enqueueMove('A');
        else if (e.getKeyCode() == KeyEvent.VK_D) enqueueMove('D');

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
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
