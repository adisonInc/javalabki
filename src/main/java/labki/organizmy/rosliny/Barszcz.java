package labki.organizmy.rosliny;

import java.awt.Color;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import labki.organizmy.Organizm;
import labki.organizmy.zwierzeta.Cyber;
import labki.organizmy.zwierzeta.Zwierze;

public class Barszcz extends Roslina {
    public Barszcz(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.inicjatywa=0;
        this.sila=10;

    }

    @Override
    public void akcja() {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;

                int nx = this.polozenie.x() + dx;
                int ny = this.polozenie.y() + dy;

                if (world.sprawdzCzyWGrid(nx, ny)) {
                    Organizm ofiara = world.ktoTutaj(nx, ny);
                    if (ofiara != null && ofiara instanceof Zwierze) {
                        if (!(ofiara instanceof Cyber)) {
                            ofiara.setZyje(false);
                            world.dodajLogi("Barszcz zabija sąsiada: " + ofiara.getClass().getSimpleName());
                        }
                    }
                }
            }
        }

        super.akcja();
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
        return new Rys('B',  new Color(104, 141, 104));
    }
}
