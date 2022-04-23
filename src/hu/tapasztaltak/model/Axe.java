package hu.tapasztaltak.model;

import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;

/**
 * A baltát reprezentáló osztály
 */
public class Axe extends Suite{
    /**
     * Használták-e már a baltát, ha igen, akkor kicsorbult, így nincs hatása
     */
    private boolean used = false;
    /**
     * v használja a baltát
     * @param v a {@link Virologist}, aki használja a baltát
     */
    @Override
    public void activate(Virologist v) {
        //TODO fix
        if(used) return;
        Virologist toKill = v.getField().chooseVirologist(v);
        RoundManager.getInstance().removeSteppable(toKill);
        RoundManager.getInstance().removeVirologist(toKill);
    }

    public void use(Virologist v) {
        //TODO kommenttel meg minden
    }

    /**
     * Eldobja a baltát v
     * @param v a {@link Virologist}, aki eldobja a baltát
     */
    @Override
    public void deactivate(Virologist v) {
        remove(v.getInventory());
    }

    //region GETTEREK ÉS SETTEREK

    public boolean isUsed() { return used; }

    public void setUsed(boolean used) { this.used = used; }

    @Override
    public String toString() { return super.toString() + "[" + (used ? 1 : 0) + "]"; }

    //endregion
}
