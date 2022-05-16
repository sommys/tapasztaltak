package hu.tapasztaltak.model;


import hu.tapasztaltak.view.ForgetView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A játékban lévő felejtő ágens reprezentációja.
 */
public class Forget extends Agent implements SpecialModifier {
	/**
	 * {@code v} összes megtanult genetikai kódját törli
	 * @param v {@link Virologist}, akire kifejti a hatását
	 */
	public void effect(Virologist v) {
		v.getLearnt().clear();
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
		return new ArrayList<>(Arrays.asList(forgetN1, forgetN2, forgetN3));
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
		Game.addView(newAgent, new ForgetView(newAgent));
		return newAgent;
	}
}
