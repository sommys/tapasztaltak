package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;
import hu.tapasztaltak.skeleton.TestSetup;

import java.util.List;

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
		if(!v.isStunned()) {
			Field f = v.getField();
			Field randField = f.getRandomNeighbour();
			f.removeVirologist(v);
			randField.addVirologist(v);
			v.setMoved(true);
		}
		Logger.log(this, "", RETURN);
	}

	/**
	 * Visszaadja az ágens készítéséhez szükséges receptet, azaz hogy milyen anyagok kellenek hozzá
	 * @return milyen anyagok ({@link IMaterial}) kellenek a készítéséhez
	 */
	@Override
	public List<IMaterial> getRecipe() {
		Nucleotid danceN = new Nucleotid();
		Aminoacid danceA = new Aminoacid();
		TestSetup.addObject(danceN, "danceN");
		TestSetup.addObject(danceA, "danceA");
		return List.of(danceN, danceA);
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
