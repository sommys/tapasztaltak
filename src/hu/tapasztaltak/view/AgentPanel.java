package hu.tapasztaltak.view;

import hu.tapasztaltak.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AgentPanel extends JPanel {

	protected class UsableAgentPanel extends JPanel{
		List<AgentView> usableAgents = new ArrayList<>();

		public void setUsableAgents(List<AgentView> usableAgents) {
			this.usableAgents = usableAgents;
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			agentIcon.paintIcon(this,g,0,0);
			int i = 1;
			for(AgentView av : usableAgents){
				av.setPosY(0);
				av.setPosX(100*(i++));
				av.draw(g);
			}
		}
	}

	protected class EffectedAgentPanel extends JPanel{
		List<AgentView> effectedAgents = new ArrayList<>();

		public void setEffectedAgents(List<AgentView> effectedAgents) {
			this.effectedAgents = effectedAgents;
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			int i = 0;
			for(AgentView av : effectedAgents){
				av.setPosY(0);
				av.setPosX(100*(i++));
				av.draw(g);
			}
		}
	}

	private final UsableAgentPanel usableAgentPanel = new UsableAgentPanel();
	private final EffectedAgentPanel effectedAgentPanel = new EffectedAgentPanel();

	Icon agentIcon = new ImageIcon("src/hu/tapasztaltak/textures/agensPic.png");

	public AgentPanel(){
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
		List<AgentView> usableAgentViews = new ArrayList<>();
		for(Agent a : Game.getInstance().getCurrentVirologist().getInventory().getAgents()){
			usableAgentViews.add((AgentView) Game.objectViewHashMap.get(a));
		}
		usableAgentPanel.setUsableAgents(usableAgentViews);

		List<AgentView> effectedAgentViews = new ArrayList<>();
		for(SpecialModifier sm : Game.getInstance().getCurrentVirologist().getModifiers()){
			effectedAgentViews.add((AgentView)Game.objectViewHashMap.get(sm));
		}

		List<IDefense> activeDefenseAgents = Game.getInstance().getCurrentVirologist().getDefenses().stream().filter(it -> it instanceof Agent).collect(Collectors.toList());
		for(IDefense d : activeDefenseAgents){
			effectedAgentViews.add((AgentView) Game.objectViewHashMap.get(d));
		}
		effectedAgentPanel.setEffectedAgents(effectedAgentViews);

		revalidate();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		usableAgentPanel.revalidate();
		effectedAgentPanel.revalidate();
	}
}
