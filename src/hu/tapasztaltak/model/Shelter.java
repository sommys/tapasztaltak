package hu.tapasztaltak.model;

import hu.tapasztaltak.proto.ProtoLogger;
import hu.tapasztaltak.skeleton.Logger;
import hu.tapasztaltak.skeleton.TestSetup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static hu.tapasztaltak.proto.ProtoLogger.logMessage;
import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;
import static hu.tapasztaltak.proto.ProtoMain.getSuiteId;


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
    public void getItem(Virologist v) throws Exception {
        String vList = virologists.size() == 1 ? "-" : virologists.stream().filter(it -> it != v).map(it -> getIdForObject(it)).collect(Collectors.joining(", "));
        logMessage(String.format("%s scanned %s -> suite: %s | virologists: %s", getIdForObject(v), getIdForObject(this), suite == null ? "-" : getIdForObject(suite), vList));
        if(suite == null){
            return;
        }
        List<IStealable> chosen = v.chooseItem(List.of(suite));
        if(!chosen.isEmpty() && refreshCounter == -1){
            Random random = new Random();
            refreshCounter = random.nextInt(5) + 4;
            if (v.getInventory().getSize() - v.getInventory().getUsedSize() > 0) {
                for (IStealable s : chosen) {
                    s.add(v.getInventory());
                    int space = v.getInventory().getSize() - v.getInventory().getUsedSize();
                    ProtoLogger.logMessage(String.format("%s added for %s inventory %d spaces left",getSuiteId(suite),getIdForObject(v),space));
                }
            }
            else{
                ProtoLogger.logMessage(String.format("Inventory full, can't pick up more items"));
            }
        } else{
            return;
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
                TestSetup.storage.put("b", suite);
                ProtoLogger.logMessage(String.format("%s refreshed with %s",getIdForObject(this),getSuiteId(suite)));
                break;

            case 1:
                suite = new Cape();
                TestSetup.storage.put("c", suite);
                ProtoLogger.logMessage(String.format("%s refreshed with %s",getIdForObject(this),getSuiteId(suite)));
                break;

            case 2:
                suite = new Gloves();
                TestSetup.storage.put("g", suite);
                ProtoLogger.logMessage(String.format("%s refreshed with %s",getIdForObject(this),getSuiteId(suite)));
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

    @Override
    public String toString() {
        return super.toString() + getSuiteId(suite) + "[" + refreshCounter + "]";
    }
    //endregion
}
