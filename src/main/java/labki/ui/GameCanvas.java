package labki.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import labki.organizmy.Organizm;
import labki.organizmy.rosliny.*;
import labki.organizmy.zwierzeta.*;

public class GameCanvas extends JPanel implements KeyListener, MouseListener {
    private final Swiat swiat;
    private final int rozmiarPola = 35; // Wielkość kratki w pikselach
    private final Set<Integer> wcisnieteKlawisze = new HashSet<>();

    public GameCanvas(Swiat swiat) {
        this.swiat = swiat;
        setBackground(Color.BLACK);
        setFocusable(true);

        // Dodajemy listenery, żeby gra reagowała na klawiaturę i myszkę
        SwingUtilities.invokeLater(() -> {
            addKeyListener(this);
            addMouseListener(this);
        });
    }

    public Set<Integer> getPressedKeys() {
        return wcisnieteKlawisze;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        rysujSiatke(g2d);
        rysujOrganizmy(g2d);
        rysujInfo(g2d);
    }

    private void rysujSiatke(Graphics2D g) {
        int szerokosc = swiat.getGridM() * rozmiarPola;
        int wysokosc = swiat.getGridN() * rozmiarPola;

        g.setColor(Color.DARK_GRAY);
        g.setStroke(new BasicStroke(1));

        // Rysowanie linii pionowych
        for (int i = 0; i <= swiat.getGridM(); i++) {
            g.drawLine(i * rozmiarPola, 0, i * rozmiarPola, wysokosc);
        }
        // Rysowanie linii poziomych
        for (int i = 0; i <= swiat.getGridN(); i++) {
            g.drawLine(0, i * rozmiarPola, szerokosc, i * rozmiarPola);
        }

        // Tło planszy (lekko szare pola)
        g.setColor(new Color(20, 20, 20));
        for (int y = 0; y < swiat.getGridN(); y++) {
            for (int x = 0; x < swiat.getGridM(); x++) {
                g.fillRect(x * rozmiarPola, y * rozmiarPola, rozmiarPola, rozmiarPola);
            }
        }
    }

    private void rysujOrganizmy(Graphics2D g) {
        // Przechodzimy po każdym polu planszy
        for (int y = 0; y < swiat.getGridN(); y++) {
            for (int x = 0; x < swiat.getGridM(); x++) {

                Organizm org = swiat.ktoTutaj(x, y);

                if (org != null && org.isZyje()) {
                    Rys rys = org.rysowanie();

                    int px = x * rozmiarPola;
                    int py = y * rozmiarPola;

                    // Rysuj kwadrat (ciało organizmu)
                    g.setColor(rys.color != null ? rys.color : Color.PINK);
                    g.fillRect(px + 2, py + 2, rozmiarPola - 4, rozmiarPola - 4);

                    // Rysuj literkę
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial", Font.BOLD, 20));

                    String symbol = String.valueOf(rys.symbol);
                    FontMetrics fm = g.getFontMetrics();
                    int textX = px + (rozmiarPola - fm.stringWidth(symbol)) / 2;
                    int textY = py + ((rozmiarPola - fm.getHeight()) / 2) + fm.getAscent();

                    g.drawString(symbol, textX, textY);

                    // Rysuj wiek (mała czcionka na dole)
                    g.setFont(new Font("Arial", Font.PLAIN, 10));
                    g.drawString("" + org.getWiek(), px + 2, py + rozmiarPola - 2);
                }
            }
        }
    }

    private void rysujInfo(Graphics2D g) {
        int infoY = swiat.getGridN() * rozmiarPola + 20;

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));

        g.drawString("Strzałki: Ruch Człowieka | 'U': Super Umiejętność | Spacja: Nowa Tura", 20, infoY);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int szerokoscPlanszy = swiat.getGridM() * rozmiarPola;
        int wysokoscPlanszy = swiat.getGridN() * rozmiarPola;

        if (e.getX() < szerokoscPlanszy && e.getY() < wysokoscPlanszy) {
            int x = e.getX() / rozmiarPola;
            int y = e.getY() / rozmiarPola;

            Punkt p = new Punkt(x, y);

            if (swiat.ktoTutaj(p) == null) {
                pokazMenuDodawania(p);
            }
        }
    }

    private void pokazMenuDodawania(Punkt punkt) {
        String[] opcje = {
                "Wilk", "Lis", "Owca", "Zolw", "Antylopa",
                "Cyber", "Czlowiek", "Barszcz",
                "Guarana", "Mlecz", "Trawa", "Jagoda"
        };

        String wybrany = (String) JOptionPane.showInputDialog(
                this,
                "Wybierz co dodać:",
                "Dodaj Organizm",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcje,
                opcje[0]
        );

        if (wybrany != null) {
            Organizm nowy = null;

            switch (wybrany) {
                case "Wilk": nowy = new Wilk(swiat, punkt); break;
                case "Lis": nowy = new Lis(swiat, punkt); break;
                case "Owca": nowy = new Owca(swiat, punkt); break;
                case "Zolw": nowy = new Zolw(swiat, punkt); break;
                case "Antylopa": nowy = new Antylopa(swiat, punkt); break;
                case "Cyber": nowy = new Cyber(swiat, punkt); break;
                case "Czlowiek": nowy = new Czlowiek(swiat, punkt); break;
                case "Barszcz": nowy = new Barszcz(swiat, punkt); break;
                case "Guarana": nowy = new Guarana(swiat, punkt); break;
                case "Mlecz": nowy = new Mlecz(swiat, punkt); break;
                case "Trawa": nowy = new Trawa(swiat, punkt); break;
                case "Jagoda": nowy = new Jagoda(swiat, punkt); break;
            }

            if (nowy != null) {
                swiat.dodajOrganizm(nowy);
                repaint();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        wcisnieteKlawisze.add(e.getKeyCode());

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            swiat.wykonajTure();
            repaint();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        wcisnieteKlawisze.remove(e.getKeyCode());
    }

    // Nieużywane metody interfejsów (muszą być puste)
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}