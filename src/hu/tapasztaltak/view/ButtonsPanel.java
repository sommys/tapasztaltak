package hu.tapasztaltak.view;

import hu.tapasztaltak.model.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	private Virologist currentVirologist;
	public static int activeCounter = 0;
	private boolean movebool = false;

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
			try {
				currentVirologist.scanning();
				FieldView fv = (FieldView)Game.objectViewHashMap.get(Game.getCurrentVirologist().getField());
				fv.setVisited(true);
				Game.getInstance().updatePanels();
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
		Virologist v = new Virologist();
		stealBtn.addActionListener(evt -> {
			List<VirologistView> victims = new ArrayList<>();
//			Axe axe = new Axe();
//			Bag bag = new Bag();
//			Cape cape = new Cape();
//			Gloves gloves = new Gloves();
//			Aminoacid amino = new Aminoacid();
//			Nucleotid nucleo = new Nucleotid();
//			Nucleotid nucleo2 = new Nucleotid();
//			Nucleotid nucleo3 = new Nucleotid();
//			Nucleotid nucleo4 = new Nucleotid();
//			Nucleotid nucleo5 = new Nucleotid();
//			Nucleotid nucleo6 = new Nucleotid();
//			Nucleotid nucleo7 = new Nucleotid();
//			Nucleotid nucleo8 = new Nucleotid();
//
//			AxeView axeView = new AxeView(axe);
//			BagView bagView = new BagView(bag);
//			CapeView capeView = new CapeView(cape);
//			GlovesView glovesView = new GlovesView(gloves);
//			AminoacidView aminoView = new AminoacidView(amino);
//			NucleotidView nucleoView = new NucleotidView(nucleo);
//			NucleotidView nucleoView2 = new NucleotidView(nucleo2);
//			NucleotidView nucleoView3 = new NucleotidView(nucleo3);
//			NucleotidView nucleoView4 = new NucleotidView(nucleo4);
//			NucleotidView nucleoView5 = new NucleotidView(nucleo5);
//			NucleotidView nucleoView6 = new NucleotidView(nucleo6);
//			NucleotidView nucleoView7 = new NucleotidView(nucleo7);
//			NucleotidView nucleoView8 = new NucleotidView(nucleo8);
//
//			Game.addView(axe, axeView);
//			Game.addView(bag, bagView);
//			Game.addView(cape, capeView);
//			Game.addView(gloves, glovesView);
//			Game.addView(amino, aminoView);
//			Game.addView(nucleo, nucleoView);
//			Game.addView(nucleo2, nucleoView2);
//			Game.addView(nucleo3, nucleoView3);
//			Game.addView(nucleo4, nucleoView4);
//			Game.addView(nucleo5, nucleoView5);
//			Game.addView(nucleo6, nucleoView6);
//			Game.addView(nucleo7, nucleoView7);
//			Game.addView(nucleo8, nucleoView8);

//			for(Virologist toStun : Game.getCurrentVirologist().getField().getVirologists().stream().filter(it -> it != Game.getCurrentVirologist()).collect(Collectors.toList())){
//				toStun.setStunned(true);
//				axe.add(toStun.getInventory());
//				bag.add(toStun.getInventory());
//				cape.add(toStun.getInventory());
//				gloves.add(toStun.getInventory());
//				amino.add(toStun.getInventory());
//				nucleo.add(toStun.getInventory());
//				nucleo2.add(toStun.getInventory());
//				nucleo3.add(toStun.getInventory());
//				nucleo4.add(toStun.getInventory());
//				nucleo5.add(toStun.getInventory());
//				nucleo6.add(toStun.getInventory());
//				nucleo7.add(toStun.getInventory());
//				nucleo8.add(toStun.getInventory());
//
//				axe.activate(toStun);
//				bag.activate(toStun);
//				cape.activate(toStun);
//				((VirologistView)Game.objectViewHashMap.get(toStun)).updateImage();
//			}
//			Game.getInstance().updatePanels();
			for(Virologist victim : Game.getCurrentVirologist().getField().getVirologists().stream().filter(it -> it != Game.getCurrentVirologist() && it.isStunned()).collect(Collectors.toList())){
				victims.add((VirologistView) Game.objectViewHashMap.get(victim));
			}
			Game.getInstance().getquestionpanel().stealPickVirologistQuestion(victims);
			activeCounter++;
			buttonview();
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
			activeCounter++;
			buttonview();
		});
		setButtonSettings(useAgentBtn);
		useAgentBtn.setText("ágenst használ");
		useAgentBtn.addActionListener(evt -> {
			Game.getInstance().getquestionpanel().pickAgentUseAgentQuestion();
			activeCounter++;
			buttonview();
		});
		setButtonSettings(makeAgentBtn);
		makeAgentBtn.setText("ágenst készít");
		makeAgentBtn.addActionListener(evt -> {
			Game.getInstance().getquestionpanel().pickAgentCraftQuestion();
			activeCounter++;
			buttonview();
		});
		setButtonSettings(switchSuiteBtn);
		switchSuiteBtn.setText("felszerelés csere");
		switchSuiteBtn.addActionListener(evt -> {
			Game.getInstance().getquestionpanel().switchSuiteFromQuestion();
			activeCounter++;
			buttonview();
		});
		setButtonSettings(activateSuiteBtn);
		activateSuiteBtn.setText("felszerelés felvétel");
		activateSuiteBtn.addActionListener(evt -> {
			Game.getInstance().getquestionpanel().putOnSuiteQuestion();
			activeCounter++;
			buttonview();
		});
		setButtonSettings(finishRoundBtn);
		finishRoundBtn.setText("Kör vége");
		finishRoundBtn.addActionListener(evt -> {
			Game.getInstance().getquestionpanel().removeAll();
			Game.getInstance().getquestionpanel().revalidate();
			currentVirologist.endRound();
			playerText.setText(((VirologistView)Game.objectViewHashMap.get(Game.getInstance().getCurrentVirologist())).getPlayerName()+ " lép");
			activeCounter = 0;
			buttonview();
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
			activateSuiteBtn.setEnabled(true);
		}
		else{
			disableallBtn();
		}

		if(Game.getCurrentVirologist() != null && Game.getCurrentVirologist().getField() != null) {
			if (Game.getCurrentVirologist().isStunned())
				moveBtn.setEnabled(false);
			if (Game.getCurrentVirologist().getField().getVirologists().stream().noneMatch(it -> it != Game.getCurrentVirologist() && it.isStunned()))
				stealBtn.setEnabled(false);
			if (Game.getCurrentVirologist().getInventory().getSuites().stream().noneMatch(it -> it instanceof Axe && it.isActive() && !((Axe) it).isUsed()))
				attackBtn.setEnabled(false);
			if (Game.getCurrentVirologist().getInventory().getAgents().isEmpty())
				useAgentBtn.setEnabled(false);
				makeAgentBtn.setEnabled(false);
			if (Game.getCurrentVirologist().getInventory().getSuites().stream().noneMatch(Suite::isActive) || Game.getCurrentVirologist().getInventory().getSuites().stream().noneMatch(it -> !it.isActive()))
				switchSuiteBtn.setEnabled(false);
			if (Game.getCurrentVirologist().getInventory().getSuites().stream().noneMatch(it -> !it.isActive()))
				activateSuiteBtn.setEnabled(false);
			//if (Game.getCurrentVirologist().getLearnt().stream().noneMatch(it -> ))
				//makeAgentBtn.setEnabled(false);
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
