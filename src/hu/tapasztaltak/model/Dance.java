package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;

import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

/**
 * A játékban lévő vitustánc ágens reprezentációja.
 */
public class Dance extends Agent implements SpecialModifier {
	/**
	 * v-t egy random szomszédos mezőre mozgatja.
	 * @param v {@link Virologist}, akire kifejti a hatását
	 */
	public void effect(Virologist v) {
		Logger.log(this, "effect", CALL, v);
		Field f = v.getField();
		Field randField = f.getRandomNeighbour();
		f.removeVirologist(v);
		randField.addVirologist(v);
		v.setMoved(true);
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
		Dance newAgent = new Dance();
		newAgent.setTimeLeft(3);
		Logger.log(this, "newAgent", RETURN);
		return newAgent;
	}
}
