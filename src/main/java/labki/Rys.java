package labki;

import com.googlecode.lanterna.TextColor;

public class Rys {
    public char symbol;
    public TextColor.ANSI color;

    public Rys(char symbol, TextColor.ANSI color) {
        this.symbol = symbol;
        this.color = color;
    }
}
