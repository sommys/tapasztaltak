package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;

import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;
import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

/**
 * A felszereléseknek az összefoglaló absztrakt ősosztálya,
 * ebből származnak le a különböző felszerelések.
 */
public abstract class Suite implements IStealable {
	/**
	 * Azt mutatja, hogy aktívan viselt-e a felszerelés
	 */
	protected boolean active = false;

	/**
	 * Aktivan viseltté teszi a felszerelést v-n.
	 * @param v a {@link Virologist}, aki viselni kezdi a felszerelést
	 */
	public abstract void activate(Virologist v);

	/**
	 * Leveszi, azaz megszünteti a felszerelés aktív viselését v-ről.
	 * @param v a {@link Virologist}, akin megszünteti az aktív viselést
	 */
	public abstract void deactivate(Virologist v);

	/**
	 * Hozzáadja az inv suites listájához a felszerelést.
	 * @param inv az {@link Inventory}, aminek a suites listájához adja a felszerelést.
	 */
	public void add(Inventory inv) {
		Logger.log(this, "add", CALL, inv);
		inv.getSuites().add(this);
		Logger.log(this, "", RETURN);
	}

	/**
	 * Elveszi az inv suites listájából a felszerelést.
	 * @param inv az {@link Inventory}, aminek a suites listájából elveszi a felszerelést.
	 */
	public void remove(Inventory inv) {
		Logger.log(this, "remove", CALL, inv);
		inv.getSuites().remove(this);
		active = false;
		Logger.log(this, "", RETURN);
	}

	//region GETTEREK ÉS SETTEREK

	/**
	 * Visszzaadja, hogy a felszerelés aktívan viselt-e
	 * @return aktív-e?
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Beálltja hogy aktívan viselt-e a felszerelés
	 * @param active aktívan viselt-e?
	 */
	public void setActive(boolean active) { this.active = active; }

	@Override
	public String toString(){ return getIdForObject(this); }

	//endregion
}
