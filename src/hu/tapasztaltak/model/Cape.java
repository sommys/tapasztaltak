package hu.tapasztaltak.model;

/**
 * A köpeny felszerelést reprezentáló osztály.
 * Aktív viselésével a virológusra kent ágensek hatásba lépését 82,3% valószínűséggel kivédi.
 * beírtam egy return true-t
 */
public class Cape extends Suite implements IDefense {
	public boolean tryToBlock(Virologist atc, Virologist vict, Agent a) {
		//TODO
		return true;
	}

	/**
	 * Aktív viselésre állítja a köpenyt v-n.
	 * @param v a {@link Virologist}, aki viselni kezdi a felszerelést
	 */
	public void activate(Virologist v) {
		v.addDefense(this);
		setActive(true);
	}

	/**
	 * Kiveszi a köpenyt a v aktívan viselt felszerelései közül.
	 * @param v a {@link Virologist}, akin megszünteti az aktív viselést
	 */
	public void deactivate(Virologist v) {
		v.removeDefense(this);
		setActive(false);
	}
}
