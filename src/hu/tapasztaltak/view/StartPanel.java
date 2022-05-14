package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StartPanel extends JPanel implements ActionListener {
    Game game;
    private JLabel playerNameLabel = new JLabel("Név");
    private JTextArea playerNameInput = new JTextArea();
    private JLabel playerColorLabel = new JLabel("Szín");
    private JOptionPane playerColorInput = new JOptionPane();
    private JPanel middlePanel = new JPanel();
    private JPanel virPanel = new JPanel();
    private JButton newGameBtn = new JButton("Játék indítása");
    private JButton addVirBtn = new JButton("Hozzáad");
    private JLabel messageLabel = new JLabel();

    private DefaultListModel listModel = new DefaultListModel();
    private JList list;

    JTextField name = new JTextField();

    transient private BufferedImage backGround;

    public StartPanel (Game game) {
        super();
        this.game = game;
        try {
            backGround = ImageIO.read(new File("src/hu/tapasztaltak/textures/menu/menuHatter.png"));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        setLayout(new BorderLayout());
        Component rigidArea1 = Box. createRigidArea(new Dimension(400, 1080));
        Component rigidArea2 = Box. createRigidArea(new Dimension(400, 1080));
        add(rigidArea1,BorderLayout.EAST);
        add(rigidArea2,BorderLayout.WEST);
        add(middlePanel,BorderLayout.CENTER);
        initComponents();
    }

    private void initComponents() {
        middlePanel.setBackground(new Color(125, 220, 191));
        middlePanel.setLayout(new GridLayout(3,1));

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel,BoxLayout.Y_AXIS));
        upperPanel.add(new JLabel("Játék beállítások"));
        upperPanel.add(messageLabel);
        upperPanel.setBackground(new Color(125, 220, 191));
        middlePanel.add(upperPanel);

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3,2));
        p.add(playerNameLabel);
        p.add(playerNameInput);
        p.add(playerColorLabel);
        p.add(Box.createRigidArea(new Dimension(40,40)));//TODO dropdown
        addVirBtn.addActionListener(this);
        for (int i = 0; i < game.getVirologistList().size(); i++){
            listModel.addElement(game.getVirologistList().get(i));
        }
        p.add(addVirBtn);
        newGameBtn.addActionListener(evt -> {
            if(listModel.getSize() < 2){
                messageLabel.setText(String.format("Legalább még %d virológust adj hozzá!",(2 - listModel.getSize())));
            }
            else {
                game.showGame();
            }
        });
        p.add(newGameBtn);
        p.setBackground(new Color(125, 220, 191));
        middlePanel.add(p);


        JPanel lowerPanel = new JPanel();
        lowerPanel.setBackground(new Color(125, 220, 191));
        middlePanel.add(lowerPanel);

    }

    public void actionPerformed(ActionEvent e) {
        if(listModel.getSize() == 6){
            messageLabel.setText("Elérte a maximális létszámot!");
        }
        else{
            String s1 = name.getText();
            game.setVirologistList(s1);
            listModel.addElement(s1);
            revalidate();
        }

    }

    /**
     * Egységes gomb beállítások
     * @param button amin a beállításokat elvégzi
     */
    private void setButtonSettings(JButton button) {
        button.setBackground(new Color(125, 220, 191));
        button.setForeground(new Color(208, 253, 239));
        button.setFont(new Font("Berlin Sans FB Demi",Font.PLAIN,30));
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setBorder(new BevelBorder(1,((new Color(75, 141, 124))),new Color(208, 253, 239)));
    }

    /**
     * Override-olja a paint komponentet, ezert tudjuk kirajzolni a hatteret
     * @param g graphics amin van a hatterkep
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGround.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
    }
}
