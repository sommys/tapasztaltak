package hu.tapasztaltak.view;

import hu.tapasztaltak.model.*;
import hu.tapasztaltak.proto.ProtoMain;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static hu.tapasztaltak.model.Game.addView;

public class QuestionPanel extends JPanel {
	private final Game game;
	public final JButton yesBtn = new JButton("igen");
	private final JButton noBtn = new JButton("nem");
	JTextField textField = new JTextField() {
		@Override
		public void setBorder(Border border) {}
	};

	public QuestionPanel(Game game){
		super();
		setFocusable(true);
		setBackground(new Color(102, 180, 156));
		//setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		this.game = game;

		textField.setBackground(new Color(102, 180, 156));
		textField.setFont(new Font("Berlin Sans FB Demi",Font.PLAIN,30));
		textField.setForeground(new Color(36, 140, 130));
		textField.setEditable(false);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		add(textField);
	}

	public boolean yesNoQuestion(String question, AtomicBoolean result) {
		textField.setText(question);
		add(textField);
		yesBtn.setFont(new Font("Berlin Sans FB Demi",Font.PLAIN,30));
		yesBtn.setBackground(new Color(36, 140, 130));
		yesBtn.setForeground(new Color(26, 98, 90));
		yesBtn.addActionListener(evt -> {
			removeAll();
			revalidate();
			result.set(true);
			synchronized (yesBtn){
				yesBtn.notify();
			}
		});

		noBtn.setFont(new Font("Berlin Sans FB Demi",Font.PLAIN,30));
		noBtn.setBackground(new Color(36, 140, 130));
		noBtn.setForeground(new Color(26, 98, 90));
		noBtn.addActionListener(evt -> {
			removeAll();
			revalidate();
			synchronized (yesBtn){
				yesBtn.notify();
			}
		});

		JPanel buttons = new JPanel();
		buttons.setBackground(new Color(102, 180, 156));
		buttons.add(yesBtn);
		buttons.add(noBtn);
		add(buttons);
		revalidate();
		return result.get();
	}

	//SUITE_SWITCH_FROM, SUITE_SWITCH_TO, FIELD_PICKUP_SUITE, FIELD_PICKUP_MATERIAL

	public void moveQuestion(List<FieldView> neighbours){
		removeAll();
		revalidate();
		setLayout(new GridBagLayout());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		buttons.setBackground(new Color(102, 180, 156));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int xCounter = 0;
		int yCounter = 0;

		for(FieldView fv : neighbours){
			JButton btn = new JButton(""+fv.getFieldIdx());
			btn.setBackground(new Color(36, 140, 130));
			btn.addActionListener(evt -> {
				Game.getCurrentVirologist().move(fv.getField());
				removeAll();
				revalidate();
				Game.getInstance().updatePanels();
			});

			c.gridx = xCounter;
			c.gridy = yCounter;
			c.weightx = 0.2;

			buttons.add(btn, c);
			c.weightx = 0;

			xCounter++;
			if (xCounter % 2 == 0) {
				xCounter = 0;
				yCounter++;
			}

		}
		setPanelAndStuff(c, buttons, "Hova-hova?");
	}

	public void stealPickVirologistQuestion(List<VirologistView> victims){
		removeAll();
		revalidate();
		setLayout(new GridBagLayout());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		buttons.setBackground(new Color(102, 180, 156));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int xCounter = 0;
		int yCounter = 0;

		for(VirologistView vw : victims){
			JButton btn = new JButton(new ImageIcon(vw.virImg.getScaledInstance(vw.virImg.getWidth(), vw.virImg.getHeight(), Image.SCALE_SMOOTH)));
			btn.setBackground(new Color(36, 140, 130));
			btn.addActionListener(evt -> {
				removeAll();
				revalidate();
				Game.getInstance().updatePanels();
				stealPickItemQuestion(vw.getVir());
			});

			c.gridx = xCounter;
			c.gridy = yCounter;
			c.weightx = 0.2;

			buttons.add(btn, c);
			c.weightx = 0;

			xCounter++;
			if (xCounter % 3 == 0) {
				xCounter = 0;
				yCounter++;
			}
		}
		setPanelAndStuff(c, buttons, "Kitöl lopnál?");
	}

