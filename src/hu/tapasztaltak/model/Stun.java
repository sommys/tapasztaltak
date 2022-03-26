package hu.tapasztaltak.model;

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
