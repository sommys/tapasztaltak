package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Game;
import hu.tapasztaltak.model.Virologist;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InventoryPanel extends JPanel {
	private JPanel suitePanel = new JPanel();
	private JPanel invPanel = new JPanel();
	private List<ItemView> items;

	private Virologist currentVirologist;

	public InventoryPanel(){
		super();
		setFocusable(true);
		//currentVirologist = Game.getInstance().getCurrentVirologist();
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
		suitePanel.setBackground(new Color(234, 192, 139));
		invPanel.setBackground(new Color(215, 96, 196)); //TODO Virológus színének lekérése
		add(suitePanel, c);
		c.weightx = 0.7;
		c.gridx = 1;
		add(invPanel, c);
	}

}
