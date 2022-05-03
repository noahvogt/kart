package ui;

import java.awt.*;

import javax.swing.*;

public class MainWindow {
    public MainWindow() {
        initialize();
    }

	private void initialize() {
		JFrame frame = createMasterFrame();

        JPanel bottomPanel = createBottomPanel();

        JPanel topPanel = createTopPanel();

        JPanel cardPanel = new JPanel(new GridLayout(3, 8, 10, 10));
        for (int i = 1; i <= 23; i++) {
            //JLabel jlabel = new JLabel("Karte #" + Integer.toString(i));
            JLabel jlabel = new JLabel();
            ImageIcon labelIcon = new ImageIcon("img/cards/card-10_spade.png");
            jlabel.setIcon(labelIcon);
            cardPanel.add(jlabel);
        }

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(cardPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();
	}

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

        topPanel.add(mischmaschineLabel);
        topPanel.add(mischButton);

        return topPanel;
	}

	private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        bottomPanel.setBackground(Color.decode("#2e3440"));

        JLabel numberOfPagesLabel = new JLabel("Anzahl Seiten: 0");
        numberOfPagesLabel.setForeground(Color.decode("#D8DEE9"));
        numberOfPagesLabel.setFont(new Font("sans-serif", Font.PLAIN, 24));

        JLabel numberOfCardsLabel = new JLabel("Anzahl Karten: 0");
        numberOfCardsLabel.setForeground(Color.decode("#D8DEE9"));
        numberOfCardsLabel.setFont(new Font("sans-serif", Font.PLAIN, 24));

        JButton previousPageButton = createArrowButton("←", "vorherigen");
        JButton nextPageButton = createArrowButton("→", "nächsten");

        bottomPanel.add(previousPageButton);
        bottomPanel.add(numberOfCardsLabel);
        bottomPanel.add(numberOfPagesLabel);
        bottomPanel.add(nextPageButton);

        return bottomPanel;
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
        jbutton.setToolTipText("Gehe zur " + pageTarget + "Seite der " +
                               "Kartenanzeige");
        return jbutton;
    }


    public void show() {}
}
