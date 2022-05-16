package hu.tapasztaltak.model;


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
     * @param v a {@link Virologist}, aki felveszi a baltát
     */
    @Override
    public void activate(Virologist v) {
        setActive(true);

    }

    /**
     *
     * @param v a {@link Virologist}, aki használja a baltát
     * Ha nem aktív kilépünk
     * Ha már használt kilépünk
     * Ha használjuk kivesszük a megtámadott virológust a steppebleből és a virológusok közül és false lesz az activateja
     */
    public void use(Virologist v, Virologist toKill) {
        if(!active){
            return;
        }
        if(used) {
            return;
        }
        if(!v.canReach(toKill)){
            return;
        }
        toKill.die();
        used = true;
        Game.objectViewHashMap.get(this).update();
        Game.objectViewHashMap.get(this).update();
    }

    /**
     * Eldobja a baltát v
     * @param v a {@link Virologist}, aki eldobja a baltát
     */
    @Override
    public void deactivate(Virologist v) {
        active = false;
        remove(v.getInventory());
    }

    //region GETTEREK ÉS SETTEREK

    public boolean isUsed() { return used; }

    public void setUsed(boolean used) { this.used = used; }

    @Override
    public String toString() { return super.toString() + "[" + (used ? 1 : 0) + "]"; }

    //endregion
}
