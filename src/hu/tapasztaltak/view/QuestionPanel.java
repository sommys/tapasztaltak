package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Virologist;

import javax.swing.*;
import java.awt.*;

public class QuestionPanel extends JPanel {
	private String question;
	private JList chooseList;
	private JButton yesBtn;
	private JButton noBtn;
	private JButton approveBtn;
	private Object selected;

	public QuestionPanel(){
		super();
		setFocusable(true);
		setBackground(new Color(102, 180, 156));
	}
}
