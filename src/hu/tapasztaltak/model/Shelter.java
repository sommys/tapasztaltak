package hu.tapasztaltak.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A pályán lévő óvóhely mező reprezentálása.
 */
public class Shelter extends Field {
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
        List<IStealable> chosen = v.chooseItem(List.of(suite));
        if(!chosen.isEmpty()){
            //todo random szamra allitani a refreshcountert
        }
        for(IStealable s : chosen){
            v.getInventory().addSuite((Suite)s);
        }
    }

    /**
     * Random védőfelszerelést tesz az óvóhelyre
     */
    public void refresh() {
        refreshCounter = -1;

        Random random = new Random();
        int randomNumber = random.nextInt(3);

        switch (randomNumber) {
            case 0:
                suite = new Bag();
                break;

            case 1:
                suite = new Cape();
                break;

            case 2:
                suite = new Gloves();
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
