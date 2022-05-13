package hu.tapasztaltak.view;

import hu.tapasztaltak.model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StartPanel extends JPanel implements ActionListener {
    Game game;
    private JLabel playerName = new JLabel("Név");
    private JLabel playerColor = new JLabel("Szín");

    private JButton newGameBtn = new JButton();
    private JButton b1 = new JButton();

    private DefaultListModel listModel = new DefaultListModel();
    private JList list;

    JTextField name;

    transient private BufferedImage backGround;

    public StartPanel (Game game) {
        //Régi menubol loptam
        super();
        this.game = game;
        try {
            backGround = ImageIO.read(new File("src/hu/tapasztaltak/textures/menu/menuHatter.png"));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        initComponents();
    }

    private void initComponents() {
        //Valaki ert a java layoutokhoz?
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        playerName.setFont(new Font("Arial",Font.BOLD,40));
        playerName.setBorder(BorderFactory.createEmptyBorder(10, 35, 10, 10));
        playerName.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(playerName);

        playerColor.setFont(new Font("Arial",Font.BOLD,40));
        playerColor.setBorder(BorderFactory.createEmptyBorder(10, 35, 10, 10));
        playerColor.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(playerColor);

        setButtonSettings(newGameBtn);
        newGameBtn.setText("Játék indítása");
        newGameBtn.addActionListener(evt -> game.showGame()); //TODO Generálni virológusokat, első következő virológust beállítani game.CurrentVirologistnak
        add(newGameBtn);

        name = new JTextField("Virológus neve");
        //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA nem mukodiiiiiiiiiik
        //Update: mukodik xd
        name.setMaximumSize( name.getPreferredSize() );
        add(name);

        b1 = new JButton("Hozzáad");
        b1.addActionListener(this);
        add(b1);

        for (int i = 0; i < game.getVirologistList().size(); i++){
            listModel.addElement(game.getVirologistList().get(i));
        }

        list = new JList(listModel);

        add(list);

        add(Box.createVerticalGlue());
    }

    public void actionPerformed(ActionEvent e) {
        String s1 = name.getText();
        game.setVirologistList(s1);
        listModel.addElement(s1);
        revalidate();
    }

    /**
     * Egységes gomb beállítások
     * @param button amin a beállításokat elvégzi
     */
    private void setButtonSettings(JButton button) {
        button.setFont(new Font("Arial",Font.BOLD,20));
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
