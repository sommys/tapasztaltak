package hu.tapasztaltak.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A pályán lévő raktár mező reprezentálása.
 */
public class Warehouse extends Field {
    /**
     * A raktárban található anyagok listája.
     */
    private List<IMaterial> materials = new ArrayList<>();

    /**
     * A {@code v} virológusnak adja a rajta lévő anyagat.
     *
     * @param v a {@link Virologist}, aki az anyagokat kapja.
     */
    public void getItem(Virologist v) {
        // Todo: Peti
    }

    /**
     * Random anyagokat tesz a mezőre.
     */
    public void refresh() {
        // Todo: Peti, mennyi anyag keletkezzen random?
    }

    //region GETTEREK és SETTEREK

    /**
     * Visszaadja a raktárban tárolt anyagok listáját.
     *
     * @return a raktárban tárolt {@link IMaterial} lista.
     */
    public List<IMaterial> getMaterials() { return materials; }

    /**
     * Beállítja a raktárban tárolt anyagok listáját.
     *
     * @param materials a raktárban tárolt {@link IMaterial} lista.
     */
    public void setMaterials(List<IMaterial> materials) { this.materials = materials; }

    /**
     * Hozzáadja {@code material}-t a raktárban tárolt anyagok listájához.
     *
     * @param material a hozzáadandó {@link IMaterial}
     */
    public void addMaterials(IMaterial material) { materials.add(material); }

    /**
     * Törli {@code material}-t a raktárban tárolt anyagok listájából.
     *
     * @param material a törlendő {@link IMaterial}
     */
    public void removeMaterials(IMaterial material) { materials.remove(material);  }

    //endregion
}
