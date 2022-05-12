package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel {
	Icon newGameButton = new ImageIcon("src/hu/tapasztaltak/textures/menu/uj_jatek_gomb.png");
	Icon loadGameButton = new ImageIcon("src/hu/tapasztaltak/textures/menu/menuJatekbetoltesGomb.png");
	Icon exitButton = new ImageIcon("src/hu/tapasztaltak/textures/menu/menuKilepesGomb.png");
	Game game;

	transient private BufferedImage backGround;

	public MenuPanel(Game game){
		super();
		this.game = game;
		try {
			backGround = ImageIO.read(new File("src/hu/tapasztaltak/textures/menu/menuHatter.png"));
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		initComponents();
	}

	private void initComponents() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		//todo szebben szethuzni a gombokat
		JButton newGameBtn = new JButton(newGameButton);
		setButtonSettings(newGameBtn);
		newGameBtn.addActionListener(evt -> game.startGameSettings());
		add(newGameBtn);
		JButton loadBtn = new JButton(loadGameButton);
		setButtonSettings(loadBtn);
		loadBtn.addActionListener(evt -> System.out.println("Jatek betoltese"));
		add(loadBtn);
		JButton exitBtn = new JButton(exitButton);
		setButtonSettings(exitBtn);
		exitBtn.addActionListener(evt -> System.exit(0));
		add(exitBtn);
		add(Box.createVerticalGlue());
	}

	/**
	 * Egységes gomb beállítások
	 * @param button amin a beállításokat elvégzi
	 */
	private void setButtonSettings(JButton button) {
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setSize(newGameButton.getIconWidth(), newGameButton.getIconHeight());
		button.setMargin(new Insets(0,0,0,0));
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		add(Box.createVerticalGlue());
	}

	/**
	 * Override-olja a paint komponentet, ezert tudjuk kirajzolni a hatteret
	 * @param g graphics amin van a hatterkep
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backGround.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
	}
}
