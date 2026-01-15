package labki.organizmy.zwierzeta;

import java.awt.Color;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;

public class Wilk extends Zwierze {


    public Wilk(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.sila = 9;
        this.inicjatywa = 5;
        this.wiek = 0;
    }

    @Override
    public void akcja() {
        this.idz();
        swiat.dodajLogi("W ruch");
    }


    @Override
    protected void urodzDziecko(Punkt p) {
        swiat.dodajOrganizm(new Wilk(swiat, p));
    }

    @Override
    public Rys rysowanie() {
        return new Rys('W', Color.RED);
    }


}
