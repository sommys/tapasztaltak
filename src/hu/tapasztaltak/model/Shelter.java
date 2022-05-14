package hu.tapasztaltak.model;

import hu.tapasztaltak.proto.ProtoLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;
import static hu.tapasztaltak.proto.ProtoMain.getSuiteId;


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
    public void getItem(Virologist v) throws Exception {
        String vList = virologists.size() == 1 ? "-" : virologists.stream().filter(it -> it != v).map(it -> getIdForObject(it)).collect(Collectors.joining(", "));
        if(suite == null){
            return;
        }
        List<IStealable> chosen = v.chooseItem(new ArrayList<>(Arrays.asList(suite)));
        if(!chosen.isEmpty() && refreshCounter == -1){
            Random random = new Random();
            refreshCounter = random.nextInt(5) + 4;
            if (v.getInventory().getSize() - v.getInventory().getUsedSize() > 0) {
                for (IStealable s : chosen) {
                    s.add(v.getInventory());
                    int space = v.getInventory().getSize() - v.getInventory().getUsedSize();
                }
            }
            else{
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

    @Override
    public String toString() {
        return super.toString() + getSuiteId(suite) + "[" + refreshCounter + "]";
    }
    //endregion
}
