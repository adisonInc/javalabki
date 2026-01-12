package labki;

import labki.organizmy.Organizm;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.TextCharacter;

import java.io.IOException;
import java.util.*;

public class Swiat {
    private static Swiat instance = null;

    private int N, M;
    private Organizm[][] grid;
    private List<Organizm> inicjatywy = new ArrayList<>();
    private List<String> logi = new ArrayList<>();
    private int numerTury = 0;
    private Screen screen;

    private Random rand = new Random();


    private Swiat() {
        this.N = 0;
        this.M = 0;
    }

    public static Swiat getSwiat() {
        if (instance == null) {
            instance = new Swiat();
            return instance;
        }
        return instance;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void setGrid(int n, int m) {
        this.N = n;
        this.M = m;
        grid = new Organizm[N][M];
    }

    public void paintGrid() throws IOException {
        if (screen == null) return;
        screen.clear();

        for (int y = 0; y < N; y++) {
            for (int x = 0; x < M; x++) {
                TextCharacter tc;
                if (grid[y][x] != null) {
                    Rys dane = grid[y][x].rysowanie();
                    tc = new com.googlecode.lanterna.TextCharacter(
                            dane.symbol,
                            dane.color,
                            TextColor.ANSI.BLACK
                    );

                } else {
                    tc = new TextCharacter('.');
                }
                screen.setCharacter(x, y, tc);
            }
        }

        wypiszLogi();
        screen.refresh();
    }

    public int getGridN() { return N; }
    public int getGridM() { return M; }

    public boolean sprawdzCzyWGrid(Punkt p) {
        return p.x >= 0 && p.x < M && p.y >= 0 && p.y < N;
    }

    public boolean sprawdzPole(Punkt p) {
        return grid[p.y][p.x] != null;
    }

    public Punkt pustySasiad(Punkt start) {
        for (int i = 0; i < 30; i++) {
            int xr = rand.nextInt(3) - 1 + start.x;
            int yr = rand.nextInt(3) - 1 + start.y;
            Punkt rnd = new Punkt(xr, yr);
            if (sprawdzCzyWGrid(rnd) && ktoTutaj(rnd) == null) {
                return rnd;
            }
        }
        return start;
    }

    public Organizm ktoTutaj(Punkt p) {
        return grid[p.y][p.x];
    }

    public void wykonajTure() {
        numerTury++;
        sortInicjatywa();
        List<Organizm> kopia = new ArrayList<>(inicjatywy);

        for (Organizm o : kopia) {
            if (o.isZyje()) {
                o.akcja();
                StringBuilder s = new StringBuilder();
                s.append(o.rysowanie().symbol);
                s.append(" Wiek: ").append(o.getWiek());
                s.append(" Sila: ").append(o.getSila());
                dodajLogi("ruch: " + s);
                o.incWiek();
            }
        }

        czysczenie();
    }

    private void sortInicjatywa() {
        inicjatywy.sort((a, b) -> {
            if (a.getInicjatywa() != b.getInicjatywa())
                return b.getInicjatywa() - a.getInicjatywa();
            return b.getWiek() - a.getWiek();
        });
    }

    private void czysczenie() {

        for (int y = 0; y < N; y++) {
            for (int x = 0; x < M; x++) {
                if (grid[y][x] != null && !grid[y][x].isZyje()) {
                    grid[y][x] = null;
                }
            }
        }


        inicjatywy.removeIf(o -> !o.isZyje());
    }

    public List<Organizm> getInicjatywy() {
        return inicjatywy;
    }

    public void zmienPoz(Punkt stary, Punkt nowy, Organizm org) {
        grid[stary.y][stary.x] = null;
        grid[nowy.y][nowy.x] = org;
    }

    public void dodajLogi(String s) {
        logi.add(s);
    }

    private void wypiszLogi() throws IOException {
        if (screen == null) return;
        int row = 0;
        for (String log : logi) {
            if (row < N) {
                screen.setCharacter(M + 1, row, new TextCharacter(log.charAt(0))); // prosto, można poprawić
                row++;
            }
        }
        czyscLogi();
    }

    private void czyscLogi() {
        logi.clear();
    }

    public Punkt losujPunkt(){
        int x = rand.nextInt(M-1);
        int y = rand.nextInt(N-1);
        return new Punkt(x,y);
    }
    public Punkt losujPustyPunky(){
        int mozliwosci = N * M;
        while (mozliwosci>0){
            Punkt p = losujPunkt();
            if(ktoTutaj(p)==null){return p;}
            mozliwosci--;
        }
        return null;
    }

    public int getNumerTury() {
        return numerTury;
    }
    public void wypelnijGrid(Organizm o, int ilosc){
        for (int i = 0; i < ilosc; i++) {
            inicjatywy.add(o);
            o.setSwiat(this);
            Punkt p = losujPustyPunky();
            o.setPolozenie(p);

            grid[p.y][p.x] = o;

        }

    }

    public void dodajOrganizm(Organizm o) {
        inicjatywy.add(o);
        Punkt p = o.getPolozenie();
        grid[p.y][p.x] = o;
    }
}
