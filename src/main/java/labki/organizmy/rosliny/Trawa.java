package labki.organizmy.rosliny;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;

import java.awt.*;

public class Trawa extends Roslina {
    public Trawa(Swiat swiat, Punkt p) {
        super(swiat, p);

    }



    @Override
    protected void urodzDziecko(Punkt p) {
        swiat.dodajOrganizm(new Trawa(swiat,p));


    }

    @Override
    public Rys rysowanie() {
        return new Rys('T', new Color(45, 82, 22));
    }
}
