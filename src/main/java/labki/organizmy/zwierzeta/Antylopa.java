package labki.organizmy.zwierzeta;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import com.googlecode.lanterna.TextColor;
import labki.organizmy.Organizm;

public class Antylopa extends Zwierze {


    public Antylopa(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.sila = 4;
        this.inicjatywa = 4;
        this.wiek = 0;
    }




    @Override
    protected void urodzDziecko(Punkt p) {
        world.dodajOrganizm(new Antylopa(world, p));
    }

    @Override
    public Rys rysowanie() {

        return new Rys('A', TextColor.ANSI.BLUE);
    }

    @Override
    public void akcja(){
        this.idz();
        if(this.isZyje()){
            this.idz();
        }
    }

    @Override
    public void kolizja(Organizm inny){
      if(inny instanceof  Antylopa){
            this.rozmnoz(this.getPolozenie());
            return;
        }
        int proby = 0;
      int los= world.getRandom().nextInt(10);
          if (los>5){
              idz(world.pustySasiad(this.getPolozenie()));
          }else {
              walka(inny);
          }
    }


}
