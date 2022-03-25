package hu.tapasztaltak.model;

import java.util.Random;

/**
 * A pályán lévő óvóhely mező reprezentálása.
 */
public class Shelter extends Field {
    /**
     * Az óvóhelyen található védőfelszerelés.
     */
    private Suite suite;

    /**
     * A {@code v} virológusnak adja a rajta lévő védőfelszerelést.
     *
     * @param v a {@link Virologist}, aki a védőfelszerelést kapja.
     */
    public void getItem(Virologist v) {
        // Todo: Peti, utólag rájöttem, hogy valszeg nem ezt kéne itt csinálni.
        v.getInventory().addSuite(suite);
    }

    /**
     * Random védőfelszerelést tesz az óvóhelyre
     */
    public void refresh() {
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
