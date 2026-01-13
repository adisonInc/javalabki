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
        rozmnoz(polozenie);
        rozmnoz(polozenie);
        rozmnoz(polozenie);
    }

    @Override
    protected void urodzDziecko(Punkt p) {
        world.dodajOrganizm(new Mlecz(world,p));


    }

    @Override
    public Rys rysowanie() {
        return new Rys('M', Color.DARK_GRAY);
    }
}
