package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import cards.Karte;
import cards.MischMaschine;

public class MainWindow {
    private JFrame frame;
    private JPanel bottomPanel, topPanel, cardPanel;
    private JButton previousPageButton, nextPageButton;
    private JLabel numberOfPagesLabel, numberOfCardsLabel;

    private int numberOfPages, numberOfCards, activePageNumber;
    private MischMaschine kartenDeck;

    public MainWindow() {
        initialize();
    }

	private void initialize() {
        UIManager.put("ToolTip.background", Color.decode("#A3BE8C"));
        UIManager.put("ToolTip.border", new LineBorder(Color.decode("#2e3440"),
                      1));

		frame = createMasterFrame();

        bottomPanel = createBottomPanel();

        topPanel = createTopPanel();

        numberOfPages = 0;
        numberOfCards = 0;
        activePageNumber = 1;

        cardPanel = new JPanel(new GridLayout(3, 8, 10, 10));
        Border border = cardPanel.getBorder();
        Border margin = new EmptyBorder(0, 10, 0, 10);
        cardPanel.setBorder(new CompoundBorder(border, margin));

        kartenDeck = new MischMaschine();
        kartenDeck.generateFullDeck();

        redrawCardPanel();
        updateBottomPanelNumbers();

        displayAndPackFrame();
	}

	private void displayAndPackFrame() {
		frame.add(topPanel, BorderLayout.NORTH);
        frame.add(cardPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();
	}

    private void redrawCardPanel() {
        // if (isNotNeededToDrawMultiplePages())
        //     return;

        int startIndex = (24 * (activePageNumber - 1));
        int endIndex = (Math.min((24 * (activePageNumber - 1) + 23),
                                  (kartenDeck.getDeckSize() - 1)));

        ArrayList<Karte> kartenListe = kartenDeck.getDeck();

        cardPanel.removeAll();

        for (int index = startIndex; index <= endIndex; index++) {
            Karte karte = kartenListe.get(index);
            String iconImagePath = "img/cards/" +
                                   karte.getFarbe().toString().toLowerCase() +
                                   "-" + karte.getWert() + ".png";
            JLabel jlabel = new JLabel();
            ImageIcon labelIcon = new ImageIcon(iconImagePath);
            jlabel.setIcon(labelIcon);
            cardPanel.add(jlabel);
        }

        cardPanel.revalidate();
        cardPanel.repaint();
    }

    // private boolean isNotNeededToDrawMultiplePages() {
    //     return kartenDeck.getDeckSize() <= 24;
    // }

	private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        topPanel.setBackground(Color.decode("#2e3440"));

        JLabel mischmaschineLabel = new JLabel("Mischmaschine");
        ImageIcon mischmaschineIcon = new ImageIcon("img/shuffle-64.png");
        mischmaschineLabel.setBackground(Color.decode("#373d49"));
        mischmaschineLabel.setForeground(Color.decode("#D8DEE9"));
        mischmaschineLabel.setFont(new Font("sans-serif", Font.PLAIN, 24));
        mischmaschineLabel.setIcon(mischmaschineIcon);

        JButton mischButton = new JButton("Mischen");
        mischButton.setBackground(Color.decode("#373d49"));
        mischButton.setForeground(Color.decode("#D8DEE9"));
        mischButton.setFocusable(false);
        mischButton.setFont(new Font("sans-serif", Font.PLAIN, 24));
        mischButton.setToolTipText("Mische alle Karten in der Maschine");

        mischButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
                mischButton.setEnabled(false);
                kartenDeck.mischen();
				goToPageDirection(0);
                mischButton.setEnabled(true);
			}

        });

        topPanel.add(mischmaschineLabel);
        topPanel.add(mischButton);

        return topPanel;
	}

    private void goToPreviousPage() {
        if (activePageNumber == 1)
            return;

        goToPageDirection(-1);
    }

    private void goToNextPage() {
        if (activePageNumber == numberOfPages)
            return;
        goToPageDirection(1);
    }

    private void goToPageDirection(int direction) {
        activePageNumber += direction;
        updateBottomPanelNumbers();
        redrawCardPanel();
    }

	private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        bottomPanel.setBackground(Color.decode("#2e3440"));

        numberOfPagesLabel = new JLabel("Anzahl Seiten: 0");
        numberOfPagesLabel.setForeground(Color.decode("#D8DEE9"));
        numberOfPagesLabel.setFont(new Font("sans-serif", Font.PLAIN, 24));

        numberOfCardsLabel = new JLabel("Anzahl Karten: 0");
        numberOfCardsLabel.setForeground(Color.decode("#D8DEE9"));
        numberOfCardsLabel.setFont(new Font("sans-serif", Font.PLAIN, 24));

        previousPageButton = createArrowButton("←", "vorherigen");
        nextPageButton = createArrowButton("→", "nächsten");

        nextPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                nextPageButton.setEnabled(false);
                goToNextPage();
                nextPageButton.setEnabled(true);
			}
        });

        previousPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                nextPageButton.setEnabled(false);
                goToPreviousPage();
                nextPageButton.setEnabled(true);
			}
        });

        bottomPanel.add(previousPageButton);
        bottomPanel.add(numberOfCardsLabel);
        bottomPanel.add(numberOfPagesLabel);
        bottomPanel.add(nextPageButton);

        return bottomPanel;
	}

    private void updateBottomPanelNumbers() {
        numberOfPages = kartenDeck.getDeckSize() / 24 + 1;
        numberOfPagesLabel.setText("Seite: " + Integer.toString(
                                   activePageNumber) + " / " + Integer.toString(
                                   numberOfPages));

        numberOfCards = kartenDeck.getDeckSize();
        numberOfCardsLabel.setText("Anzahl Karten: " + Integer.toString(
                                   numberOfCards));
    }

	private JFrame createMasterFrame() {
		JFrame frame = new JFrame();
        frame.setTitle("ayyy");
        frame.setLayout(new BorderLayout(10, 5));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        /* center window if floating window management mode */
        frame.setLocationRelativeTo(null);

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(10);
        borderLayout.setVgap(10);
        frame.setLayout(borderLayout);

        return frame;
	}

    private JButton createArrowButton(String text, String pageTarget) {
        JButton jbutton = new JButton(text);
        jbutton.setBackground(Color.decode("#373d49"));
        jbutton.setForeground(Color.decode("#D8DEE9"));
        jbutton.setFocusable(false);
        jbutton.setFont(new Font("sans-serif", Font.PLAIN, 24));
        jbutton.setToolTipText("Gehe zur " + pageTarget + " Seite der " +
                               "Kartenanzeige");
        return jbutton;
    }


    public void show() {}
}
