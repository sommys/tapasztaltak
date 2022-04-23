package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;
import hu.tapasztaltak.skeleton.TestSetup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static hu.tapasztaltak.proto.ProtoMain.getSuiteId;
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
        if(suite == null){
            Logger.log(this, "", RETURN);
            return;
        }
        List<IStealable> chosen = v.chooseItem(List.of(suite));
        if(!chosen.isEmpty() && refreshCounter == -1){
            Random random = new Random();
            refreshCounter = random.nextInt(5) + 4;
        }
        //Van-e elég helye? - ellenőrzés
        for(IStealable s : chosen){
            s.add(v.getInventory());
        }
        Logger.log(this, "", RETURN);
    }

    /**
     * Random védőfelszerelést tesz az óvóhelyre
     */
    public void refresh() {
        Logger.log(this, "refresh", CALL);
        if(suite != null){
            Logger.log(this, "", RETURN);
            return;
        }
        refreshCounter = -1;

        Random random = new Random();
        int randomNumber = random.nextInt(3);

        switch (randomNumber) {
            case 0:
                suite = new Bag();
                TestSetup.storage.put("b", suite);
                break;

            case 1:
                suite = new Cape();
                TestSetup.storage.put("c", suite);
                break;

            case 2:
                suite = new Gloves();
                TestSetup.storage.put("g", suite);
                break;
        }
        Logger.log(suite, "<<create>>", CALL);
        Logger.log(suite, "suite="+TestSetup.getName(suite), RETURN);
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

    @Override
    public String toString() {
        return super.toString() + getSuiteId(suite) + "[" + refreshCounter + "]";
    }
    //endregion
}
