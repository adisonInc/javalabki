package labki.organizmy.zwierzeta;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import com.googlecode.lanterna.TextColor;
import labki.organizmy.Organizm;

public class Wilk extends Zwierze {


    public Wilk(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.sila = 9;
        this.wiek = 0;
        polozenie = new Punkt(p.x,p.y);

    }

    @Override
    public void akcja() {
        this.idz();
    }
    @Override
    public void kolizja(Organizm inny){
        if (inny instanceof Wilk){
            urodzDziecko(world.pustySasiad(this.polozenie));
        }
    }


    @Override
    protected void urodzDziecko(Punkt p) {
        world.dodajOrganizm(new Wilk(world, p));
    }

    @Override
    public Rys rysowanie() {

        return new Rys('W', TextColor.ANSI.RED);
    }


}
