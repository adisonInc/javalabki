package labki.organizmy.zwierzeta;

import labki.Punkt;
import labki.Swiat;
import labki.organizmy.Organizm;

import java.io.IOException;
import java.util.Random;

public abstract class Zwierze extends Organizm {
    protected Punkt ostatniaPozycja;
    public Zwierze(Swiat swiat, Punkt p) {
        super(swiat, p);
    }

    @Override
    public void akcja() {
        idz();
    }
    @Override
    public void kolizja(Organizm inny){
        if (inny.getClass() == this.getClass()){
            this.rozmnoz(this.polozenie);
        }else{
            walka(inny);
        }
    }

    public void walka(Organizm inny){
        if (inny.getSila() <= this.getSila()){
            inny.setZyje(false);
            Punkt nowaPoz = inny.getPolozenie();
            Punkt staraPoz = this.getPolozenie();
            world.zmienPoz(staraPoz,nowaPoz,this);
            this.setPolozenie(nowaPoz);
        }else {
            this.setZyje(false);
            world.zmienPoz(this.getPolozenie(),this.getPolozenie(),null);
        }
    }

    void idz() {
        ostatniaPozycja = this.polozenie;
        Random rand = world.getRandom();
        boolean wykonanoRuch = false;
        int proby = 10;
        Punkt startPoz = this.polozenie;

        while(proby > 0 && !wykonanoRuch) {
            int dx = rand.nextInt(3) - 1; // -1,0,1
            int dy = rand.nextInt(3) - 1; // -1,0,1
            if(dx == 0 && dy == 0) {
                proby--;
                continue;
            }

            int rx = startPoz.x() + dx;
            int ry = startPoz.y() + dy;

            if(world.sprawdzCzyWGrid(rx, ry)) {
                Organizm zajety = world.ktoTutaj(rx, ry);
                if(zajety == null) {
                    Punkt cel = new Punkt(rx, ry);
                    world.zmienPoz(startPoz, cel, this);
                    this.setPolozenie(cel);
                    wykonanoRuch = true;
                } else {
                    zajety.kolizja(this);
                    wykonanoRuch = true;
                }
            }

            proby--;
        }
    }
    void idz(Punkt punkt) {
        ostatniaPozycja = this.polozenie;
        boolean wykonanoRuch = false;
        int proby = 10;
        Punkt startPoz = this.polozenie;
            int rx = punkt.x();
            int ry = punkt.y();
            if(world.sprawdzCzyWGrid(rx, ry)) {
                Organizm zajety = world.ktoTutaj(rx, ry);
                if(zajety == null) {
                    Punkt cel = new Punkt(rx, ry);
                    world.zmienPoz(startPoz, cel, this);
                    this.setPolozenie(cel);
                    wykonanoRuch = true;
                } else {
                    zajety.kolizja(this);
                    wykonanoRuch = true;
                }
            }

            proby--;
        }

    protected void wroc() {
        idz(ostatniaPozycja);
    }
}


