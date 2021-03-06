package hu.tapasztaltak.model;


import hu.tapasztaltak.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * A játékot reprezentáló singleton osztály.
 */
public class Game extends JFrame {
    public static HashMap<Object, View> objectViewHashMap = new HashMap<>();
    /** ablak felbontasa*/
    public static int WINDOW_WIDTH = 1920;
    public static int WINDOW_HEIGHT = 1080;
    private static Virologist currentVirologist = null;
    public static Gene[] genes = {new Gene(), new Gene(), new Gene(), new Gene()};
    public static boolean running = true;

    JPanel gamePanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JPanel leftPanel = new JPanel();

    AgentPanel agentPanel;
    ButtonsPanel buttonsPanel;
    InventoryPanel inventoryPanel;
    MapPanel mapPanel;
    MenuPanel menuPanel = new MenuPanel(this);
    QuestionPanel questionPanel = new QuestionPanel(this);
    StartPanel startPanel = new StartPanel(this);

    ArrayList<Virologist> virologists = new ArrayList<>();

    /**
     * Privát konstruktor, a singleton elvárásainak megfelelően.
     */
    private Game() {
        /** ablak inicializalasa */
        Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
        if (resolution.getWidth() < 1920 || resolution.getHeight() < 1080) {
            JOptionPane.showMessageDialog(this, "Túl alacsony felbontás!", "Hiba", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } else if (resolution.getWidth() == 1920 && resolution.getHeight() == 1080) {
            setExtendedState(Frame.MAXIMIZED_BOTH);
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setTitle("Virologusos jatek");
        setLayout(new BorderLayout());
        setVisible(true);

        /** panelek lathatosaganak beallitasa */
        menuPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        menuPanel.setVisible(true);
        setContentPane(menuPanel);
        menuPanel.grabFocus();

        genes[0].setAgent(new Dance());
        genes[1].setAgent(new Forget());
        genes[2].setAgent(new Protect());
        genes[3].setAgent(new Stun());
    }


    /**
     * Játék kirajzolása
     */
    public void showGame() {
        buttonsPanel = new ButtonsPanel();
        agentPanel = new AgentPanel();
        inventoryPanel = new InventoryPanel();
        mapPanel = new MapPanel();
        menuPanel.setVisible(false);
        buttonsPanel.setVisible(true);
        questionPanel.setVisible(true);
        agentPanel.setVisible(true);
        inventoryPanel.setVisible(true);
        mapPanel.setVisible(true);
        //ActivePanel scrollable? - KURVARA NEM :((
        JScrollPane scrollable = new JScrollPane(agentPanel);
        scrollable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollable);

        GridBagLayout gl = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        leftPanel.setLayout(gl);
        rightPanel.setLayout(gl);
        gamePanel.setLayout(gl);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;

        /**Left panel kialakítása */
        c.weighty = 0.86;
        c.gridy = 0;
        leftPanel.add(mapPanel, c);

        c.weighty = 0.07;
        c.gridy = 1;
        leftPanel.add(inventoryPanel, c);

        c.gridy = 2;
        leftPanel.add(agentPanel, c);

        /**Right panel kialakítása */
        c.weighty = 0.3;
        c.gridy = 0;
        rightPanel.add(buttonsPanel, c);

        c.weighty = 0.7;
        c.gridy = 1;
        rightPanel.add(questionPanel, c);

        /** Left és Right panelek elhelyezése vízszintesen */
        c.weighty = 1;
        c.weightx = 0.865;
        c.gridx = 0;
        gamePanel.add(leftPanel,c);
        c.weightx = 0.135;
        c.gridx = 1;
        gamePanel.add(rightPanel,c);
        gamePanel.setVisible(true);

        setContentPane(gamePanel);
        gamePanel.grabFocus();
        List<FieldView> flist = mapPanel.getFields().stream().filter(it -> it.getFieldNum() != 0 &&!(it instanceof InfLaborView)).collect(Collectors.toList());
        for(int i = 0; i < virologists.size(); i++){
            Virologist v = virologists.get(i);
            Random r = new Random();
            FieldView fview =flist.get(r.nextInt(flist.size()));
            Field f =fview.getField();
            fview.setVisited(true);
            v.setField(f);
            f.addVirologist(v);
        }

        updatePanels();

        revalidate();
    }

    public void startGameSettings(){
        startPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        startPanel.setVisible(true);
        setContentPane(startPanel);
        startPanel.grabFocus();
        revalidate();
    }
    /**
     * Az egyetlen Game példány.
     */
    private static Game instance = null;
    /**
     * Győzelemhez szükséges genetikai kódok száma.
     */
    final int maxAgent = 4;
    /**
     * A játék mezőinek listája.
     */
    private List<Field> fields = new ArrayList<>();

    /**
     * Hozzáférés a singleton példányhoz.
     *
     * @return a {@link Game} példánya.
     */
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public static void main(String[] args){
        instance = new Game();
    }

    /**
     * Elindítja a játékot, létrehozza a pályát és a további szereplőket.
     */
    public void startGame(int fieldNum, int laborNum, int warehouseNum, int shelterNum, int virologistNum) {
        RoundManager rm = RoundManager.getInstance();
        for(int i = 1; i <= fieldNum; i++){
            Field f = new Field();
            fields.add(f);
            rm.addSteppable(f);
        }
        for(int i = 1; i <= laborNum; i++){
            Labor l= new Labor();
            fields.add(l);
            rm.addSteppable(l);
        }
        for(int i = 1; i <= warehouseNum; i++){
            Warehouse w = new Warehouse();
            fields.add(w);
            rm.addSteppable(w);
        }
        for(int i = 1; i <= shelterNum; i++){
            Shelter s = new Shelter();
            fields.add(s);
            rm.addSteppable(s);
        }
    }

    public void updatePanels(){
        agentPanel.update();
        buttonsPanel.update();
        inventoryPanel.update();
        mapPanel.update();
        //menuPanel.update(); //ez kell?
        //questionPanel.update(); //ez kell?
        //startPanel.update(); //ez kell?
    }

    /**
     * Győzelem esetén nyertest hirdet és leállítja a játékot.
     *
     * @param v a potenciális győztes {@link Virologist}
     */
    public void checkEndGame(Virologist v) {
        if(!running) return;
        if(RoundManager.getInstance().getVirologists().stream().allMatch(it -> it.getModifiers().stream().anyMatch(m -> m instanceof Bear))){
            running = false;
            JOptionPane.showMessageDialog(this,"Mindenki maci lett :(( brumm", "MedveGang rise up", JOptionPane.WARNING_MESSAGE);
            buttonsPanel.disableallBtn();
            return;
        }
        if (v.getLearnt().size() == maxAgent) {
            running = false;
            JOptionPane.showMessageDialog(this,Game.getCurrentVirologistView().getPlayerName()+" játékos nyert!!! GGWP", "VAN EGY NYERTES", JOptionPane.WARNING_MESSAGE);
        }
    }

    //region GETTEREK és SETTEREK

    /**
     * Visszaadja a játékban lévő különböző ágensek számát.
     *
     * @return a játékban gyűjtehtő különböző ágensek száma.
     */
    public int getMaxAgent(){ return maxAgent; }

    /**
     * Visszaadja a játék mezőinek listáját.
     *
     * @return a játék {@link Field} listája.
     */
    public List<Field> getFields() { return fields; }

    /**
     * Beállítja a játék mezőinek listáját.
     *
     * @param fields a játék {@link Field} listája.
     */
    public void setFields(List<Field> fields) { this.fields = fields; }

    /**
     * Hozzáadja {@code field}-et a játék mezőinek listájához.
     *
     * @param field a hozzáadandó {@link Field}
     */
    public void addField(Field field) { fields.add(field); RoundManager.getInstance().addSteppable(field); }

    /**
     * Törli {@code field}-et a játék mezőinek listájából.
     *
     * @param field a törlendő {@link Field}
     */
    public void removeField(Field field) { fields.remove(field); RoundManager.getInstance().removeSteppable(field); }

    public void setCurrentVirologist(Virologist virologist) {
        currentVirologist = virologist;
        if(currentVirologist.getModifiers().stream().anyMatch(it -> it instanceof Bear)){
            currentVirologist.step();
        }
    }

    public static Virologist getCurrentVirologist() {
        return currentVirologist;
    }

    public static VirologistView getCurrentVirologistView(){
        return (VirologistView) objectViewHashMap.get(currentVirologist);
    }

    public static void addView(Object o, View view) {
        objectViewHashMap.put(o, view);
    }

    public void addVirologist(Virologist v){
        virologists.add(v);
    }

    public ArrayList<Virologist> getVirologists() {
        return virologists;
    }
    public QuestionPanel getquestionpanel(){
        return  questionPanel;
    }

    //endregion
}
