package labki.organizmy.zwierzeta;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyType;

import com.googlecode.lanterna.input.KeyStroke;
import labki.organizmy.Organizm;
import java.io.IOException;

public class Czlowiek extends Zwierze {
    private int cd = 5;

    public Czlowiek(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.sila = 5;
        this.inicjatywa = 4;
        this.wiek = 0;
    }

    @Override
    public void akcja() {
        if (cd > 0) cd--;

        KeyStroke ks = world.getOstatniKlawisz();
        if (ks == null) return;

        int dx = 0, dy = 0;

        if (ks.getKeyType() == KeyType.Character) {
            char c = Character.toLowerCase(ks.getCharacter());

            if (c == 'w') dy = -1;
            else if (c == 's') dy = 1;
            else if (c == 'a') dx = -1;
            else if (c == 'd') dx = 1;
        }

        if (ks.getCharacter() != null && ks.getCharacter() == 'q' && cd == 0) {
            try {
                aktywujUmiejetnosc();
            }catch (IOException e){
                e.printStackTrace();
            }
            return;
        }

        if (dx != 0 || dy != 0) {
            Punkt cel = new Punkt(this.polozenie.x() + dx, this.polozenie.y() + dy);
            if (world.sprawdzCzyWGrid(cel)) {
                this.idz(cel);
            }
        }
    }

    private void wykonajRuch(char klawisz) {
        int dx = 0, dy = 0;
        if (klawisz == 'w') dy = -1;
        else if (klawisz == 's') dy = 1;
        else if (klawisz == 'a') dx = -1;
        else if (klawisz == 'd') dx = 1;

        if (dx != 0 || dy != 0) {
            Punkt cel = new Punkt(this.polozenie.x() + dx, this.polozenie.y() + dy);
            if (world.sprawdzCzyWGrid(cel)) {
                this.idz(cel);
            }
        }
    }

    private void aktywujUmiejetnosc() throws IOException {
        world.dodajLogi("Człowiek aktywował Szybkość Antylopy!");
        this.cd = 5;
        int superRuchy = 5;

        while (superRuchy > 0) {
            world.paintGrid();

            KeyStroke ks = world.getScreen().readInput();
            char klawisz = (ks.getCharacter() != null) ? Character.toLowerCase(ks.getCharacter()) : ' ';

            if (klawisz == 'w' || klawisz == 's' || klawisz == 'a' || klawisz == 'd') {
                wykonajRuch(klawisz);
                superRuchy--;

                if (superRuchy <= 1 && world.getRandom().nextInt(2) == 0) {
                    return;
                }
            }
        }
    }

    @Override
    public void kolizja(Organizm napastnik) {
        if (napastnik instanceof Czlowiek) {
            this.rozmnoz(this.polozenie);
        } else {
            super.kolizja(napastnik);
        }
    }

    @Override
    protected void urodzDziecko(Punkt p) {
        world.dodajOrganizm(new Czlowiek(world, p));
    }

    @Override
    public Rys rysowanie() {
        return new Rys('K', TextColor.ANSI.BLUE_BRIGHT);
    }
}