package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

public class QuestionPanel extends JPanel {
	private final Game game;
	private final JButton yesBtn = new JButton("igen");
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

	public void yesNoQuestion(String question) {
		textField.setText(question);
		add(textField);

		yesBtn.setFont(new Font("Berlin Sans FB Demi",Font.PLAIN,30));
		yesBtn.setBackground(new Color(36, 140, 130));
		yesBtn.setForeground(new Color(26, 98, 90));
		yesBtn.addActionListener(evt -> {
			removeAll();
			revalidate();
			// Todo: set result true
		});

		noBtn.setFont(new Font("Berlin Sans FB Demi",Font.PLAIN,30));
		noBtn.setBackground(new Color(36, 140, 130));
		noBtn.setForeground(new Color(26, 98, 90));
		noBtn.addActionListener(evt -> {
			removeAll();
			revalidate();
			// Todo: set result false
		});

		JPanel buttons = new JPanel();
		buttons.setBackground(new Color(102, 180, 156));
		buttons.add(yesBtn);
		buttons.add(noBtn);
		add(buttons);
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
			Game.addView(o, new NucleotidView());
			ItemView i = (ItemView) Game.objectViewHashMap.get(o);
			JButton btn = new JButton(i.getItemImage());
			btn.setBackground(new Color(36, 140, 130));
			btn.addActionListener(evt -> {
				removeAll();
				revalidate();
				// Todo: eredmény visszaadása
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
}
