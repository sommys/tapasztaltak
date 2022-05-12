package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Game;

import javax.swing.*;
import java.awt.*;

public class ButtonsPanel extends JPanel{

	Icon buttonIcon = new ImageIcon("src/hu/tapasztaltak/textures/SmallButton.png");
	private JLabel playerText = new JLabel("Játékos következik");
	private VirColor playerColor;
	private JButton moveBtn = new JButton();
	private JButton stealBtn = new JButton();
	private JButton attackBtn = new JButton();
	private JButton useAgentBtn = new JButton();
	private JButton scanBtn = new JButton();
	private JButton makeAgentBtn = new JButton();
	private JButton switchSuiteBtn= new JButton();

	public ButtonsPanel(){
		super();
		setFocusable(true);
		setSize(new Dimension(320,1080));
		setBackground(new Color(125, 220, 191));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initComponents();
	}

	public void initComponents() {
		//playerText =  //TODO név kiírása
		//playerColor =  //TODO szín visszaszerzése a virologistViewból a virológuson keresztül
		playerText.setFont(new Font("Arial",Font.BOLD,40));
		playerText.setBorder(BorderFactory.createEmptyBorder(10, 35, 10, 10));
		playerText.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(playerText);
		setButtonSettings(scanBtn);
		scanBtn.setText("tapogat");
		setButtonSettings(moveBtn);
		moveBtn.setText("mozog");
		setButtonSettings(stealBtn);
		stealBtn.setText("lop");
		setButtonSettings(attackBtn);
		setButtonSettings(useAgentBtn);
		setButtonSettings(makeAgentBtn);
		setButtonSettings(switchSuiteBtn);
		add(scanBtn);
		add(moveBtn);
		add(stealBtn);
		add(attackBtn);
		add(useAgentBtn);
		add(makeAgentBtn);
		add(switchSuiteBtn);
	}

	public void setButtonSettings(JButton button){
		button.setFont(new Font("Arial",Font.BOLD,20));
/*
		button.setVerticalTextPosition(AbstractButton.CENTER);
		button.setHorizontalTextPosition(AbstractButton.CENTER);
		button.setAlignmentX(Component.RIGHT_ALIGNMENT);
		button.setSize(buttonIcon.getIconWidth(), buttonIcon.getIconHeight());
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);

 */
	}
}
