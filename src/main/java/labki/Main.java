package labki;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import labki.organizmy.rosliny.*;
import labki.organizmy.zwierzeta.Antylopa;
import labki.organizmy.zwierzeta.Cyber;
import labki.organizmy.zwierzeta.Czlowiek;
import labki.organizmy.zwierzeta.Lis;
import labki.organizmy.zwierzeta.Owca;
import labki.organizmy.zwierzeta.Wilk;
import labki.organizmy.zwierzeta.Zolw;
import labki.ui.GamePanel;

public class Main {
    public static void main(String[] args) {

        final int WIDTH = 30;
        final int HEIGHT = 30;

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Świat - Symulator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(WIDTH * 50 + 200, HEIGHT * 50 + 100);
            frame.setLocationRelativeTo(null);
            frame.setResizable(true);

            Swiat swiat = Swiat.getSwiat();
            swiat.setGrid(HEIGHT, WIDTH);
            
            for (int i = 0; i < 2; i++) {
                swiat.dodajOrganizm(new Wilk(swiat, swiat.losujPustyPunkt()));
                swiat.dodajOrganizm(new Owca(swiat, swiat.losujPustyPunkt()));
                swiat.dodajOrganizm(new Zolw(swiat, swiat.losujPustyPunkt()));
                swiat.dodajOrganizm(new Antylopa(swiat, swiat.losujPustyPunkt()));
                swiat.dodajOrganizm(new Cyber(swiat, swiat.losujPustyPunkt()));
                swiat.dodajOrganizm(new Lis(swiat, swiat.losujPustyPunkt()));


            }
            for (int i = 0; i <5 ; i++) {
                swiat.dodajOrganizm(new Barszcz(swiat, swiat.losujPustyPunkt()));
                swiat.dodajOrganizm(new Guarana(swiat, swiat.losujPustyPunkt()));
                swiat.dodajOrganizm(new Jagoda(swiat, swiat.losujPustyPunkt()));
                swiat.dodajOrganizm(new Mlecz(swiat, swiat.losujPustyPunkt()));
                swiat.dodajOrganizm(new Trawa(swiat, swiat.losujPustyPunkt()));
            }


            swiat.dodajOrganizm(new Czlowiek(swiat, swiat.losujPunkt()));

            GamePanel gamePanel = new GamePanel(swiat);
            frame.setContentPane(gamePanel);
            frame.setVisible(true);
        });
    }
}
