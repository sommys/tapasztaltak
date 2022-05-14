package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Game;
import hu.tapasztaltak.model.Virologist;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InventoryPanel extends JPanel {
	private List<ItemView> items;

	public InventoryPanel(){
		super();
		setFocusable(true);
		setBackground(new Color(215, 96, 196));
		initComponents();
	}
	private void initComponents(){

	}

}