	public void stealPickItemQuestion(Virologist stolenFrom){
		removeAll();
		revalidate();
		VirologistView victim = (VirologistView) Game.objectViewHashMap.get(stolenFrom);
		setLayout(new GridBagLayout());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		buttons.setBackground(new Color(102, 180, 156));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int xCounter = 0;
		int yCounter = 0;

		List<IStealable> stealables = new ArrayList<>();
		stealables.addAll(victim.getVir().getInventory().getSuites());
		stealables.addAll(victim.getVir().getInventory().getMaterials());
		for(IStealable s : stealables){
			JButton btn = new JButton(((ItemView)Game.objectViewHashMap.get(s)).getItemImage());
			btn.setBackground(new Color(36, 140, 130));
			btn.addActionListener(evt -> {
				stolenFrom.stolen(Game.getCurrentVirologist(), s);
				removeAll();
				revalidate();
				Game.getInstance().updatePanels();
			});

			c.gridx = xCounter;
			c.gridy = yCounter;
			c.weightx = 0.2;

			buttons.add(btn, c);
			c.weightx = 0;

			xCounter++;
			if (xCounter % 2 == 0) {
				xCounter = 0;
				yCounter++;
			}
		}
		setPanelAndStuff(c, buttons, "Na és mit?");
	}

	public void attackQuestion(List<VirologistView> possibleVictims){
		removeAll();
		revalidate();
		setLayout(new GridBagLayout());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		buttons.setBackground(new Color(102, 180, 156));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int xCounter = 0;
		int yCounter = 0;

		for(VirologistView vw : possibleVictims){
			JButton btn = new JButton(new ImageIcon(vw.virImg.getScaledInstance(vw.virImg.getWidth(), vw.virImg.getHeight(), Image.SCALE_SMOOTH)));
			btn.setBackground(new Color(36, 140, 130));
			btn.addActionListener(evt -> {
				removeAll();
				revalidate();
				Axe a = (Axe)Game.getCurrentVirologist().getInventory().getSuites().stream().filter(it -> it.isActive() && it instanceof Axe).collect(Collectors.toList()).get(0);
				Game.getCurrentVirologist().attack(a, vw.getVir());
				Game.getInstance().updatePanels();
			});

			c.gridx = xCounter;
			c.gridy = yCounter;
			c.weightx = 0.2;

			buttons.add(btn, c);
			c.weightx = 0;

			xCounter++;
			if (xCounter % 3 == 0) {
				xCounter = 0;
				yCounter++;
			}
		}
		setPanelAndStuff(c, buttons, "Kit gyilok?");
	}

	public void pickAgentUseAgentQuestion(){
		removeAll();
		revalidate();
		setLayout(new GridBagLayout());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		buttons.setBackground(new Color(102, 180, 156));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int xCounter = 0;
		int yCounter = 0;

		for(Agent a : Game.getCurrentVirologist().getInventory().getAgents()){
			AgentView av = (AgentView) Game.objectViewHashMap.get(a);
			JButton btn = new JButton(av.icon);
			btn.setBackground(new Color(36, 140, 130));
			btn.addActionListener(evt -> {
				removeAll();
				revalidate();
				pickAgentUseVirologistQuestion(av);
				Game.getInstance().updatePanels();
			});

			c.gridx = xCounter;
			c.gridy = yCounter;
			c.weightx = 0.2;

			buttons.add(btn, c);
			c.weightx = 0;

			xCounter++;
			if (xCounter % 2 == 0) {
				xCounter = 0;
				yCounter++;
			}
		}
		setPanelAndStuff(c, buttons, "Melyiket?");
	}

	private void pickAgentUseVirologistQuestion(AgentView av) {
		removeAll();
		revalidate();
		setLayout(new GridBagLayout());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		buttons.setBackground(new Color(102, 180, 156));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int xCounter = 0;
		int yCounter = 0;

		for(Virologist v : Game.getCurrentVirologist().getField().getVirologists()){
			VirologistView vw = (VirologistView)Game.objectViewHashMap.get(v);
			JButton btn = new JButton(new ImageIcon(vw.virImg.getScaledInstance(vw.virImg.getWidth(), vw.virImg.getHeight(), Image.SCALE_SMOOTH)));
			btn.setBackground(new Color(36, 140, 130));
			btn.addActionListener(evt -> {
				removeAll();
				revalidate();
				Game.getCurrentVirologist().useAgent(av.a, vw.getVir());
				Game.getInstance().updatePanels();
			});

			c.gridx = xCounter;
			c.gridy = yCounter;
			c.weightx = 0.2;

			buttons.add(btn, c);
			c.weightx = 0;

			xCounter++;
			if (xCounter % 3 == 0) {
				xCounter = 0;
				yCounter++;
			}
		}
		setPanelAndStuff(c, buttons, "Melyiket?");
	}

