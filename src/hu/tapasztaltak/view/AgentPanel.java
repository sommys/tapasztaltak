package hu.tapasztaltak.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AgentPanel extends JPanel {
	private List<AgentView> agents;
	Icon agentIcon = new ImageIcon("src/hu/tapasztaltak/textures/agensPic.png");
	JLabel agent = new JLabel(agentIcon);

	public AgentPanel(){
		super();
		setLayout(new FlowLayout());
		setFocusable(true);
		setBackground(new Color(138, 122, 171));
		initComponents();
	}
	private void initComponents(){

		//add(agent);
	}
}
