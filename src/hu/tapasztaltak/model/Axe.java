package hu.tapasztaltak.model;

import hu.tapasztaltak.proto.ProtoLogger;

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
     * @param v a {@link Virologist}, aki felveszi a baltát
     */
    @Override
    public void activate(Virologist v) {
        setActive(true);
        ProtoLogger.logMessage(String.format("%s is now worn by %s", getIdForObject(this),getIdForObject(v)));
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
            ProtoLogger.logMessage(String.format("%s is not wearing %s", getIdForObject(v), getIdForObject(this)));
            return;
        }
        if(used) {
            ProtoLogger.logMessage(String.format("%s can't use %s [broken]",getIdForObject(v),getIdForObject(this)));
            return;
        }
        if(!v.canReach(toKill)){
            ProtoLogger.logMessage(String.format("%s can't use %s [can't reach]",getIdForObject(v),getIdForObject(this)));
            return;
        }
        toKill.die();
        ProtoLogger.logMessage(String.format("%s used %s on %s",getIdForObject(v),getIdForObject(this),getIdForObject(toKill)));
        ProtoLogger.logMessage(String.format("%s died %s broke",getIdForObject(toKill),getIdForObject(this)));
        used = true;
    }

    /**
     * Eldobja a baltát v
     * @param v a {@link Virologist}, aki eldobja a baltát
     */
    @Override
    public void deactivate(Virologist v) {
        active = false;
        remove(v.getInventory());
        ProtoLogger.logMessage(String.format("%s is no longer worn by %s", getIdForObject(this),getIdForObject(v)));
    }

    //region GETTEREK ÉS SETTEREK

    public boolean isUsed() { return used; }

    public void setUsed(boolean used) { this.used = used; }

    @Override
    public String toString() { return super.toString() + "[" + (used ? 1 : 0) + "]"; }

    //endregion
}
