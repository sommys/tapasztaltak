package hu.tapasztaltak.model;

import hu.tapasztaltak.proto.ProtoLogger;
import hu.tapasztaltak.skeleton.Logger;
import hu.tapasztaltak.skeleton.TestSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static hu.tapasztaltak.proto.ProtoMain.getGeneId;
import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;

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
        String virologists = getVirologists().size() == 1 ? "-" : getVirologists().stream().filter(it -> it != v).map(it -> getIdForObject(it)).collect(Collectors.joining(", "));
        String materialsList = getMaterials().isEmpty() ? "-" : getMaterials().stream().map(it -> getIdForObject(it)).collect(Collectors.joining(", "));
        ProtoLogger.logMessage(String.format("%s scanned %s -> materials: %s | virologists: %s", getIdForObject(v), getIdForObject(this), materialsList, virologists));
        int mat = 0;
        List<IStealable> chosen = v.chooseItem(new ArrayList<>(materials));
        for (IStealable m : chosen) {
            if (v.getInventory().getSize() - v.getInventory().getUsedSize() > 0) {
                m.add(v.getInventory());
                materials.remove(m);
                mat++;
            }
        }
        if(materials.isEmpty() && refreshCounter == -1){
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
                TestSetup.addObject(a, "a"+i);
                materials.add(a);
            }
            else {
                Nucleotid n = new Nucleotid();
                TestSetup.addObject(n, "n"+i);
                materials.add(n);
            }
        }
        ProtoLogger.logMessage(String.format("%s refreshed with %s",getIdForObject(this),materials.stream().map(it -> getIdForObject(it)).collect(Collectors.joining(" "))));
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

    @Override
    public String toString() {
        int amino = materials.stream().filter(it -> it instanceof Aminoacid).collect(Collectors.toList()).size();
        int ncl = materials.stream().filter(it -> it instanceof Nucleotid).collect(Collectors.toList()).size();
        return super.toString() + amino + " " + ncl + "[" + refreshCounter + "]";
    }

    //endregion
}
