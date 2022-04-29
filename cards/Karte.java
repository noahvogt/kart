package cards;

public class Karte {
    public enum Farbe {
        KREUZ, PIK, KARO, HERZ;
    }

    private static final int MIN_WERT = 2;
    private static final int MAX_WERT = 14;

    private int wert;
    private Farbe farbe;

    public Karte(Farbe farbe, int wert) {
        setFarbe(farbe);
        setWert(wert);
    }

    public int getWert() {
        return wert;
    }

    public void setWert(int wert) {
        validateWert(wert);
        this.wert = wert;
    }

    private void validateWert(int wert) {
        if (wert < MIN_WERT || wert > MAX_WERT)
            throw new RuntimeException(
                    String.format("Unerlaubter Wert: %d. Der Wert muss zwischen %d und %d liegen.",
                            wert, MIN_WERT, MAX_WERT));
    }

    public Farbe getFarbe() {
        return farbe;
    }

    public void setFarbe(Farbe farbe) {
        validateFarbe(farbe);
        this.farbe = farbe;
    }

    private void validateFarbe(Farbe farbe) {
        if (farbe == null)
            throw new RuntimeException("Farbe darf kein Null-Objekt sein");
    }
}

