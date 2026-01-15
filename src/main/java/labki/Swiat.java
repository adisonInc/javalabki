package labki;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import labki.organizmy.Organizm;

public class Swiat {
    private static Swiat instance = null;

    private int N, M; // Wysokość, Szerokość
    private Organizm[][] grid;
    private final List<Organizm> inicjatywy = new ArrayList<>();
    private final List<String> logi = new ArrayList<>();
    private int numerTury = 0;
    private final Random rand = new Random();

    private Swiat() {}

    public static Swiat getSwiat() {
        if (instance == null) {
            instance = new Swiat();
        }
        return instance;
    }

    public void setGrid(int n, int m) {
        this.N = n;
        this.M = m;
        grid = new Organizm[N][M];
    }

    public void wykonajTure() {
        numerTury++;
        logi.clear();

        // Sortowanie po inicjatywie
        inicjatywy.sort((a, b) -> {
            if (a.getInicjatywa() != b.getInicjatywa())
                return b.getInicjatywa() - a.getInicjatywa();
            return b.getWiek() - a.getWiek();
        });

        // Kopia listy, aby uniknąć błędów modyfikacji w pętli
        List<Organizm> kopia = new ArrayList<>(inicjatywy);
        for (Organizm o : kopia) {
            if (o.isZyje()) {
                o.akcja();
                o.incWiek();
            }
        }

        czysczenie();
    }

    private void czysczenie() {
        inicjatywy.removeIf(o -> !o.isZyje());

        for (int y = 0; y < N; y++) {
            for (int x = 0; x < M; x++) {
                if (grid[y][x] != null && !grid[y][x].isZyje()) {
                    grid[y][x] = null;
                }
            }
        }
    }

    public boolean sprawdzCzyWGrid(Punkt p) {
        return p.x() >= 0 && p.x() < M && p.y() >= 0 && p.y() < N;
    }

    public boolean sprawdzCzyWGrid(int x, int y) {
        return x >= 0 && x < M && y >= 0 && y < N;
    }

    public int getGridM() { return M; }
    public int getGridN() { return N; }

    public Organizm ktoTutaj(Punkt p) {
        if (!sprawdzCzyWGrid(p)) return null;
        return grid[p.y()][p.x()];
    }


    public Punkt pustySasiad(Punkt start) {
        for (int i = 0; i < 30; i++) {
            int rx = rand.nextInt(3) - 1 + start.x();
            int ry = rand.nextInt(3) - 1 + start.y();

            // Pomiń, jeśli wylosowało to samo pole, na którym stoimy
            if (rx == start.x() && ry == start.y()) continue;

            Punkt p = new Punkt(rx, ry);
            if (sprawdzCzyWGrid(p) && ktoTutaj(p) == null) {
                return p;
            }
        }
        // Jeśli nie znajdzie pustego sąsiada po 30 próbach, zwraca punkt startowy
        return start;
    }
    public Organizm ktoTutaj(int x, int y) {
        if (!sprawdzCzyWGrid(x, y)) return null;
        return grid[y][x];
    }

    public void przesunOrganizm(Organizm org, Punkt nowy) {
        if (org == null || !sprawdzCzyWGrid(nowy)) return;

        // Czyścimy stare miejsce
        Punkt stary = org.getPolozenie();
        if (stary != null && grid[stary.y()][stary.x()] == org) {
            grid[stary.y()][stary.x()] = null;
        }

        // Ustawiamy nowe
        grid[nowy.y()][nowy.x()] = org;
        org.setPolozenie(nowy);
    }

    // Metoda pomocnicza do walki (np. przesuwanie wygranego na miejsce przegranego)
    public void zmienPoz(Punkt stary, Punkt nowy, Organizm org) {
        if(stary != null && sprawdzCzyWGrid(stary)) grid[stary.y()][stary.x()] = null;
        if(nowy != null && sprawdzCzyWGrid(nowy)) grid[nowy.y()][nowy.x()] = org;
        if(org != null) org.setPolozenie(nowy);
    }

    public void dodajOrganizm(Organizm o) {
        Punkt p = o.getPolozenie();
        if (sprawdzCzyWGrid(p) && grid[p.y()][p.x()] == null) {
            inicjatywy.add(o);
            grid[p.y()][p.x()] = o;
        }
    }

    public void usunZGrid(Organizm org) {
        org.setZyje(false);
        Punkt p = org.getPolozenie();
        if (p != null && sprawdzCzyWGrid(p) && grid[p.y()][p.x()] == org) {
            grid[p.y()][p.x()] = null;
        }
    }
    public int losujPrzesuniecie() {
        // Zwraca -1, 0 lub 1
        return rand.nextInt(3) - 1;
    }
    public Punkt losujPustyPunkt() { // Zostawiam Twoją nazwę metody :)
        for (int i = 0; i < 50; i++) {
            int x = rand.nextInt(M);
            int y = rand.nextInt(N);
            if (grid[y][x] == null) return new Punkt(x, y);
        }
        return null;
    }

    public Punkt losujPunkt() {
        return new Punkt(rand.nextInt(M), rand.nextInt(N));
    }

    public void dodajLogi(String s) { logi.add(s); }
    public List<String> getLogi() { return logi; }
    public List<Organizm> getOrganizmy() { return inicjatywy; }
    public Random getRandom() { return rand; }
    public int getNumerTury() { return numerTury; }

    // Dla kompatybilności z Twoim kodem klawiszy
    private labki.ui.GameCanvas gameCanvas;
    public void setGameCanvas(labki.ui.GameCanvas gc) { this.gameCanvas = gc; }
    public java.util.Set<Integer> getPressedKeys() {
        return gameCanvas != null ? gameCanvas.getPressedKeys() : new java.util.HashSet<>();
    }
}