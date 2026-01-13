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

        Set<Integer> pressedKeys = world.getPressedKeys();
        
        int dx = 0, dy = 0;

        if (pressedKeys.contains(KeyEvent.VK_W)) dy = -1;
        else if (pressedKeys.contains(KeyEvent.VK_S)) dy = 1;
        
        if (pressedKeys.contains(KeyEvent.VK_A)) dx = -1;
        else if (pressedKeys.contains(KeyEvent.VK_D)) dx = 1;

        if (pressedKeys.contains(KeyEvent.VK_Q) && cd == 0) {
            aktywujUmiejetnosc();
            return;
        }

        if (dx != 0 || dy != 0) {
            Punkt cel = new Punkt(this.polozenie.x() + dx, this.polozenie.y() + dy);
            if (world.sprawdzCzyWGrid(cel)) {
                this.idz(cel);
                world.dodajLogi("H ruch");
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
        world.dodajLogi("H umiejetnosc");
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
        return new Rys('K', Color.BLUE);
    }
}