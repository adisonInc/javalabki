package labki.organizmy;

import java.util.Random;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;

public abstract class Organizm {
    protected Swiat swiat;
    protected Punkt polozenie;
    protected boolean zyje;
    protected int wiek;
    protected int sila;
    protected int inicjatywa;

    public Organizm(Swiat swiat, Punkt p) {
        this.swiat = swiat;
        this.polozenie = p;
        this.zyje = true;
        this.wiek = 0;
    }

    public abstract void akcja();
    public abstract void kolizja(Organizm inny);
    public abstract Rys rysowanie();
    protected abstract void urodzDziecko(Punkt p);

    public void rozmnoz(Punkt p) {
        Random rand = swiat.getRandom();
        for (int i = 0; i < 10; i++) {
            int dx = rand.nextInt(3) - 1;
            int dy = rand.nextInt(3) - 1;
            Punkt dzieckoPoz = new Punkt(p.x() + dx, p.y() + dy);

            if (swiat.sprawdzCzyWGrid(dzieckoPoz) && swiat.ktoTutaj(dzieckoPoz) == null) {
                urodzDziecko(dzieckoPoz);
                swiat.dodajLogi("Nowy organizm: " + this.getClass().getSimpleName());
                return;
            }
        }
    }

    public int getSila() { return sila; }
    public void setSila(int s) { sila = s; }
    public int getInicjatywa() { return inicjatywa; }
    public Punkt getPolozenie() { return polozenie; }
    public void setPolozenie(Punkt p) { this.polozenie = p; }
    public boolean isZyje() { return zyje; }
    public void setZyje(boolean z) { zyje = z; }
    public int getWiek() { return wiek; }
    public void incWiek() { wiek++; }
}