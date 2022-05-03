package ui;

import java.awt.*;

import javax.swing.*;

public class MainWindow {
    private JFrame frame;

    public MainWindow() {
        initialize();
    }

	private void initialize() {
		frame = new JFrame();
        frame.setTitle("ayyy");
        frame.setLayout(new BorderLayout(10, 5));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        /* center window if floating window management mode */
        frame.setLocationRelativeTo(null);

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(10);
        borderLayout.setVgap(10);
        frame.setLayout(borderLayout);

        frame.setResizable(false);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setBackground(Color.RED);
        //panel.setPreferredSize(new Dimension(200,50));

        Button button = new Button("TITLE");
        panel.add(button);

        frame.add(panel, BorderLayout.SOUTH);
	}

    public void show() {
    }
}
