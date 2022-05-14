package hu.tapasztaltak.model;

import hu.tapasztaltak.proto.ProtoLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;

/**
 * A bénító ágenst reprezentáló osztály.
 * A hatása alatt a virológust nem hagyja cselekedni.
 */
public class Stun extends Agent implements SpecialModifier {
	/**
	 * {@code v}-t megbénítja, amíg hat addig tehetetlen lesz
	 * @param v {@link Virologist}, akire kifejti a hatását
	 */
	public void effect(Virologist v) {
		v.setStunned(true);
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
		Aminoacid stunA1 = new Aminoacid();
		Aminoacid stunA2 = new Aminoacid();
		Aminoacid stunA3 = new Aminoacid();
		return new ArrayList<>(Arrays.asList(stunA1, stunA2, stunA3));
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
		Stun newAgent = new Stun();
		newAgent.setTimeLeft(3);
		return newAgent;
	}
}
