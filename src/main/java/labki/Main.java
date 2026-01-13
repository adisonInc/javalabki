package labki;

import labki.organizmy.rosliny.Barszcz;
import labki.organizmy.zwierzeta.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import com.googlecode.lanterna.TerminalSize;


public class Main {
    public static void main(String[] args) throws Exception {
        Screen screen = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(140, 120))
                .createScreen();
        screen.startScreen();
        screen.setCursorPosition(null);

        Swiat world = Swiat.getSwiat();
        world.setScreen(screen);
        world.setGrid(10, 10);
        for (int i = 0; i < 2; i++) {
            world.dodajOrganizm(new Wilk(world, world.losujPustyPunky()));
            world.dodajOrganizm(new Barszcz(world, world.losujPustyPunky()));

//            world.dodajOrganizm(new Owca(world, world.losujPustyPunky()));
//            world.dodajOrganizm(new Zolw(world, world.losujPustyPunky()));
//            world.dodajOrganizm(new Antylopa(world, world.losujPustyPunky()));
//            world.dodajOrganizm(new Cyber(world, world.losujPustyPunky()));
//            world.dodajOrganizm(new Lis(world, world.losujPustyPunky()));
//
      }

        world.dodajOrganizm(new Czlowiek(world, world.losujPunkt()));

        boolean running = true;
       world.paintGrid();

        while (running) {
            KeyStroke key = screen.pollInput();
            if (key != null) {
                world.setOstatniKlawisz(key);
                if (key.getKeyType() == KeyType.Escape) running = false;

                world.setOstatniKlawisz(key);

                world.wykonajTure();

                world.paintGrid();
            }
            Thread.sleep(10);
        }
        screen.stopScreen();
    }
}