	public void pickAgentCraftQuestion(){
		removeAll();
		revalidate();
		setLayout(new GridBagLayout());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		buttons.setBackground(new Color(102, 180, 156));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int xCounter = 0;
		int yCounter = 0;

		for(Gene g : ProtoMain.genes){
			JButton btn = new JButton(g.getAgent().getClass().getSimpleName());
			btn.setBackground(new Color(36, 140, 130));
			btn.addActionListener(evt -> {
				removeAll();
				revalidate();
				if(Game.getCurrentVirologist().getLearnt().contains(g)){
					Game.getCurrentVirologist().makeAgent(g);
				} else {
					ButtonsPanel.activeCounter--;
					JOptionPane.showMessageDialog(this,"Előbb meg kéne tanulni haver...", "Nana", JOptionPane.WARNING_MESSAGE);
				}
				Game.getInstance().updatePanels();
			});

			c.gridx = xCounter;
			c.gridy = yCounter;
			c.weightx = 0.2;

			buttons.add(btn, c);
			c.weightx = 0;

			xCounter++;
			if (xCounter % 2 == 0) {
				xCounter = 0;
				yCounter++;
			}
		}
		setPanelAndStuff(c, buttons, "Melyiket?");
	}

	public void putOnSuiteQuestion(){
		removeAll();
		revalidate();
		setLayout(new GridBagLayout());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		buttons.setBackground(new Color(102, 180, 156));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int xCounter = 0;
		int yCounter = 0;

		for(Suite s : Game.getCurrentVirologist().getInventory().getSuites().stream().filter(it -> !it.isActive()).collect(Collectors.toList())){
			JButton btn = new JButton(((SuiteView)Game.objectViewHashMap.get(s)).icon);
			btn.setBackground(new Color(36, 140, 130));
			btn.addActionListener(evt -> {
				removeAll();
				revalidate();
				Game.getCurrentVirologist().putOnSuite(s);
				Game.getInstance().updatePanels();
			});

			c.gridx = xCounter;
			c.gridy = yCounter;
			c.weightx = 0.2;

			buttons.add(btn, c);
			c.weightx = 0;

			xCounter++;
			if (xCounter % 2 == 0) {
				xCounter = 0;
				yCounter++;
			}
		}
		setPanelAndStuff(c, buttons, "Melyiket?");
	}

	public void switchSuiteFromQuestion(){
		setLayout(new GridBagLayout());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		buttons.setBackground(new Color(102, 180, 156));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int xCounter = 0;
		int yCounter = 0;
		for(Suite s : Game.getCurrentVirologist().getInventory().getSuites().stream().filter(Suite::isActive).collect(Collectors.toList())){
			JButton btn = new JButton(((SuiteView)Game.objectViewHashMap.get(s)).icon);
			btn.setBackground(new Color(36, 140, 130));
			btn.addActionListener(evt -> {
				removeAll();
				revalidate();
				switchSuiteToQuestion(s);
				Game.getInstance().updatePanels();
			});

			c.gridx = xCounter;
			c.gridy = yCounter;
			c.weightx = 0.2;

			buttons.add(btn, c);
			c.weightx = 0;

			xCounter++;
			if (xCounter % 2 == 0) {
				xCounter = 0;
				yCounter++;
			}
		}
		setPanelAndStuff(c, buttons, "Melyiket?");
	}

	public void pickUpStuffFromShelterQuestion(Shelter sh){
		removeAll();
		revalidate();
		setLayout(new GridBagLayout());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		buttons.setBackground(new Color(102, 180, 156));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int xCounter = 0;
		int yCounter = 0;
		Inventory inv = Game.getCurrentVirologist().getInventory();
		JButton btn = new JButton(((ItemView)Game.objectViewHashMap.get(sh.getSuite())).icon);
		btn.setBackground(new Color(36, 140, 130));
		btn.addActionListener(evt -> {
			if(inv.getSize() == inv.getUsedSize()){
				JOptionPane.showMessageDialog(this,"Már tele a tárhely...", "Nana", JOptionPane.WARNING_MESSAGE);
			} else {
				sh.getSuite().add(inv);
				sh.setSuite(null);
				buttons.remove(btn);
			}
			Game.getInstance().updatePanels();
		});

		c.gridx = xCounter;
		c.gridy = yCounter;
		c.weightx = 0.2;

		buttons.add(btn, c);

		JButton doneBtn = new JButton("Kész vagyok");
		doneBtn.setBackground(new Color(36, 140, 130));
		doneBtn.addActionListener(evt -> {
			sh.endPickup();
			removeAll();
			revalidate();
			Game.getInstance().updatePanels();
		});
		yCounter++;
		c.gridy=yCounter;
		c.weightx=0.2;
		buttons.add(doneBtn, c);
		setPanelAndStuff(c, buttons, "Viszed?");
	}

