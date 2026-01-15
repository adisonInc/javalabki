package labki.organizmy.zwierzeta;

import java.awt.Color;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
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
        swiat.dodajOrganizm(new Antylopa(swiat, p));
    }

    @Override
    public Rys rysowanie() {
        return new Rys('A', Color.CYAN);
    }

    @Override
    public void akcja(){
        this.idz();
        swiat.dodajLogi("A ruch1");
        if(this.isZyje()){
            this.idz();
            swiat.dodajLogi("A ruch2");
        }
    }

    @Override
    public void kolizja(Organizm inny){
      if(inny instanceof  Antylopa){
            this.rozmnoz(this.getPolozenie());
            swiat.dodajLogi("A rozmnazanie");
            return;
        }
      if (inny != null) {
        int los= swiat.getRandom().nextInt(10);
          if (los>5){
              swiat.dodajLogi("A ucieczka");
              idz(swiat.pustySasiad(this.getPolozenie()));
          }else {
              swiat.dodajLogi("A walka");
              walka(inny);
          }
      }
    }


}
