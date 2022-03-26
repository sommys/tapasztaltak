package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;

import java.util.Collection;
import java.util.Collections;

import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

/**
 * A játékban lévő felejtő ágens reprezentációja.
 */
public class Forget extends Agent implements SpecialModifier {
	/**
	 * {@code v} összes megtanult genetikai kódját törli
	 * @param v {@link Virologist}, akire kifejti a hatását
	 */
	public void effect(Virologist v) {
		Logger.log(this, "effect", CALL, v);
		v.setLearnt(Collections.emptyList());
		Logger.log(this, "", RETURN);
	}

	/**
	 * Hozzáadjuk {@code v} módosító listájához, azaz felkenődik a virológusra
	 * @param v a {@link Virologist}, akire felkenődik
	 */
	public void spread(Virologist v) {
		Logger.log(this, "spread", CALL, v);
		v.addModifier(this);
		Logger.log(this, "", RETURN);
	}

	/**
	 * Készít egy felhasználható másolatot az ágensből
	 * @return felhasználható másolat
	 */
	public Agent clone(){
		Logger.log(this, "clone", CALL);
		Forget newAgent = new Forget();
		newAgent.setTimeLeft(3);
		Logger.log(this, "newAgent", RETURN);
		return newAgent;
	}
}
