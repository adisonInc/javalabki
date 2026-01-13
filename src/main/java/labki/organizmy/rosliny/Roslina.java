package labki.organizmy.rosliny;

import labki.Punkt;
import labki.Swiat;
import labki.organizmy.Organizm;

public abstract class Roslina extends Organizm {
    public Roslina(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.inicjatywa=0;

    }



    @Override
    public void akcja() {
       int los = world.getRandom().nextInt(30);
       if(los==4){
           rozmnoz(this.polozenie);
       }
    }
    @Override
    public void kolizja(Organizm inny){
        if (inny.getClass() == this.getClass()){
            this.rozmnoz(this.polozenie);
        }else{
            walka(inny);
        }
    }

    public void walka(Organizm inny) {
        if (inny.getSila() <= this.getSila()) {
            inny.setZyje(false);
            world.zmienPoz(inny.getPolozenie(), inny.getPolozenie(), null);
        } else {
            this.setZyje(false);
            world.zmienPoz(this.getPolozenie(), this.getPolozenie(), inny);
            inny.setPolozenie(this.getPolozenie());
        }
    }


}


