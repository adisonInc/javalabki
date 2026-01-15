package labki.organizmy.zwierzeta;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Set;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import labki.organizmy.Organizm;

public class Czlowiek extends Zwierze {
    private int cd = 0;
    private boolean umiejetnoscAktywna = false;
    private int pozostaloRuchow = 0;
    private int turUmiejetnosci = 0;

    public Czlowiek(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.sila = 5;
        this.inicjatywa = 4;
        this.wiek = 0;
    }

    @Override
    public void akcja() {
        if (cd > 0) cd--;

        Set<Integer> keys = swiat.getPressedKeys();

        if (keys.contains(KeyEvent.VK_U) && cd == 0 && !umiejetnoscAktywna) {
            aktywujUmiejetnosc();
        }

        if (!czyWcisnietoStrzalke(keys)) {
            return;
        }

        int dystans = 1;
        boolean ruchZablokowany = false;

        if (umiejetnoscAktywna) {
            if (turUmiejetnosci < 3) {
                dystans = 2;
            } else {

                if (swiat.getRandom().nextDouble() < 0.5) {
                    dystans = 0;
                    ruchZablokowany = true;
                }
            }
        }


        if (ruchZablokowany) {

            swiat.dodajLogi("Szybkosc Antylopy: pech! Czlowiek stoi w miejscu.");
        } else {
            Punkt cel = pobierzCelZKeys(dystans);

            if (cel != null && !cel.equals(this.polozenie)) { // Dodatkowe zabezpieczenie
                if (swiat.sprawdzCzyWGrid(cel)) {
                    this.idz(cel);
                }
            }
        }

        if (umiejetnoscAktywna) {
            turUmiejetnosci++;
            pozostaloRuchow--;
            if (pozostaloRuchow <= 0) {
                umiejetnoscAktywna = false;
                turUmiejetnosci = 0;
                swiat.dodajLogi("Szybkosc Antylopy wygasla.");
            }
        }
    }

    private boolean czyWcisnietoStrzalke(Set<Integer> keys) {
        return keys.contains(KeyEvent.VK_UP) ||
                keys.contains(KeyEvent.VK_DOWN) ||
                keys.contains(KeyEvent.VK_LEFT) ||
                keys.contains(KeyEvent.VK_RIGHT);
    }

    private Punkt pobierzCelZKeys(int dystans) {
        Set<Integer> keys = swiat.getPressedKeys();
        int dx = 0, dy = 0;

        if (keys.contains(KeyEvent.VK_UP)) dy = -dystans;
        else if (keys.contains(KeyEvent.VK_DOWN)) dy = dystans;
        else if (keys.contains(KeyEvent.VK_LEFT)) dx = -dystans;
        else if (keys.contains(KeyEvent.VK_RIGHT)) dx = dystans;
        else return null;

        return new Punkt(this.polozenie.x() + dx, this.polozenie.y() + dy);
    }

    public void aktywujUmiejetnosc() {
        swiat.dodajLogi("Czlowiek aktywuje Szybkosc Antylopy!");
        this.cd = 10;
        this.umiejetnoscAktywna = true;
        this.pozostaloRuchow = 5;
        this.turUmiejetnosci = 0;
    }

    @Override
    public void kolizja(Organizm napastnik) {
        super.kolizja(napastnik);
    }

    @Override
    protected void urodzDziecko(Punkt p) {
        swiat.dodajOrganizm(new Czlowiek(swiat, p));
    }

    @Override
    public Rys rysowanie() {
        return new Rys('K', new Color(255, 106, 106));
    }
}