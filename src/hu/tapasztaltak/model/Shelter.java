package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

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
        Logger.log(this, "getItem", CALL, v);
        List<IStealable> chosen = v.chooseItem(List.of(suite));
        if(!chosen.isEmpty() && refreshCounter == -1){
            Random random = new Random();
            refreshCounter = random.nextInt(5) + 4;
        }
        for(IStealable s : chosen){
            v.getInventory().addSuite((Suite)s);
        }
        Logger.log(this, "", RETURN);
    }

    /**
     * Random védőfelszerelést tesz az óvóhelyre
     */
    public void refresh() {
        Logger.log(this, "refresh", CALL);
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
        Logger.log(this, "", RETURN);
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
