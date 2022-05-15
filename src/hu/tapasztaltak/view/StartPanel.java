package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Game;
import hu.tapasztaltak.model.Virologist;

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
    private JLabel playerNameLabel = new JLabel("Név: ");
    private JTextArea playerNameInput = new JTextArea();
    private JLabel playerColorLabel = new JLabel("Szín: ");
    ArrayList<String> usedColors = new ArrayList<>();
    String[] colors = {"kék","lila","rózsaszín","piros","sárga","zöld"};
    private JComboBox<String> playerColorInput = new JComboBox<String>(colors);
    private JPanel middlePanel = new JPanel();
    private JPanel virPanel = new JPanel();
    private JButton newGameBtn = new JButton("Játék indítása");
    private JButton addVirBtn = new JButton("Hozzáad");
    private JLabel messageLabel = new JLabel();
    private JPanel lowerPanel;
    private Color bgColor = new Color(125, 220, 191);

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
        middlePanel.setBackground(bgColor);
        middlePanel.setLayout(new GridLayout(3,1));
        middlePanel.setBorder(BorderFactory.createLineBorder(new Color(75, 141, 124),3));

        JLabel gameSetLabel = new JLabel("Játék beállítások");
        setLabelSettings(gameSetLabel);
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new GridLayout(2,1));
        upperPanel.add(gameSetLabel);
        setLabelSettings(messageLabel);
        upperPanel.add(messageLabel);
        upperPanel.setBackground(bgColor);
        middlePanel.add(upperPanel);

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3,2));
        setLabelSettings(playerNameLabel);
        setLabelSettings(playerColorLabel);
        p.add(playerNameLabel);
        playerNameInput.setFont(new Font("Berlin Sans FB Demi",Font.PLAIN,30));
        playerNameInput.setBackground(new Color(166, 241, 218));
        playerNameInput.setBorder(new BevelBorder(1,((new Color(75, 141, 124))),new Color(208, 253, 239)));
        p.add(playerNameInput);
        p.add(playerColorLabel);
        playerColorInput.setBorder(new BevelBorder(1,((new Color(75, 141, 124))),new Color(208, 253, 239)));
        playerColorInput.setBackground(new Color(166, 241, 218));
        playerColorInput.setFont(new Font("Berlin Sans FB Demi",Font.PLAIN,30));
        p.add(playerColorInput);
        addVirBtn.addActionListener(this);
        setButtonSettings(addVirBtn);
        p.add(addVirBtn);
        setButtonSettings(newGameBtn);
        newGameBtn.addActionListener(evt -> {
            if(game.getVirologists().size() < 2){
                messageLabel.setText(String.format("Legalább még %d virológust adj hozzá!",(2 - game.getVirologists().size())));
            }
            else {
                game.showGame();
            }
        });
        p.add(newGameBtn);
        p.setBackground(bgColor);
        middlePanel.add(p);


        lowerPanel = new JPanel();
        lowerPanel.setBackground(bgColor);
        lowerPanel.setLayout(new GridLayout(1,6));
        middlePanel.add(lowerPanel);

    }

    public void actionPerformed(ActionEvent e) {
        if(playerNameInput.getText().isEmpty()){
            messageLabel.setText("Adj meg egy nevet a virológus létrehozásához");
        } else {
            if (game.getVirologists().size() == 6) {
                messageLabel.setText("Elérte a maximális létszámot!");
            } else if (!usedColors.contains(playerColorInput.getSelectedItem().toString())){
                usedColors.add(playerColorInput.getSelectedItem().toString());
                String s1 = name.getText();
                Virologist newVir = new Virologist();
                VirologistView newVirView = new VirologistView(newVir,playerColorInput.getSelectedItem().toString(),playerNameInput.getText());
                if(game.getVirologists().size() == 0){
                    game.setCurrentVirologist(newVir);
                }
                Game.objectViewHashMap.put(newVir,newVirView);
                game.addVirologist(newVir);
                Image bi = newVirView.getVirImg().getScaledInstance(192,240,Image.SCALE_SMOOTH);
                JLabel virPic = new JLabel(new ImageIcon(bi));
                virPic.setPreferredSize(new Dimension(352,440));
                JPanel addedPanel = new JPanel();
                addedPanel.setLayout(new BorderLayout());
                JLabel newName = new JLabel(playerNameInput.getText());
                setLabelSettings(newName);
                newVirView.setTextColortoVir(newName);
                addedPanel.add(virPic, BorderLayout.CENTER);
                addedPanel.add(newName, BorderLayout.NORTH);
                addedPanel.setBackground(bgColor);
                lowerPanel.add(addedPanel);
                revalidate();
            } else {
                messageLabel.setText("Ez a szín már használatban van, válassz másikat!");
            }
        }
    }

    /**
     * Egységes gomb beállítások
     * @param button amin a beállításokat elvégzi
     */
    private void setButtonSettings(JButton button) {
        button.setBackground(new Color(102, 180, 156));
        button.setForeground(new Color(208, 253, 239));
        button.setFont(new Font("Berlin Sans FB Demi",Font.PLAIN,30));
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setBorder(new BevelBorder(1,((new Color(75, 141, 124))),new Color(208, 253, 239)));
    }
    private void setLabelSettings(JLabel label){
        label.setFont(new Font("Berlin Sans FB Demi",Font.PLAIN,50));
        label.setForeground(new Color(208, 253, 239));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(new BevelBorder(1,((new Color(75, 141, 124))),new Color(208, 253, 239)));
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
