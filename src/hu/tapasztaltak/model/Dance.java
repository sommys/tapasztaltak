package hu.tapasztaltak.model;

/**
 * A játékban lévő vitustánc ágens reprezentációja.
 */
public class Dance extends Agent implements SpecialModifier {
	/**
	 * v-t egy random szomszédos mezőre mozgatja.
	 * @param v {@link Virologist}, akire kifejti a hatását
	 */
	public void effect(Virologist v) {
		//todo LEGYEN TÁNC (Soma)
	}

	/**
	 * Hozzáadjuk {@code v} módosító listájához, azaz felkenődik a virológusra
	 * @param v a {@link Virologist}, akire felkenődik
	 */
	public void spread(Virologist v) {
		//todo Soma
	}
}
