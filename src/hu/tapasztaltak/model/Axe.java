package hu.tapasztaltak.model;

/**
 * A baltát reprezentáló osztály
 */
public class Axe extends Suite implements IStealable{
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
        if(used) return;
        Virologist toKill = v.getField().chooseVirologist(v);
        RoundManager.getInstance().removeSteppable(toKill);
        RoundManager.getInstance().removeVirologist(toKill);
    }

    /**
     * Eldobja a baltát v
     * @param v a {@link Virologist}, aki eldobja a baltát
     */
    @Override
    public void deactivate(Virologist v) {
        remove(v.getInventory());
    }
}
