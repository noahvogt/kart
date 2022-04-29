package cards;

import java.util.ArrayList;

public class KartenDeck {
    private static final int MIN_DECK_SIZE = 0;
    private static final int MAX_DECK_SIZE = 52;

    private ArrayList<Karte> kartenListe = new ArrayList<Karte>();

    public KartenDeck() {
    }

    public ArrayList<Karte> getDeck() {
        return kartenListe;
    }

    public void setDeck(ArrayList<Karte> kartenListe) {
        this.kartenListe = kartenListe;
    }

    public int getDeckSize() {
        return this.kartenListe.size();
    }

    public void generateFullDeck() {
        ArrayList<Karte> kartenListe = new ArrayList<Karte>();
        for (Karte.Farbe farbe : Karte.Farbe.values()) {
            for (int wert = 2; wert < 15; wert++) {
                kartenListe.add(new Karte(farbe, wert));
            }
        }

        this.kartenListe = kartenListe;
    }

    public void print() {
        Karte karte;
        for (int index = 0; index < this.kartenListe.size(); index++) {
            karte = kartenListe.get(index);
            System.out.println("cards.Karte #" + (index + 1) + "\n" +
                               "Farbe = " + karte.getFarbe() + "\n" +
                               "Wert = " + karte.getWert());
        }
    }

    /* return value '-1' means karte not found */
    public int getListIndexByKarte(Karte karte) {
        int found_index = -1;
        for (int index = 0; index < this.kartenListe.size(); index++) {
            if (karte.getWert() == this.kartenListe.get(index).getWert() &&
                karte.getFarbe() == this.kartenListe.get(index).getFarbe()) {
                found_index = index;
                break;
            }
        }

        return found_index;
    }

    public void removeKarte(Karte karte) {
        int list_index = getListIndexByKarte(karte);
        if (!(list_index == -1))
            this.kartenListe.remove(list_index);
        else
            throw new RuntimeException(String.format("Fehler: Die zu " +
                        "ersetzende Karte '%s' wurde nicht gefunden.",
                        karte.getFarbe().toString() + " " + karte.getWert()));
    }

    public void appendKarte(Karte karte) {
        this.kartenListe.add(karte);
    }

    public static void main(String[] args) {
        KartenDeck kartenDeck = new KartenDeck();
        kartenDeck.generateFullDeck();
        kartenDeck.print();
        System.out.println(kartenDeck.getDeckSize());
        kartenDeck.removeKarte(new Karte(Karte.Farbe.PIK, 10));
        kartenDeck.removeKarte(new Karte(Karte.Farbe.PIK, 10));
        System.out.println(kartenDeck.getDeckSize());
        // kartenDeck.removeKarte(karo7);
        // System.out.println(kartenDeck.getDeckSize());
        for (Karte.Farbe farbe : Karte.Farbe.values()) {
            System.out.println(farbe);
        }

    }
}
