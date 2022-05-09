package hu.tapasztaltak.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel {
	Icon newGameButton = new ImageIcon("src/hu/tapasztaltak/textures/uj_jatek_gomb.png");
	private JButton newGameBtn;
	private JButton loadBtn;
	private JButton exitBtn;

	transient private BufferedImage backGround;

	public MenuPanel(){
		super();
		try {
			backGround = ImageIO.read(new File("src/hu/tapasztaltak/textures/menu_hatter.png"));
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		initComponents();
	}

	private void initComponents() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//todo szebben szethuzni a gombokat
		newGameBtn = new JButton(newGameButton);
		newGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		newGameBtn.setSize(newGameButton.getIconWidth(), newGameButton.getIconHeight());
		newGameBtn.setMargin(new Insets(0,0,0,0));
		newGameBtn.setOpaque(false);
		newGameBtn.setContentAreaFilled(false);
		newGameBtn.setBorderPainted(false);
		newGameBtn.setFocusPainted(false);
		newGameBtn.addActionListener(evt -> System.out.println("Uj jatek inditasa"));
		add(newGameBtn);
		loadBtn = new JButton(newGameButton); //todo kepcsere
		loadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadBtn.setSize(newGameButton.getIconWidth(), newGameButton.getIconHeight());
		loadBtn.setMargin(new Insets(0,0,0,0));
		loadBtn.setOpaque(false);
		loadBtn.setContentAreaFilled(false);
		loadBtn.setBorderPainted(false);
		loadBtn.setFocusPainted(false);
		loadBtn.addActionListener(evt -> System.out.println("Jatek betoltese"));
		add(loadBtn);
		exitBtn = new JButton(newGameButton); //todo kepcsere
		exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitBtn.setSize(newGameButton.getIconWidth(), newGameButton.getIconHeight());
		exitBtn.setMargin(new Insets(0,0,0,0));
		exitBtn.setOpaque(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.setBorderPainted(false);
		exitBtn.setFocusPainted(false);
		exitBtn.addActionListener(evt -> System.exit(0));
		add(exitBtn);
	}

	/**
	 * Override-olja a paint komponentet, ezert tudjuk kirajzolni a hatteret
	 * @param g graphics amin van a hatterkep
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backGround,0,0,null);

	}
}
