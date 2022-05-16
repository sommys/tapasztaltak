package hu.tapasztaltak.model;

/**
 * A kesztyű felszerelést reprezentáló osztály.
 * Aktív viselésével egy éppen kenésben lévő ágens visszakenhető a folyamatot indító virológusra.
 */
public class Gloves extends Suite implements IDefense {
	/**
	 * Azt mutatja, hogy egy folyamatban lévő kenésben felhasználták-e már a kesztyüt.
	 */
	private boolean used = false;

	/**
	 * Azt mutatja, hányszor lehet még a kesztyűt a hámlásig felhasználni
	 */
	private int useCount = 3;

	/**
	 * Visszakenhető vele egy ágens, amelyet rákentek a virológusra.
	 * @param atc a támadó {@link Virologist}
	 * @param vict a megtámadott {@link Virologist}
	 * @param a a használt {@link Agent}
	 * @return az, hogy visszakenjük-e a támadóra
	 */
	public boolean tryToBlock(Virologist atc, Virologist vict, Agent a) {
		if(atc != vict && !used && useCount > 0 && !vict.isStunned()){
			used = true;
			useCount--;
			if(useCount==0) {
				remove(vict.getInventory());
			}
			vict.useAgent(a,atc);
			return true;
		}
		return false;
	}

	/**
	 * Megadja, hogy aktívan hat-e még a védő
	 * @return aktívan hat-e még a védő
	 */
	@Override
	public boolean stillActive() {
		return active;
	}

	/**
	 * Aktív viselésre állítja a kesztyűt v-n.
	 * @param v a {@link Virologist}, aki viselni kezdi a felszerelést
	 */
	public void activate(Virologist v) {
		v.addDefense(this);
		setActive(true);
	}

	/**
	 * Kiveszi a kesztyűt a v aktívan viselt felszerelései közül.
	 * @param v a {@link Virologist}, akin megszünteti az aktív viselést
	 */
	public void deactivate(Virologist v) {
		v.removeDefense(this);
		setActive(false);
	}

	//region GETTEREK ÉS SETTEREK

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public int getUseCount() { return useCount; }

	public void setUseCount(int useCount) { this.useCount = useCount; }

	@Override
	public String toString() { return super.toString() + "[" + useCount + "]"; }
	//endregion
}
