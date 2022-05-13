package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Game;
import hu.tapasztaltak.model.Inventory;
import hu.tapasztaltak.model.Protect;
import hu.tapasztaltak.model.Virologist;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ActivePanel extends JPanel {
	private List<AgentView> agents;
	Icon agentIcon = new ImageIcon("src/hu/tapasztaltak/textures/agensPic.png");
	Icon protectIcon = new ImageIcon("src/hu/tapasztaltak/textures/agensPic.png");
	Game game;


	public ActivePanel(){
		super();
		this.game = game;

		setFocusable(true);
		setBackground(new Color(138, 122, 171));
		initComponents();
	}
	private void initComponents(){

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		agentIcon.paintIcon(this,g,0,0);

		/*Virologist v = new Virologist();

		Inventory inv = new Inventory();
		v.setInventory(inv);

		Protect p = new Protect();
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);
		p.spread(v);


		for (int i = 0; i < v.getDefenses().size(); i++){
			protectIcon.paintIcon(this,g,60 + i * 60,0);
		}

		 */
	}
}
