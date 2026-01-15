package labki.organizmy.zwierzeta;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Set;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import labki.organizmy.Organizm;

public class Czlowiek extends Zwierze {
    private int cd = 5;
    private boolean umiejetnoscAktywna = false;
    private int pozostaloRuchow = 0;

    public Czlowiek(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.sila = 5;
        this.inicjatywa = 4;
        this.wiek = 0;
    }

    @Override
    public void akcja() {
        if (cd > 0) cd--;


        int dx = 0, dy = 0;
        Character mv = null;
        if (world.getGameCanvas() != null) {
            mv = world.getGameCanvas().pollNextMove();
        }


        if (mv == null) {
            Set<Integer> pressedKeys = world.getPressedKeys();
            if (pressedKeys.contains(KeyEvent.VK_Q) && cd == 0) {
                aktywujUmiejetnosc();
                return;
            }

            if (pressedKeys.contains(KeyEvent.VK_W)) dy = -1;
            else if (pressedKeys.contains(KeyEvent.VK_S)) dy = 1;

            if (pressedKeys.contains(KeyEvent.VK_A)) dx = -1;
            else if (pressedKeys.contains(KeyEvent.VK_D)) dx = 1;
        } else {
            if (mv == 'W') dy = -1;
            else if (mv == 'S') dy = 1;
            else if (mv == 'A') dx = -1;
            else if (mv == 'D') dx = 1;
        }

        if (dx != 0 || dy != 0) {
            Punkt cel = new Punkt(this.polozenie.x() + dx, this.polozenie.y() + dy);
            if (world.sprawdzCzyWGrid(cel)) {
                this.idz(cel);
                world.dodajLogi("Cz ruch");
            }
        }


        if (umiejetnoscAktywna && pozostaloRuchow > 0 && world.getGameCanvas() != null) {
            while (pozostaloRuchow > 0) {
                Character mv2 = world.getGameCanvas().pollNextMove();
                if (mv2 == null) break;

                int ddx = 0, ddy = 0;
                if (mv2 == 'W') ddy = -1;
                else if (mv2 == 'S') ddy = 1;
                else if (mv2 == 'A') ddx = -1;
                else if (mv2 == 'D') ddx = 1;

                if (ddx != 0 || ddy != 0) {
                    Punkt cel2 = new Punkt(this.polozenie.x() + ddx, this.polozenie.y() + ddy);
                    if (world.sprawdzCzyWGrid(cel2)) {
                        this.idz(cel2);
                        world.dodajLogi("Cz dodatkowy");
                        pozostaloRuchow--;
                    }
                }
            }

            if (pozostaloRuchow == 0) {
                umiejetnoscAktywna = false;
            }
        }
    }

    private void wykonajRuch(int dx, int dy) {
        if (dx != 0 || dy != 0) {
            Punkt cel = new Punkt(this.polozenie.x() + dx, this.polozenie.y() + dy);
            if (world.sprawdzCzyWGrid(cel)) {
                this.idz(cel);
            }
        }
    }

    public void aktywujUmiejetnosc() {
        world.dodajLogi("Czlowiek umiejetnosc");
        this.cd = 5;
        this.umiejetnoscAktywna = true;
        this.pozostaloRuchow = 5;
    }

    public boolean czyUmiejetnoscAktywna() {
        return umiejetnoscAktywna;
    }

    public void wykonajDodatkowyRuch(int dx, int dy) {
        if (umiejetnoscAktywna && pozostaloRuchow > 0) {
            wykonajRuch(dx, dy);
            pozostaloRuchow--;
            
            if (pozostaloRuchow == 0) {
                umiejetnoscAktywna = false;
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
        return new Rys('K', new Color(255, 106, 106));
    }
}