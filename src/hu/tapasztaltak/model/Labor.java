package hu.tapasztaltak.model;

import static hu.tapasztaltak.proto.ProtoMain.getGeneId;

/**
 * A pályán lévő laboratórium mező reprezentálása.
 */
public class Labor extends Field {
    /**
     * Labor falán lévő genetikai kód.
     */
    protected Gene gene = null;

    /**
     * A {@code v} virológusnak adja a rajta található genetikai kódot.
     * Ha infected, akkor
     *
     * @param v a {@link Virologist}, aki a genetikai kódot kapja.
     */
    public void getItem(Virologist v) {
        v.learn(gene);
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

    @Override
    public String toString() {
        return super.toString() + " " + getGeneId(gene);
    }

    //endregion
}
