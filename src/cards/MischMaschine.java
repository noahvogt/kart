package cards;

import java.util.Collections;

public class MischMaschine extends KartenDeck {
    public void mischen() {
        Collections.shuffle(this.kartenListe);
    }

    public static void main(String[] args) {
        MischMaschine kartenDeck = new MischMaschine();
        kartenDeck.generateFullDeck();
        kartenDeck.mischen();
        kartenDeck.print();
        System.out.println(kartenDeck.getDeckSize());
        kartenDeck.removeKarte(new Karte(Karte.Farbe.PIK, 10));
        //kartenDeck.removeKarte(new Karte(Karte.Farbe.PIK, 10));
        System.out.println(kartenDeck.getDeckSize());
        // kartenDeck.removeKarte(karo7);
        // System.out.println(kartenDeck.getDeckSize());
        for (Karte.Farbe farbe : Karte.Farbe.values()) {
            System.out.println(farbe);
        }

    }
}
