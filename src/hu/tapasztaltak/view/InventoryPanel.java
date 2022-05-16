package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Game;
import hu.tapasztaltak.model.IMaterial;
import hu.tapasztaltak.model.Inventory;
import hu.tapasztaltak.model.Suite;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPanel extends JPanel {
	private class ActiveSuitePanel extends JPanel{
		List<SuiteView> activeSuites = new ArrayList<>();

		public void setActiveSuites(List<SuiteView> activeSuites) {
			this.activeSuites = activeSuites;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			int i = 0;
			for(SuiteView sv : activeSuites){
				sv.setPosX((i++)*100);
				sv.setPosY(0);
				sv.draw(g);
			}
		}
	}
	private class StoragePanel extends JPanel{
		List<ItemView> items = new ArrayList<>();

		public void setItems(List<ItemView> items) {
			this.items = items;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			int i = 0;
			for(ItemView iv : items){
				iv.setPosX((i++)*100);
				iv.setPosY(0);
				iv.draw(g);
			}
		}
	}
	private ActiveSuitePanel activeSuitePanel = new ActiveSuitePanel();
	private StoragePanel storagePanel = new StoragePanel();
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
		add(activeSuitePanel, c);
		c.weightx = 0.7;
		c.gridx = 1;
		add(storagePanel, c);
		update();
	}

	public void update(){
		activeSuitePanel.setBackground(new Color(250, 187, 94));
		storagePanel.setBackground(Game.getCurrentVirologistView().getColor().getColor());

		Inventory actInventory = Game.getCurrentVirologist().getInventory();

		List<SuiteView> activeSuiteViews = new ArrayList<>();
		for(Suite s : actInventory.getSuites().stream().filter(Suite::isActive).collect(Collectors.toList())){
			activeSuiteViews.add((SuiteView) Game.objectViewHashMap.get(s));
		}
		activeSuitePanel.setActiveSuites(activeSuiteViews);

		List<ItemView> storedItemViews = new ArrayList<>();
		for(Suite s : actInventory.getSuites().stream().filter(it -> !it.isActive()).collect(Collectors.toList())){
			storedItemViews.add((SuiteView) Game.objectViewHashMap.get(s));
		}
		for(IMaterial m : actInventory.getMaterials()){
			storedItemViews.add((MaterialView) Game.objectViewHashMap.get(m));
		}
		storagePanel.setItems(storedItemViews);

		storagePanel.repaint();
		activeSuitePanel.repaint();
		revalidate();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

	}
}
