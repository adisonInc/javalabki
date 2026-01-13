package labki.organizmy.zwierzeta;

import labki.Punkt;
import labki.Rys;
import labki.Swiat;
import com.googlecode.lanterna.TextColor;
import labki.organizmy.Organizm;
import labki.organizmy.rosliny.Barszcz;
//import labki.organizmy.rosliny.Barszcz;

import java.util.ArrayList;
import java.util.List;

public class Cyber extends Zwierze {
    private static List<Organizm> cacheBarszcze = new ArrayList<>();
    private static int nrTuryCache = -1;

    public Cyber(Swiat swiat, Punkt p) {
        super(swiat, p);
        this.sila = 11;
        this.inicjatywa = 4;
    }

    @Override
    public void akcja() {
        Organizm celBarszcz = najblizszyBarszcz();

        if (celBarszcz == null) {
            super.idz();
            return;
        }

        Punkt pCyber = this.polozenie;
        Punkt pBarszcz = celBarszcz.getPolozenie();

        int dx = Integer.compare(pBarszcz.x(), pCyber.x());
        int dy = Integer.compare(pBarszcz.y(), pCyber.y());

        Punkt nastepnyKrok = new Punkt(pCyber.x() + dx, pCyber.y() + dy);

        if (world.sprawdzCzyWGrid(nastepnyKrok)) {
            this.idz(nastepnyKrok);
        }
    }

    private Organizm najblizszyBarszcz() {
        int obecnaTura = world.getNumerTury();

        if (nrTuryCache != obecnaTura) {
            aktualizujCacheBarszczy();
            nrTuryCache = obecnaTura;
        }

        if (cacheBarszcze.isEmpty()) return null;

        Organizm najblizszy = null;
        int minDystans = Integer.MAX_VALUE;

        for (Organizm b : cacheBarszcze) {
            if (!b.isZyje()) continue;

            int d = Math.max(Math.abs(this.polozenie.x() - b.getPolozenie().x()),
                    Math.abs(this.polozenie.y() - b.getPolozenie().y()));

            if (d < minDystans) {
                minDystans = d;
                najblizszy = b;
                if (minDystans <= 1) break;
            }
        }
        return najblizszy;
    }

    private void aktualizujCacheBarszczy() {
        cacheBarszcze.clear();
        List<Organizm> wszystkie = world.getOrganizmy();

        for (Organizm o : wszystkie) {
            if (o instanceof Barszcz && o.isZyje()) {
                cacheBarszcze.add(o);
            }
        }
    }

    @Override
    public void kolizja(Organizm napastnik) {
        if (napastnik instanceof Cyber) {
            this.rozmnoz(this.polozenie);
        } else {
            super.kolizja(napastnik);
        }
    }

    @Override
    protected void urodzDziecko(Punkt p) {
        world.dodajOrganizm(new Cyber(world, p));
    }

    @Override
    public Rys rysowanie() {
        return new Rys('C', TextColor.ANSI.YELLOW_BRIGHT);
    }
}