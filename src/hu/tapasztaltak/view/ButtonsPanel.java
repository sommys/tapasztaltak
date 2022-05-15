package hu.tapasztaltak.view;

import hu.tapasztaltak.model.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class ButtonsPanel extends JPanel{
	private JLabel playerText = new JLabel();
	private VirologistView curVirView;
	private JButton moveBtn = new JButton();
	private JButton stealBtn = new JButton();
	private JButton attackBtn = new JButton();
	private JButton useAgentBtn = new JButton();
	private JButton scanBtn = new JButton();
	private JButton makeAgentBtn = new JButton();
	private JButton activateSuiteBtn = new JButton();
	private JButton switchSuiteBtn= new JButton();
	private JButton finishRoundBtn= new JButton();
	private Virologist currentVirologist; // TODO lekérni a virológust, most az instance hívással végtelen ciklusba kerül

	public ButtonsPanel(){
		super();
		setFocusable(true);
		setBackground(new Color(125, 220, 191));
		setLayout(new GridLayout(9,1));
		currentVirologist = Game.getInstance().getCurrentVirologist();
		initComponents();
	}

	public void initComponents() {
		curVirView = (VirologistView) Game.objectViewHashMap.get(currentVirologist);
		playerText.setText(curVirView.getPlayerName() + " lép");
		curVirView.setTextColortoVir(playerText);
		playerText.setFont(new Font("Berlin Sans FB Demi",Font.PLAIN,30));
		playerText.setBorder(BorderFactory.createEmptyBorder(10, 35, 10, 10));
		playerText.setHorizontalAlignment(SwingConstants.CENTER);
		playerText.setHorizontalTextPosition(SwingConstants.CENTER);
		add(playerText);
		setButtonSettings(scanBtn);
		scanBtn.setText("tapogat");
		scanBtn.addActionListener(evt -> {
			try {
				currentVirologist.scanning();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		setButtonSettings(moveBtn);
		moveBtn.setText("mozog");
		Field f = new Field(); // TODO lekérni questionpabelből
		moveBtn.addActionListener(evt -> currentVirologist.move(f));
		setButtonSettings(stealBtn);
		stealBtn.setText("lop");
		Virologist v = new Virologist();//TODO lekérni questionpabelből
		stealBtn.addActionListener(evt -> currentVirologist.steal(v));
		setButtonSettings(attackBtn);
		attackBtn.setText("támad");
		Axe a = new Axe(); // TODO lekérni questionpanelből
		attackBtn.addActionListener(evt ->currentVirologist.attack(a,v));
		setButtonSettings(useAgentBtn);
		useAgentBtn.setText("ágenst használ");//TODO lekérniiiiiii questiongeci
		useAgentBtn.addActionListener(evt -> {
			try {
				currentVirologist.useAgent(currentVirologist.getInventory().getAgents().get(0),v);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		setButtonSettings(makeAgentBtn);
		Gene g = new Gene(); // Todo Lekérni
		makeAgentBtn.setText("ágenst készít");
		makeAgentBtn.addActionListener(evt -> currentVirologist.makeAgent(currentVirologist.getLearnt().get(1))); // TODO lekérni questionpannel
		setButtonSettings(switchSuiteBtn);
		switchSuiteBtn.setText("felszerelés csere");//Todo ezt megcsinálni questionpanelből
		switchSuiteBtn.addActionListener(evt -> currentVirologist.switchSuite(currentVirologist.getInventory().getSuites().get(0),currentVirologist.getInventory().getSuites().get(0)));
		setButtonSettings(activateSuiteBtn);
		attackBtn.setText("felszerelés felvétel");
		attackBtn.addActionListener(evt -> currentVirologist.putOnSuite(currentVirologist.getInventory().getSuites().get(0))); //TODO lekérni
		setButtonSettings(finishRoundBtn);
		finishRoundBtn.setText("Kör vége");
		//finishRoundBtn.addActionListener(); //TODO kövi virológus
		add(scanBtn);
		add(moveBtn);
		add(stealBtn);
		add(attackBtn);
		add(useAgentBtn);
		add(makeAgentBtn);
		add(switchSuiteBtn);
		add(attackBtn);
		add(finishRoundBtn);
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

	//public void
}