	public void pickUpStuffFromWarehouseQuestion(Warehouse w){
		removeAll();
		revalidate();
		setLayout(new GridBagLayout());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		buttons.setBackground(new Color(102, 180, 156));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int xCounter = 0;
		int yCounter = 0;
		Inventory inv = Game.getCurrentVirologist().getInventory();
		for(IMaterial m : w.getMaterials()){
			JButton btn = new JButton(((MaterialView)Game.objectViewHashMap.get(m)).icon);
			btn.setBackground(new Color(36, 140, 130));
			btn.addActionListener(evt -> {
				if(inv.getSize() == inv.getUsedSize()){
					JOptionPane.showMessageDialog(this,"Már tele a tárhely...", "Nana", JOptionPane.WARNING_MESSAGE);
				} else {
					m.add(inv);
					w.removeMaterials(m);
					buttons.remove(btn);
				}
				Game.getInstance().updatePanels();
			});

			c.gridx = xCounter;
			c.gridy = yCounter;
			c.weightx = 0.2;

			buttons.add(btn, c);
			c.weightx = 0;

			xCounter++;
			if (xCounter % 2 == 0) {
				xCounter = 0;
				yCounter++;
			}
		}
		JButton doneBtn = new JButton("Kész vagyok");
		doneBtn.setBackground(new Color(36, 140, 130));
		doneBtn.addActionListener(evt -> {
			w.endPickup();
			removeAll();
			revalidate();
			Game.getInstance().updatePanels();
		});
		yCounter++;
		c.gridy=yCounter;
		c.weightx=0.2;
		buttons.add(doneBtn, c);
		setPanelAndStuff(c, buttons, "Viszed?");
	}

	private void switchSuiteToQuestion(Suite from){
		removeAll();
		revalidate();
		setLayout(new GridBagLayout());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		buttons.setBackground(new Color(102, 180, 156));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int xCounter = 0;
		int yCounter = 0;

		for(Suite s : Game.getCurrentVirologist().getInventory().getSuites().stream().filter(it -> !it.isActive()).collect(Collectors.toList())){
			JButton btn = new JButton(((SuiteView)Game.objectViewHashMap.get(s)).icon);
			btn.setBackground(new Color(36, 140, 130));
			btn.addActionListener(evt -> {
				removeAll();
				revalidate();
				Game.getCurrentVirologist().switchSuite(from, s);
				Game.getCurrentVirologistView().update();
				Game.getInstance().updatePanels();
			});

			c.gridx = xCounter;
			c.gridy = yCounter;
			c.weightx = 0.2;

			buttons.add(btn, c);
			c.weightx = 0;

			xCounter++;
			if (xCounter % 2 == 0) {
				xCounter = 0;
				yCounter++;
			}
		}
		setPanelAndStuff(c, buttons, "Melyiket?");
	}

	public void selectQuestion(String question, List<Object> objectList) {

		setLayout(new GridBagLayout());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		buttons.setBackground(new Color(102, 180, 156));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int xCounter = 0;
		int yCounter = 0;


		for(Object o : objectList) {
			addView(o, new NucleotidView(new Nucleotid()));
			ItemView i = (ItemView) Game.objectViewHashMap.get(o);
			JButton btn = new JButton(i.getItemImage());
			btn.setBackground(new Color(36, 140, 130));
			btn.addActionListener(evt -> {
				removeAll();
				revalidate();
			});
			c.gridx = xCounter;
			c.gridy = yCounter;
			c.weightx = 0.2;

			buttons.add(btn, c);
			c.weightx = 0;

			xCounter++;
			if (xCounter % 3 == 0) {
				xCounter = 0;
				yCounter++;
			}
		}
		JScrollPane scrollPane = new JScrollPane(buttons, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setMinimumSize(new Dimension(50, 500));
		textField.setText(question);


		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		add(textField, c);

		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 1;
		add(scrollPane, c);

		revalidate();
	}

	public void setPanelAndStuff(GridBagConstraints c, JPanel buttons, String question){
		JScrollPane scrollPane = new JScrollPane(buttons, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setMinimumSize(new Dimension(50, 500));
		scrollPane.getVerticalScrollBar().setBackground(new Color(102, 180, 156));
		scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = new Color(154, 231, 205);
			}
		});
		textField.setText(question);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		add(textField, c);

		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 1;
		add(scrollPane, c);
		revalidate();
	}
}
