package labki.organizmy.rosliny;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import labki.organizmy.Organizm;

import java.awt.*;

public class Guarana extends Roslina {
    public Guarana(Swiat swiat, Punkt p) {
        super(swiat, p);
    }

    @Override
    protected void urodzDziecko(Punkt p) {
        world.dodajOrganizm(new Guarana(world,p));
    }

    @Override
    public void kolizja(Organizm inny){
        inny.setSila(inny.getSila()+3);
        this.setZyje(false);
        world.usunZGrid(this);
        world.przesunOrganizm(inny, this.getPolozenie());
    }

    @Override
    public Rys rysowanie() {
        return new Rys('G', new Color(54, 36, 103));
    }
}
