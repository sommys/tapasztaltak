package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Game;
import hu.tapasztaltak.model.Virologist;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class ButtonsPanel extends JPanel{

	private JLabel playerText = new JLabel("Játékos következik");
	private VirColor playerColor;
	private JButton moveBtn = new JButton();
	private JButton stealBtn = new JButton();
	private JButton attackBtn = new JButton();
	private JButton useAgentBtn = new JButton();
	private JButton scanBtn = new JButton();
	private JButton makeAgentBtn = new JButton();
	private JButton activateSuiteBtn = new JButton();
	private JButton switchSuiteBtn= new JButton();

	public ButtonsPanel(Virologist v){
		super();
		setFocusable(true);
		setBackground(new Color(125, 220, 191));
		setLayout(new GridLayout(8,1));
		initComponents();
	}

	public void initComponents() {
		//playerText =  //TODO név kiírása
		//playerColor =  //TODO szín visszaszerzése a virologistViewból a virológuson keresztül
		playerText.setFont(new Font("Berlin Sans FB Demi",Font.PLAIN,30));
		playerText.setBorder(BorderFactory.createEmptyBorder(10, 35, 10, 10));
		playerText.setHorizontalAlignment(SwingConstants.CENTER);
		playerText.setHorizontalTextPosition(SwingConstants.CENTER);
		add(playerText);
		setButtonSettings(scanBtn);
		scanBtn.setText("tapogat");
		setButtonSettings(moveBtn);
		moveBtn.setText("mozog");
		setButtonSettings(stealBtn);
		stealBtn.setText("lop");
		setButtonSettings(attackBtn);
		attackBtn.setText("támad");
		setButtonSettings(useAgentBtn);
		useAgentBtn.setText("ágenst használ");
		setButtonSettings(makeAgentBtn);
		makeAgentBtn.setText("ágenst készít");
		setButtonSettings(switchSuiteBtn);
		switchSuiteBtn.setText("felszerelés csere");
		setButtonSettings(activateSuiteBtn);
		attackBtn.setText("felszerelés felvétel");
		add(scanBtn);
		add(moveBtn);
		add(stealBtn);
		add(attackBtn);
		add(useAgentBtn);
		add(makeAgentBtn);
		add(switchSuiteBtn);
		add(attackBtn);
	}

	public void setButtonSettings(JButton button){
		button.setBackground(new Color(125, 220, 191));
		button.setForeground(new Color(208, 253, 239));
		button.setAlignmentX(AbstractButton.RIGHT);
		button.setFont(new Font("Berlin Sans FB Demi",Font.PLAIN,30));
		button.setVerticalTextPosition(AbstractButton.CENTER);
		button.setHorizontalTextPosition(AbstractButton.CENTER);
		button.setOpaque(true);
		button.setBorderPainted(true);
		button.setFocusPainted(true);
		button.setBorder(new BevelBorder(1,((new Color(75, 141, 124))),new Color(208, 253, 239)));
	}
}
