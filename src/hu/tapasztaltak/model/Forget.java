package hu.tapasztaltak.model;

import hu.tapasztaltak.skeleton.Logger;
import hu.tapasztaltak.skeleton.TestSetup;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
	 * Megadja, hogy aktívan hat-e még a módosító
	 * @return aktívan hat-e a módosító
	 */
	@Override
	public boolean isActive() {
		return timeLeft > 0;
	}

	/**
	 * Visszaadja az ágens készítéséhez szükséges receptet, azaz hogy milyen anyagok kellenek hozzá
	 * @return milyen anyagok ({@link IMaterial}) kellenek a készítéséhez
	 */
	@Override
	public List<IMaterial> getRecipe() {
		Nucleotid forgetN1 = new Nucleotid();
		Nucleotid forgetN2 = new Nucleotid();
		Nucleotid forgetN3 = new Nucleotid();

		TestSetup.addObject(forgetN1, "forgetN1");
		TestSetup.addObject(forgetN2, "forgetN2");
		TestSetup.addObject(forgetN3, "forgetN3");
		return List.of(forgetN1, forgetN2, forgetN3);
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
