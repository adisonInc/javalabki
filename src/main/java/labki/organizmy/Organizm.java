package labki.organizmy;

import java.util.Random;
import labki.Swiat;
import labki.Punkt;
import labki.Rys;

public abstract class Organizm {
    protected Swiat world;
    protected Punkt polozenie;
    protected boolean zyje;
    protected int wiek;
    protected int sila;
    protected int inicjatywa;

    public Organizm(Swiat swiat, Punkt p) {
        this.world = swiat;
        this.polozenie = new Punkt(p.x(), p.y());
        this.zyje = true;
        this.wiek = 0;
    }



    public int getSila() { return sila; }
    public void setSila(int s) { sila = s; }

    public void setSwiat(Swiat s) { world = s; }

    public int getInicjatywa() { return inicjatywa; }
    public void setInicjatywa(int i) { inicjatywa = i; }

    public Punkt getPolozenie() { return polozenie; }
    public void setPolozenie(Punkt p) {
        this.polozenie = p;
    }

    public boolean isZyje() { return zyje; }
    public void setZyje(boolean z) { zyje = z; }

    public int getWiek() { return wiek; }
    public void incWiek() { wiek++; }

    protected abstract void urodzDziecko(Punkt p);
    public abstract Rys rysowanie();
    public abstract void kolizja(Organizm o);
    public abstract void akcja();

    public void rozmnoz(Punkt p) {
        int proby = 0;
        Random rand = world.getRandom();

        while (proby < 10) {
            proby++;
            int rx = rand.nextInt(3) - 1 + p.x(); // -1,0,1
            int ry = rand.nextInt(3) - 1 + p.y();
            if (world.sprawdzCzyWGrid(rx, ry) && world.ktoTutaj(rx, ry) == null) {
                Punkt cel = new Punkt(rx, ry);
                urodzDziecko(cel);
                return;
            }
        }
    }
}
