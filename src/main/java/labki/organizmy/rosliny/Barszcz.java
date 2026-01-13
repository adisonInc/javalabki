package labki.organizmy.rosliny;

import java.awt.Color;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import labki.organizmy.Organizm;

public class Barszcz extends Roslina {
    public Barszcz(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.inicjatywa=0;
        this.sila=10;

    }

    @Override
    public void akcja() {
        super.akcja();
        world.dodajLogi("B roslina");
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny.getClass() == this.getClass()){
            this.rozmnoz(this.polozenie);
            world.dodajLogi("B rozmnazanie");
        } else {
            world.dodajLogi("B trucizna");
            super.kolizja(inny);
        }
    }

    @Override
    protected void urodzDziecko(Punkt p) {
        world.dodajOrganizm(new Barszcz(world,p));
    }

    @Override
    public Rys rysowanie() {
        return new Rys('B', Color.WHITE);
    }
}
