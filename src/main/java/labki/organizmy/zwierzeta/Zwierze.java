package labki.organizmy.zwierzeta;

import java.util.Random;

import labki.Punkt;
import labki.Swiat;
import labki.organizmy.Organizm;

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
            world.usunZGrid(inny);
            inny.setZyje(false);
            world.przesunOrganizm(this, inny.getPolozenie());
        }else {
            this.setZyje(false);
            world.usunZGrid(this);
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
                    world.przesunOrganizm(this, cel);
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
                    world.przesunOrganizm(this, cel);
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


