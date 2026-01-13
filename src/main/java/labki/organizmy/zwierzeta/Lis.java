package labki.organizmy.zwierzeta;

import java.awt.Color;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import labki.organizmy.Organizm;

public class Lis extends Zwierze {


    public Lis(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.sila = 3;
        this.inicjatywa = 7;
        this.wiek = 0;
    }

    @Override
    public void akcja() {
        for (int i = 0; i < 10; i++) {
            int vx = world.losujPrzesuniecie();
            int vy = world.losujPrzesuniecie();

            if (vx == 0 && vy == 0) {
                continue;
            }

            Punkt cel = new Punkt(this.polozenie.x() + vx, this.polozenie.y() + vy);

            if (world.sprawdzCzyWGrid(cel)) {
                Organizm ofiara = world.ktoTutaj(cel);

                if (ofiara == null || ofiara.getSila() <= this.getSila()) {
                    this.idz(cel);
                    world.dodajLogi("L ruch ");
                    return;
                }
            }
        }

    }

    @Override
    protected void urodzDziecko(Punkt p) {
        world.dodajOrganizm(new Lis(world, p));
    }

    @Override
    public Rys rysowanie() {
        return new Rys('L', Color.ORANGE);
    }


}
