package labki.organizmy.zwierzeta;

import java.awt.Color;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;

public class Owca extends Zwierze {


    public Owca(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.sila = 4;
        this.inicjatywa = 4;
        this.wiek = 0;
    }

    @Override
    public void akcja() {
        super.akcja();
        swiat.dodajLogi("O ruch");
    }




    @Override
    protected void urodzDziecko(Punkt p) {
        swiat.dodajOrganizm(new Owca(swiat, p));
    }

    @Override
    public Rys rysowanie() {
        return new Rys('O', Color.WHITE);
    }


}
