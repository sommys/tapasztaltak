package hu.tapasztaltak.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ActivePanel extends JPanel {
	private List<AgentView> agents;
	Icon agentIcon = new ImageIcon("src/hu/tapasztaltak/textures/agensPic.png");


	public ActivePanel(){
		super();
		setLayout(new FlowLayout());
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
	}
}
