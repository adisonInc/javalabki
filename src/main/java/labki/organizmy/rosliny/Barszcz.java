package labki.organizmy.rosliny;

import com.googlecode.lanterna.TextColor;
import labki.Punkt;
import labki.Rys;
import labki.Swiat;

public class Barszcz extends Roslina {
    public Barszcz(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.inicjatywa=0;
        this.sila=10;

    }

    @Override
    protected void urodzDziecko(Punkt p) {
        world.dodajOrganizm(new Barszcz(world,p));
    }

    @Override
    public Rys rysowanie() {
        return new Rys('B', TextColor.ANSI.WHITE_BRIGHT);

    }
}
