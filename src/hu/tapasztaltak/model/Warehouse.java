package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;
import hu.tapasztaltak.skeleton.TestSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

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
        Logger.log(this, "getItem", CALL, v);
        if(v.isBear()){
            materials.clear();
        } else {
            List<IStealable> chosen = v.chooseItem(new ArrayList<>(materials));
            for (IStealable m : chosen) {
                m.add(v.getInventory());
                if (v.getInventory().getSize() - v.getInventory().getUsedSize() > 0) {
                    materials.remove(m);
                }
            }
        }
        if(materials.isEmpty() && refreshCounter == -1){
            Random random = new Random();
            refreshCounter = random.nextInt(5) + 4;
        }
        Logger.log(this, "", RETURN);
    }

    /**
     * Random anyagokat tesz a mezőre.
     */
    public void refresh() {
        Logger.log(this, "refresh", CALL);
        if(!materials.isEmpty()){
            Logger.log(this, "", RETURN);
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
                TestSetup.addObject(a, "a"+i);
                Logger.log(a, "<<create>>", CALL);
                Logger.log(a, "newAminoacid="+TestSetup.getName(a), RETURN);
                materials.add(a);
            }
            else {
                Nucleotid n = new Nucleotid();
                TestSetup.addObject(n, "n"+i);
                Logger.log(n, "<<create>>", CALL);
                Logger.log(n, "newNucleotid="+TestSetup.getName(n), RETURN);
                materials.add(n);
            }
        }
        Logger.log(this, "", RETURN);
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
