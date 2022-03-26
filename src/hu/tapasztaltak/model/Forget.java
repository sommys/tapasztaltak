package hu.tapasztaltak.model;

import java.util.Collection;
import java.util.Collections;

/**
 * A játékban lévő felejtő ágens reprezentációja.
 */
public class Forget extends Agent implements SpecialModifier {
	/**
	 * {@code v} összes megtanult genetikai kódját törli
	 * @param v {@link Virologist}, akire kifejti a hatását
	 */
	public void effect(Virologist v) {
		v.setLearnt(Collections.emptyList());
	}

	/**
	 * Hozzáadjuk {@code v} módosító listájához, azaz felkenődik a virológusra
	 * @param v a {@link Virologist}, akire felkenődik
	 */
	public void spread(Virologist v) {
		v.addModifier(this);
	}

	/**
	 * Készít egy felhasználható másolatot az ágensből
	 * @return felhasználható másolat
	 */
	public Agent clone(){
		Forget newAgent = new Forget();
		newAgent.setTimeLeft(3);
		return newAgent;
	}
}
