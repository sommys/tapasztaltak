package hu.tapasztaltak.view;

import hu.tapasztaltak.model.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
	private int activeCounter = 0;
	private boolean movebool = false;

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
				activeCounter++;
				movebool = false;
				buttonview();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		setButtonSettings(moveBtn);
		moveBtn.setText("mozog");
		moveBtn.addActionListener(evt -> {
			QuestionPanel q = Game.getInstance().getquestionpanel();
			//q.selectQuestion("Hova hova?", Game.getInstance().getCurrentVirologist().getField().getNeighbours().stream().collect(Collectors.toList()));
			//currentVirologist.move(f);
			List<FieldView> neighbours = new ArrayList<>();
			for(Field n : Game.getCurrentVirologist().getField().getNeighbours()){
				neighbours.add((FieldView)Game.objectViewHashMap.get(n));
			}
			q.moveQuestion(neighbours);
			disableallBtn();
			movebool = true;
			activeCounter++;
			buttonview();
		});
		setButtonSettings(stealBtn);
		stealBtn.setText("lop");
		Virologist v = new Virologist();//TODO lekérni questionpabelből
		stealBtn.addActionListener(evt -> {
			currentVirologist.steal(v);
			activeCounter++;
			buttonview();
		});
		setButtonSettings(attackBtn);
		attackBtn.setText("támad");
		Axe a = new Axe(); // TODO lekérni questionpanelből
		attackBtn.addActionListener(evt ->{
			currentVirologist.attack(a,v);
			activeCounter++;
			buttonview();
		});
		setButtonSettings(useAgentBtn);
		useAgentBtn.setText("ágenst használ");//TODO lekérniiiiiii questiongeci
		useAgentBtn.addActionListener(evt -> {
			try {
				currentVirologist.useAgent(currentVirologist.getInventory().getAgents().get(0),v);
				activeCounter++;
				buttonview();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		setButtonSettings(makeAgentBtn);
		Gene g = new Gene(); // Todo Lekérni
		makeAgentBtn.setText("ágenst készít");
		makeAgentBtn.addActionListener(evt -> {
			currentVirologist.makeAgent(currentVirologist.getLearnt().get(1));
			activeCounter++;
			buttonview();
		}); // TODO lekérni questionpannel
		setButtonSettings(switchSuiteBtn);
		switchSuiteBtn.setText("felszerelés csere");//Todo ezt megcsinálni questionpanelből
		switchSuiteBtn.addActionListener(evt -> {
			currentVirologist.switchSuite(currentVirologist.getInventory().getSuites().get(0),currentVirologist.getInventory().getSuites().get(0));
			activeCounter++;
			buttonview();
		});
		setButtonSettings(activateSuiteBtn);
		attackBtn.setText("felszerelés felvétel");
		attackBtn.addActionListener(evt -> {
			currentVirologist.putOnSuite(currentVirologist.getInventory().getSuites().get(0));
			activeCounter++;
			buttonview();
		}); //TODO lekérni
		setButtonSettings(finishRoundBtn);
		finishRoundBtn.setText("Kör vége");
		finishRoundBtn.addActionListener(evt -> {
			try {
				currentVirologist.endRound();
				playerText.setText(((VirologistView)Game.objectViewHashMap.get(Game.getInstance().getCurrentVirologist())).getPlayerName()+ " lép");
				activeCounter = 0;
				buttonview();

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		add(scanBtn);
		add(moveBtn);
		add(stealBtn);
		add(attackBtn);
		add(useAgentBtn);
		add(makeAgentBtn);
		add(switchSuiteBtn);
		add(attackBtn);
		add(finishRoundBtn);
		add(finishRoundBtn);
		buttonview();

	}
	public void buttonview() {
		if(activeCounter == 0){
			disableallBtn();
			scanBtn.setEnabled(true);
			moveBtn.setEnabled(true);
		}
		else if(activeCounter == 1 && movebool){
			disableallBtn();
			scanBtn.setEnabled(true);
		}
		else if(activeCounter == 1 && !movebool){
			disableallBtn();
			moveBtn.setEnabled(true);
		}
		else if(activeCounter == 2){
			disableallBtn();
			stealBtn.setEnabled(true);
			attackBtn.setEnabled(true);
			useAgentBtn.setEnabled(true);
			makeAgentBtn.setEnabled(true);
			switchSuiteBtn.setEnabled(true);
			attackBtn.setEnabled(true);
			finishRoundBtn.setEnabled(true);
		}
		else{
			disableallBtn();
			finishRoundBtn.setEnabled(true);
		}
		finishRoundBtn.setEnabled(true); /*teszteleshez!!!*/
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

	public void update(){
		curVirView = Game.getCurrentVirologistView();
		curVirView.setTextColortoVir(playerText);
		revalidate();
	}
	public void disableallBtn(){
		scanBtn.setEnabled(false);
		moveBtn.setEnabled(false);
		stealBtn.setEnabled(false);
		attackBtn.setEnabled(false);
		useAgentBtn.setEnabled(false);
		makeAgentBtn.setEnabled(false);
		switchSuiteBtn.setEnabled(false);
		attackBtn.setEnabled(false);
		finishRoundBtn.setEnabled(false);
	}
}
