package labki.organizmy.zwierzeta;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import com.googlecode.lanterna.TextColor;
import labki.organizmy.Organizm;

import java.util.Random;

public class Zolw extends Zwierze {


    public Zolw(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.sila = 2;
        this.inicjatywa = 1;
        this.wiek = 0;
    }


    @Override
    public void akcja(){
        Random rand = world.getRandom();
        int los = rand.nextInt(4);
        if(los==0){
            idz();
        }
    }
    @Override
    public void kolizja(Organizm napastnik){
        if (napastnik instanceof Zolw) {
            this.rozmnoz(this.polozenie);
        }
        else if (napastnik.getSila() < 5) {
            if (napastnik instanceof Zwierze) {
                ((Zwierze) napastnik).wroc();
            }
            world.dodajLogi("Żółw odpiera atak " + napastnik.getClass().getSimpleName());
        }
        else {
            if (napastnik.getSila() >= this.getSila()) {
                world.dodajLogi(napastnik.getClass().getSimpleName() + " zjada Żółwia");
                this.setZyje(false);

                Punkt staraPozNapastnika = napastnik.getPolozenie();
                Punkt pozycjaZolwia = this.getPolozenie();

                world.zmienPoz(staraPozNapastnika, pozycjaZolwia, napastnik);
                napastnik.setPolozenie(pozycjaZolwia);
            } else {
                world.dodajLogi("Żółw pokonuje " + napastnik.getClass().getSimpleName());
                napastnik.setZyje(false);
                world.zmienPoz(napastnik.getPolozenie(), napastnik.getPolozenie(), null);
            }
        }
    }
    @Override
    protected void urodzDziecko(Punkt p) {
        world.dodajOrganizm(new Zolw(world, p));
    }

    @Override
    public Rys rysowanie() {

        return new Rys('Z', TextColor.ANSI.GREEN_BRIGHT);
    }


}
