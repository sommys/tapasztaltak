package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Game;
import hu.tapasztaltak.model.Inventory;
import hu.tapasztaltak.model.Protect;
import hu.tapasztaltak.model.Virologist;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ActivePanel extends JPanel {

	private JPanel usableAgentPanel = new JPanel();
	private JPanel effectedAgentPanel = new JPanel();
	private List<AgentView> agents;
	Icon agentIcon = new ImageIcon("src/hu/tapasztaltak/textures/agensPic.png");
	Icon protectIcon = new ImageIcon("src/hu/tapasztaltak/textures/agensPic.png");
	Game game;


	public ActivePanel(){
		super();
		this.game = game;

		setFocusable(true);
		setBackground(new Color(125, 220, 191));
		setLayout(new GridLayout(1,3));
		initComponents();
	}
	private void initComponents(){
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.3;
		c.weighty = 1;
		c.gridx = 0;
		usableAgentPanel.setBackground(new Color(155, 140, 185));
		effectedAgentPanel.setBackground(new Color(162, 171, 211));

		add(usableAgentPanel,c);
		c.weightx = 0.7;
		c.gridx = 1;
		add(effectedAgentPanel, c);
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
