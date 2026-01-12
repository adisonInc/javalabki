package labki.organizmy.zwierzeta;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import labki.organizmy.Organizm;

import java.util.Random;

public abstract class Zwierze extends Organizm {
    public Zwierze(Swiat swiat, Punkt p) {
        super(swiat, p);
    }
    protected Punkt ostatniaPozycja;
    protected Punkt polozenie;

    @Override
    public void akcja(){
        idz();
    }
    void idz() {
        this.ostatniaPozycja = new Punkt(this.polozenie.x, this.polozenie.y);
        boolean wykonanoRuch = false;
        int proby = 10;

        while(proby > 0 && !wykonanoRuch) {
            int dx = rand.nextInt(3) - 1; // -1,0,1
            int dy = rand.nextInt(3) - 1; // -1,0,1
            if(dx == 0 && dy == 0) {
                continue; // nie ruszamy się w miejscu
            }

            Punkt cel = new Punkt(this.polozenie.x + dx, this.polozenie.y + dy);

            if(world.sprawdzCzyWGrid(cel)) {
                Organizm zajety = world.ktoTutaj(cel);
                if(zajety == null) {
                    world.zmienPoz(this.polozenie, cel, this);
                    this.polozenie = cel;
                    wykonanoRuch = true;
                } else {
                    zajety.kolizja(this);
                    wykonanoRuch = true;
                }
            }

            proby--;
        }
    }

}
