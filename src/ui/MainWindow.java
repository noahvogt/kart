package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import cards.Karte;
import cards.KartenDeck;

public class MainWindow {
    final int GAP_SIZE = 10;
    final int CARD_ROWS = 3, CARD_COLUMNS = 7;

    private JFrame masterFrame;
    private JPanel bottomPanel, topPanel, cardPanel;
    private JButton previousPageButton, nextPageButton;
    private JLabel numberOfPagesLabel, numberOfCardsLabel;

    private int numberOfPages = 0, numberOfCards = 0, activePageNumber = 1;
    int startIndex, endIndex;
    private KartenDeck kartenDeck;

    public MainWindow() {
        initialize();
    }

	private void initialize() {
        applyUIManagerTheme();

		createMasterFrame();

        createBottomPanel();
        createTopPanel();
        createCardPanel();

        createCardDeck();

        redrawCardPanel();
        updateBottomPanelNumbers();

        displayAndPackFrame();
	}

	private void createCardDeck() {
		kartenDeck = new KartenDeck();
        kartenDeck.generateFullDeck();
	}

	private void applyUIManagerTheme() {
		UIManager.put("ToolTip.background", Color.decode("#A3BE8C"));
        UIManager.put("ToolTip.border", new LineBorder(Color.decode("#2E3440"),
                      1));
	}

	private void createCardPanel() {
		cardPanel = new JPanel(new GridLayout(CARD_ROWS, CARD_COLUMNS,
                                              GAP_SIZE, GAP_SIZE));
        Border border = cardPanel.getBorder();
        Border margin = new EmptyBorder(0, GAP_SIZE, 0, GAP_SIZE);
        cardPanel.setBorder(new CompoundBorder(border, margin));
	}

	private void displayAndPackFrame() {
		masterFrame.add(topPanel, BorderLayout.NORTH);
        masterFrame.add(cardPanel, BorderLayout.CENTER);
        masterFrame.add(bottomPanel, BorderLayout.SOUTH);
        masterFrame.pack();
	}

    private void redrawCardPanel() {
        cardPanel.removeAll();
        calulateStartAndEndIndex();

        ArrayList<Karte> kartenListe = kartenDeck.getDeck();

        for (int index = startIndex; index <= endIndex; index++) {
            Karte karte = kartenListe.get(index);
            URL iconImagePath = ClassLoader.getSystemClassLoader().
                                   getResource("img/cards/" +
                                   karte.getFarbe().toString().toLowerCase() +
                                   "-" + karte.getWert() + ".png");
            JLabel jlabel = new JLabel();
            ImageIcon labelIcon = new ImageIcon(iconImagePath);
            jlabel.setIcon(labelIcon);
            cardPanel.add(jlabel);
        }

        fillUpWithEmptyCardsIfNeeded();
        cardPanel.revalidate();
        cardPanel.repaint();
    }

	private void calulateStartAndEndIndex() {
		startIndex = (CARD_ROWS * CARD_COLUMNS * (activePageNumber - 1));
        endIndex = (Math.min((CARD_ROWS * CARD_COLUMNS * (activePageNumber - 1)
                        + (CARD_ROWS * CARD_COLUMNS) - 1), (kartenDeck.
                        getDeckSize()- 1)));
	}

    private void fillUpWithEmptyCardsIfNeeded() {
        if ((endIndex - startIndex) >= ((CARD_ROWS - 1 ) * CARD_COLUMNS)) {
            return;
        }

        for (int index = endIndex; index < startIndex + ((CARD_ROWS *
                        CARD_COLUMNS) - 1); index++) {
            URL iconImagePath = ClassLoader.getSystemClassLoader().
                                   getResource("img/cards/empty.png");
            JLabel jlabel = new JLabel();
            ImageIcon labelIcon = new ImageIcon(iconImagePath);
            jlabel.setIcon(labelIcon);
            cardPanel.add(jlabel);
        }
    }

