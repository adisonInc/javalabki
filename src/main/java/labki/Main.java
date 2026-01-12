package labki;

import labki.organizmy.zwierzeta.Wilk;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import com.googlecode.lanterna.TerminalSize;


public class Main {
    public static void main(String[] args) throws Exception {
        // Terminal + screen
        Screen screen = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(40, 20))
                .createScreen();
        screen.startScreen();
        screen.setCursorPosition(null);

        // Tworzymy świat
        Swiat world = Swiat.getSwiat();
        world.setScreen(screen);
        world.setGrid(10, 10);
        for (int i = 0; i < 3; i++) {
            world.dodajOrganizm(new Wilk(world, world.losujPustyPunky()));

        }


        boolean running = true;
        while (running) {
            world.paintGrid();
            KeyStroke key = screen.pollInput();
            if (key != null) {
                if (key.getKeyType() == KeyType.Character &&
                        (key.getCharacter() == 'q' || key.getCharacter() == 'Q')) {
                    running = false;
                }
                if (key.getKeyType() == KeyType.ArrowRight) {
                    world.wykonajTure();
                }
            }

            Thread.sleep(100); // tick
        }

        screen.stopScreen();
    }
}
