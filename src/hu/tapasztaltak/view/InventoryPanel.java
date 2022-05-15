package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InventoryPanel extends JPanel {
	private JPanel suitePanel = new JPanel();
	private JPanel invPanel = new JPanel();
	private List<ItemView> items;

	public InventoryPanel(){
		super();
		setFocusable(true);
		setBackground(new Color(125, 220, 191));
		initComponents();
	}
	private void initComponents(){
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.3;
		c.weighty = 1;
		c.gridx = 0;
		add(suitePanel, c);
		c.weightx = 0.7;
		c.gridx = 1;
		add(invPanel, c);
		update();
	}

	public void update(){
		suitePanel.setBackground(new Color(250, 187, 94));
		invPanel.setBackground(Game.getCurrentVirologistView().getColor().getColor());
		revalidate();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

	}
}