    private void applyNordTextTheme(JComponent jComponent) {
        jComponent.setBackground(Color.decode("#373D49"));
        jComponent.setForeground(Color.decode("#D8DEE9"));
        jComponent.setFont(new Font("sans-serif", Font.PLAIN, 24));
    }

	private void createTopPanel() {
        topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, GAP_SIZE));
        topPanel.setBackground(Color.decode("#2E3440"));

        JLabel mischMaschineLabel = createMischMaschineLabel();
        JButton mischButton = createMischButton();
        JButton resetButton = createResetButton();

        resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetButton.setEnabled(false);
                createCardDeck();
				goToPageDirection(0);
                resetButton.setEnabled(true);
			}

        });

        mischButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
                mischButton.setEnabled(false);
                kartenDeck.mischen();
				goToPageDirection(0);
                mischButton.setEnabled(true);
			}
        });

        topPanel.add(mischMaschineLabel);
        topPanel.add(mischButton);
        topPanel.add(resetButton);
	}

	private JLabel createMischMaschineLabel() {
		JLabel mischmaschineLabel = new JLabel("Mischmaschine");
        applyNordTextTheme(mischmaschineLabel);
        ImageIcon mischmaschineIcon = new ImageIcon(ClassLoader.
                getSystemClassLoader().getResource("img/shuffle-64.png"));
        mischmaschineLabel.setIconTextGap(GAP_SIZE);
        mischmaschineLabel.setIcon(mischmaschineIcon);
		return mischmaschineLabel;
	}

    private JButton createResetButton() {
        JButton resetButton = new JButton("Reset");
        applyNordTextTheme(resetButton);
        resetButton.setFocusable(false);
        resetButton.setToolTipText("Setze die Kartenpositionen zurück");
        return resetButton;
    }

	private JButton createMischButton() {
		JButton mischButton = new JButton("Mischen");
        applyNordTextTheme(mischButton);
        mischButton.setFocusable(false);
        mischButton.setToolTipText("Mische alle Karten in der Maschine");
		return mischButton;
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

	private void createBottomPanel() {
        bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, GAP_SIZE));
        bottomPanel.setBackground(Color.decode("#2E3440"));

        numberOfPagesLabel = createBottomLabel();
        numberOfCardsLabel = createBottomLabel();

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
	}

	private JLabel createBottomLabel() {
		JLabel bottomLabel = new JLabel();
        bottomLabel.setForeground(Color.decode("#D8DEE9"));
        bottomLabel.setFont(new Font("sans-serif", Font.PLAIN, 24));
        return bottomLabel;
	}

    private void updateBottomPanelNumbers() {
        numberOfPages = kartenDeck.getDeckSize() / (CARD_ROWS * CARD_COLUMNS) + 1;
        numberOfPagesLabel.setText("Seite: " + Integer.toString(
                                   activePageNumber) + " / " + Integer.toString(
                                   numberOfPages));

        numberOfCards = kartenDeck.getDeckSize();
        numberOfCardsLabel.setText("Anzahl Karten: " + Integer.toString(
                                   numberOfCards));
    }

	private void createMasterFrame() {
		masterFrame = new JFrame();
        masterFrame.setTitle("Mischmaschine");
        masterFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        masterFrame.setResizable(false);
        masterFrame.setVisible(true);

        /* spawn window centered (in floating window management mode) */
        masterFrame.setLocationRelativeTo(null);

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(GAP_SIZE);
        borderLayout.setVgap(GAP_SIZE);
        masterFrame.setLayout(borderLayout);
	}

    private JButton createArrowButton(String text, String pageTarget) {
        JButton jbutton = new JButton(text);
        applyNordTextTheme(jbutton);
        jbutton.setFocusable(false);
        jbutton.setToolTipText("Gehe zur " + pageTarget + " Seite der " +
                               "Kartenanzeige");
        return jbutton;
    }

    public void show() {}
}
