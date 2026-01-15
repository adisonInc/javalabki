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
            swiat.usunZGrid(inny);
            inny.setZyje(false);
            swiat.przesunOrganizm(this, inny.getPolozenie());
        }else {
            this.setZyje(false);
            swiat.usunZGrid(this);
        }
    }

    void idz() {
        ostatniaPozycja = this.polozenie;
        Random rand = swiat.getRandom();
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

            if(swiat.sprawdzCzyWGrid(rx, ry)) {
                Organizm zajety = swiat.ktoTutaj(rx, ry);
                if(zajety == null) {
                    Punkt cel = new Punkt(rx, ry);
                    swiat.przesunOrganizm(this, cel);
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
            if(swiat.sprawdzCzyWGrid(rx, ry)) {
                Organizm zajety = swiat.ktoTutaj(rx, ry);
                if(zajety == null) {
                    Punkt cel = new Punkt(rx, ry);
                    swiat.przesunOrganizm(this, cel);
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


