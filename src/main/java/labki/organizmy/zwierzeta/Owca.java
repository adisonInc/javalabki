package labki.organizmy.zwierzeta;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import com.googlecode.lanterna.TextColor;

public class Owca extends Zwierze {


    public Owca(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.sila = 4;
        this.inicjatywa = 4;
        this.wiek = 0;
    }




    @Override
    protected void urodzDziecko(Punkt p) {
        world.dodajOrganizm(new Owca(world, p));
    }

    @Override
    public Rys rysowanie() {

        return new Rys('O', TextColor.ANSI.WHITE_BRIGHT);
    }


}
