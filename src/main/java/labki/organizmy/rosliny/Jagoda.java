package labki.organizmy.rosliny;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import labki.organizmy.Organizm;

import java.awt.*;

public class Jagoda extends Roslina {
    public Jagoda(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.sila = 99;
    }

    @Override
    public void kolizja(Organizm inny){
        inny.setZyje(false);
        swiat.zmienPoz(inny.getPolozenie(),inny.getPolozenie(),null);
    }

    @Override
    protected void urodzDziecko(Punkt p) {
        swiat.dodajOrganizm(new Jagoda(swiat,p));


    }

    @Override
    public Rys rysowanie() {
        return new Rys('J',  new Color(103, 37, 47));
    }
}
