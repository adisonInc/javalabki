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

            Swiat world = Swiat.getSwiat();
            world.setGrid(HEIGHT, WIDTH);
            
            for (int i = 0; i < 2; i++) {
                world.dodajOrganizm(new Wilk(world, world.losujPustyPunky()));
                world.dodajOrganizm(new Owca(world, world.losujPustyPunky()));
                world.dodajOrganizm(new Zolw(world, world.losujPustyPunky()));
                world.dodajOrganizm(new Antylopa(world, world.losujPustyPunky()));
                world.dodajOrganizm(new Cyber(world, world.losujPustyPunky()));
                world.dodajOrganizm(new Lis(world, world.losujPustyPunky()));


            }
            for (int i = 0; i <5 ; i++) {
                world.dodajOrganizm(new Barszcz(world, world.losujPustyPunky()));
                world.dodajOrganizm(new Guarana(world, world.losujPustyPunky()));
                world.dodajOrganizm(new Jagoda(world, world.losujPustyPunky()));
                world.dodajOrganizm(new Mlecz(world, world.losujPustyPunky()));
                world.dodajOrganizm(new Trawa(world, world.losujPustyPunky()));
            }


            world.dodajOrganizm(new Czlowiek(world, world.losujPunkt()));

            GamePanel gamePanel = new GamePanel(world);
            frame.setContentPane(gamePanel);
            frame.setVisible(true);
        });
    }
}
