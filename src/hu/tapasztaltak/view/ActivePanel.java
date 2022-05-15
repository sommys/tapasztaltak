package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Game;
import hu.tapasztaltak.model.SpecialModifier;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ActivePanel extends JPanel {

	private JPanel usableAgentPanel = new JPanel();
	private JPanel effectedAgentPanel = new JPanel();
	private List<AgentView> agents;
	Icon agentIcon = new ImageIcon("src/hu/tapasztaltak/textures/agensPic.png");
	Icon protectIcon = new ImageIcon("src/hu/tapasztaltak/textures/agensPic.png");

	public ActivePanel(){
		super();
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

	public void update(){
		agents.clear();
		List<SpecialModifier> activeAgents = Game.getInstance().getCurrentVirologist().getModifiers();
		for(SpecialModifier sm : activeAgents){
			agents.add((AgentView)Game.objectViewHashMap.get(sm));
		}
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		agentIcon.paintIcon(this,g,0,0);
	/*	for(AgentView av : agents){
			av.draw(g);
		}*/
	}
}
