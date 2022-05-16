package hu.tapasztaltak.view;

import hu.tapasztaltak.model.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ButtonsPanel extends JPanel{
	private final JLabel playerText = new JLabel();
	private VirologistView curVirView;
	private final JButton moveBtn = new JButton();
	private final JButton stealBtn = new JButton();
	private final JButton attackBtn = new JButton();
	private final JButton useAgentBtn = new JButton();
	private final JButton scanBtn = new JButton();
	private final JButton makeAgentBtn = new JButton();
	private final JButton activateSuiteBtn = new JButton();
	private final JButton switchSuiteBtn= new JButton();
	private final JButton finishRoundBtn= new JButton();
	private Virologist currentVirologist;

	public ButtonsPanel(){
		super();
		setFocusable(true);
		setBackground(new Color(125, 220, 191));
		setLayout(new GridLayout(10,1));
		currentVirologist = Game.getCurrentVirologist();
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
			currentVirologist.scanning();
			FieldView fv = (FieldView)Game.objectViewHashMap.get(Game.getCurrentVirologist().getField());
			fv.setVisited(true);
			Game.getInstance().updatePanels();
			buttonview(Action.SCAN);
		});
		setButtonSettings(moveBtn);
		moveBtn.setText("mozog");
		moveBtn.addActionListener(evt -> {
			QuestionPanel q = Game.getInstance().getquestionpanel();
			List<FieldView> neighbours = new ArrayList<>();
			for(Field n : Game.getCurrentVirologist().getField().getNeighbours()){
				neighbours.add((FieldView)Game.objectViewHashMap.get(n));
			}
			q.moveQuestion(neighbours);
			disableallBtn();
			buttonview(Action.MOVE);
		});
		setButtonSettings(stealBtn);
		stealBtn.setText("lop");
		stealBtn.addActionListener(evt -> {
			List<VirologistView> victims = new ArrayList<>();
			for(Virologist victim : Game.getCurrentVirologist().getField().getVirologists().stream().filter(it -> it != Game.getCurrentVirologist() && it.isStunned()).collect(Collectors.toList())){
				victims.add((VirologistView) Game.objectViewHashMap.get(victim));
			}
			Game.getInstance().getquestionpanel().stealPickVirologistQuestion(victims);
			buttonview(Action.ACTION);
		});
		setButtonSettings(attackBtn);
		attackBtn.setText("támad");
		attackBtn.addActionListener(evt ->{
			List<VirologistView> possibleVictims = new ArrayList<>();
			for(Virologist vict : currentVirologist.getField().getVirologists()){
				if(vict == currentVirologist) continue;
				possibleVictims.add((VirologistView) Game.objectViewHashMap.get(vict));
			}
			Game.getInstance().getquestionpanel().attackQuestion(possibleVictims);
			buttonview(Action.ACTION);
		});
		setButtonSettings(useAgentBtn);
		useAgentBtn.setText("ágenst használ");
		useAgentBtn.addActionListener(evt -> {
			Game.getInstance().getquestionpanel().pickAgentUseAgentQuestion();
			buttonview(Action.ACTION);
		});
		setButtonSettings(makeAgentBtn);
		makeAgentBtn.setText("ágenst készít");
		makeAgentBtn.addActionListener(evt -> {
			Game.getInstance().getquestionpanel().pickAgentCraftQuestion();
			buttonview(Action.ACTION);
		});
		setButtonSettings(switchSuiteBtn);
		switchSuiteBtn.setText("felszerelés csere");
		switchSuiteBtn.addActionListener(evt -> {
			Game.getInstance().getquestionpanel().switchSuiteFromQuestion();
			buttonview(Action.ACTION);
		});
		setButtonSettings(activateSuiteBtn);
		activateSuiteBtn.setText("felszerelés felvétel");
		activateSuiteBtn.addActionListener(evt -> {
			Game.getInstance().getquestionpanel().putOnSuiteQuestion();
			buttonview(Action.ACTION);
		});
		setButtonSettings(finishRoundBtn);
		finishRoundBtn.setText("Kör vége");
		finishRoundBtn.addActionListener(evt -> {
			if(!Game.running){
				System.exit(0);
			}
			Game.getInstance().getquestionpanel().removeAll();
			Game.getInstance().getquestionpanel().revalidate();
			currentVirologist.endRound();
			playerText.setText(((VirologistView)Game.objectViewHashMap.get(Game.getInstance().getCurrentVirologist())).getPlayerName()+ " lép");
			buttonview(Action.ENDROUND);
		});
		add(scanBtn);
		add(moveBtn);
		add(stealBtn);
		add(attackBtn);
		add(useAgentBtn);
		add(makeAgentBtn);
		add(switchSuiteBtn);
		add(activateSuiteBtn);
		add(finishRoundBtn);
		buttonview(Action.ENDROUND);
	}

	public enum Action{
		SCAN, MOVE, ACTION, ENDROUND
	}

	public void buttonview(Action a) {
		disableallBtn();
		finishRoundBtn.setEnabled(true);
		switch(a){
			case SCAN:{
				finishRoundBtn.setEnabled(true);
				enableActionButtons();
				break;
			}
			case MOVE:{
				scanBtn.setEnabled(true);
				break;
			}
			case ACTION:{
				disableallBtn();
				break;
			}
			case ENDROUND:{
				scanBtn.setEnabled(true);
				moveBtn.setEnabled(true);
				break;
			}
		}
		disableUnavailableButtons();
	}

	private void disableUnavailableButtons() {
		if(Game.getCurrentVirologist() != null && Game.getCurrentVirologist().getField() != null) {
			if (Game.getCurrentVirologist().isStunned())
				moveBtn.setEnabled(false);
			if (Game.getCurrentVirologist().getField().getVirologists().stream().noneMatch(it -> it != Game.getCurrentVirologist() && it.isStunned()))
				stealBtn.setEnabled(false);
			if (Game.getCurrentVirologist().getInventory().getSuites().stream().noneMatch(it -> it instanceof Axe && it.isActive() && !((Axe) it).isUsed()))
				attackBtn.setEnabled(false);
			if (Game.getCurrentVirologist().getInventory().getAgents().isEmpty()){
				useAgentBtn.setEnabled(false);
			}
			if (Game.getCurrentVirologist().getInventory().getSuites().stream().noneMatch(Suite::isActive) || Game.getCurrentVirologist().getInventory().getSuites().stream().noneMatch(it -> !it.isActive()))
				switchSuiteBtn.setEnabled(false);
			if (Game.getCurrentVirologist().getInventory().getSuites().stream().noneMatch(it -> !it.isActive()))
				activateSuiteBtn.setEnabled(false);
			if (Game.getCurrentVirologist().getLearnt().isEmpty()) {
				makeAgentBtn.setEnabled(false);
			} else {
				boolean canMake = false;
				for(Gene g : Game.getCurrentVirologist().getLearnt()){
					List<IMaterial> needed = g.getMaterials();
					List<IMaterial> have = new ArrayList<>(Game.getCurrentVirologist().getInventory().getMaterials());
					int i = 0;
					ArrayList<IMaterial> found = new ArrayList<>();

					for (IMaterial m : needed) {
						while (i < have.size() && !m.isCompatible(have.get(i))) {
							i++;
						}
						if (i != have.size()) {
							found.add(have.get(i));
							have.remove(i);
							i=0;
						} else {
							break;
						}
					}
					if(needed.size() == found.size()){
						canMake = true;
						break;
					}
				}
				makeAgentBtn.setEnabled(canMake);
			}
		}
	}

	private void enableActionButtons() {
		stealBtn.setEnabled(true);
		attackBtn.setEnabled(true);
		useAgentBtn.setEnabled(true);
		makeAgentBtn.setEnabled(true);
		switchSuiteBtn.setEnabled(true);
		attackBtn.setEnabled(true);
		activateSuiteBtn.setEnabled(true);
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
		currentVirologist = Game.getCurrentVirologist();
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
		activateSuiteBtn.setEnabled(false);
		finishRoundBtn.setEnabled(false);
	}
}
