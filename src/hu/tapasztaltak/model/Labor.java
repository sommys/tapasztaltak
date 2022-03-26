package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;

import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

/**
 * A pályán lévő laboratórium mező reprezentálása.
 */
public class Labor extends Field {
    /**
     * Labor falán lévő genetikai kód.
     */
    private Gene gene = null;

    /**
     * A {@code v} virológusnak adja a rajta található genetikai kódot.
     *
     * @param v a {@link Virologist}, aki a genetikai kódot kapja.
     */
    public void getItem(Virologist v) {
        Logger.log(this, "getItem", CALL, v);
        v.learn(gene);
        Logger.log(this, "", RETURN);
    }

    //region GETTEREK és SETTEREK

    /**
     * Visszaadja a laborban található genetikai kódot.
     *
     * @return a laborban található {@link Gene}
     */
    public Gene getGene() { return gene; }

    /**
     * Beállítja a laborban található genetikai kódot.
     *
     * @param gene a laborban található {@link Gene}
     */
    public void setGene(Gene gene) { this.gene = gene; }

    //endregion
}
