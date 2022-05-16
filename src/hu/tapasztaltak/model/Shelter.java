package hu.tapasztaltak.model;

import hu.tapasztaltak.view.BagView;
import hu.tapasztaltak.view.CapeView;
import hu.tapasztaltak.view.GlovesView;

import java.util.Random;


/**
 * A pályán lévő óvóhely mező reprezentálása.
 */
public class Shelter extends Field {
    public Shelter(){
        refresh();
    }
    /**
     * Az óvóhelyen található védőfelszerelés.
     */
    private Suite suite = null;

    /**
     * A {@code v} virológusnak adja a rajta lévő védőfelszerelést.
     *
     * @param v a {@link Virologist}, aki a védőfelszerelést kapja.
     */
    public void getItem(Virologist v) {
        if(suite == null){
            return;
        }
        Game.getInstance().questionPanel.pickUpStuffFromShelterQuestion(this);
    }

    public void endPickup(){
        if(suite == null && refreshCounter == -1){
            Game.objectViewHashMap.get(this).update();
            Random random = new Random();
            refreshCounter = random.nextInt(5) + 4;
        }
    }

    /**
     * Random védőfelszerelést tesz az óvóhelyre
     */
    public void refresh() {
        if(suite != null){
            return;
        }
        refreshCounter = -1;

        Random random = new Random();
        int randomNumber = random.nextInt(3);

        switch (randomNumber) {
            case 0:
                suite = new Bag();
                Game.addView(suite, new BagView ((Bag)suite));
                break;
            case 1:
                suite = new Cape();
                Game.addView(suite, new CapeView((Cape)suite));
                break;
            case 2:
                suite = new Gloves();
                Game.addView(suite, new GlovesView((Gloves)suite));
                break;
        }
    }

    //region GETTEREK és SETTEREK

    /**
     * Visszaadja az óvóhelyen tárolt védőfelszerelést.
     *
     * @return az óvóhelyen tárolt {@link  Suite}
     */
    public Suite getSuite() { return suite; }

    /**
     * Beállítja az óvóhelyen tárolt védőfelszerelést.
     *
     * @param suite az óvóhelyen tárolt {@link  Suite}
     */
    public void setSuite(Suite suite) { this.suite = suite; }
    //endregion
}
