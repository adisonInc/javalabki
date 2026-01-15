package labki.organizmy.zwierzeta;

import java.awt.Color;
import java.util.Random;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import labki.organizmy.Organizm;

public class Zolw extends Zwierze {


    public Zolw(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.sila = 2;
        this.inicjatywa = 1;
        this.wiek = 0;
    }


    @Override
    public void akcja(){
        Random rand = swiat.getRandom();
        int los = rand.nextInt(4);
        if(los==0){
            idz();
            swiat.dodajLogi("Z ruch");
        } else {
            swiat.dodajLogi("Z stoi");
        }
    }
    @Override
    public void kolizja(Organizm napastnik){
        if (napastnik == null) return;
        if (napastnik instanceof Zolw) {
            this.rozmnoz(this.polozenie);
        }
        else if (napastnik.getSila() < 5) {
            if (napastnik instanceof Zwierze) {
                ((Zwierze) napastnik).wroc();
            }
            swiat.dodajLogi("Z odbicie");
        }
        else {
            if (napastnik.getSila() >= this.getSila()) {
                swiat.dodajLogi("Z zjedzony");
                this.setZyje(false);

                Punkt staraPozNapastnika = napastnik.getPolozenie();
                Punkt pozycjaZolwia = this.getPolozenie();

                swiat.zmienPoz(staraPozNapastnika, pozycjaZolwia, napastnik);
                napastnik.setPolozenie(pozycjaZolwia);
            } else {
                swiat.dodajLogi("Z wygral");
                napastnik.setZyje(false);
                swiat.zmienPoz(napastnik.getPolozenie(), napastnik.getPolozenie(), null);
            }
        }
    }
    @Override
    protected void urodzDziecko(Punkt p) {
        swiat.dodajOrganizm(new Zolw(swiat, p));
    }

    @Override
    public Rys rysowanie() {
        return new Rys('Z', Color.GREEN);
    }


}
