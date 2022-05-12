package hu.tapasztaltak.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AgentPanel extends JPanel {
	private List<AgentView> agents;

	public AgentPanel(){
		super();
		setFocusable(true);
		setBackground(new Color(138, 122, 171));
	}
}
