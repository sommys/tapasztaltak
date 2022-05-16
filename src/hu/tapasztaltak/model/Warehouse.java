package hu.tapasztaltak.model;

import hu.tapasztaltak.view.AminoacidView;
import hu.tapasztaltak.view.NucleotidView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A pályán lévő raktár mező reprezentálása.
 */
public class Warehouse extends Field {
    public Warehouse(){
        refresh();
    }
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
        Game.getInstance().questionPanel.pickUpStuffFromWarehouseQuestion(this);
    }

    public void endPickup(){
        if(materials.isEmpty() && refreshCounter == -1){
            Game.objectViewHashMap.get(this).update();
            Random random = new Random();
            refreshCounter = random.nextInt(5) + 4;
        }
    }

    /**
     * A medve hívja, és szétzúzza a mezőn található anyagokat, amennyiben vannak.
     */
    @Override
    public void destroyStuff(){
        materials.clear();
    }

    /**
     * Random anyagokat tesz a mezőre.
     */
    public void refresh() {
        if(!materials.isEmpty()){
            return;
        }
        refreshCounter = -1;

        Random random = new Random();
        int materialsSize = random.nextInt(4) + 1;
        int randomNumber;

        for (int i = 0; i < materialsSize; i++) {
            randomNumber = random.nextInt(2);
            if (randomNumber == 0) {
                Aminoacid a = new Aminoacid();
                Game.addView(a, new AminoacidView(a));
                materials.add(a);
            }
            else {
                Nucleotid n = new Nucleotid();
                Game.addView(n, new NucleotidView(n));
                materials.add(n);
            }
        }
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
