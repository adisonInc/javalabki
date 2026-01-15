package labki.organizmy.rosliny;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import labki.organizmy.Organizm;

import java.awt.*;

public class Mlecz extends Roslina {
    public Mlecz(Swiat swiat, Punkt p) {
        super(swiat, p);

    }

    @Override
    public void akcja() {
        super.akcja();
        super.akcja();
        super.akcja();
    }

    @Override
    protected void urodzDziecko(Punkt p) {
        swiat.dodajOrganizm(new Mlecz(swiat,p));


    }

    @Override
    public Rys rysowanie() {
        return new Rys('M', new Color(9, 68, 0));
    }
}
