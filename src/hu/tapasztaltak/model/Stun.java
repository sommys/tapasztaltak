package hu.tapasztaltak.model;

import hu.tapasztaltak.proto.ProtoLogger;
import hu.tapasztaltak.skeleton.Logger;
import hu.tapasztaltak.skeleton.TestSetup;

import java.util.List;

import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;
import static hu.tapasztaltak.skeleton.Logger.LogType.CALL;
import static hu.tapasztaltak.skeleton.Logger.LogType.RETURN;

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
		Logger.log(this, "effect", CALL, v);
		v.setStunned(true);
		Logger.log(this, "", RETURN);
		ProtoLogger.logMessage(String.format("[%s effect] %s stunned", getIdForObject(this),getIdForObject(v)));
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

		TestSetup.addObject(stunA1, "stunA1");
		TestSetup.addObject(stunA2, "stunA2");
		TestSetup.addObject(stunA3, "stunA3");
		return List.of(stunA1, stunA2, stunA3);
	}

	/**
	 * Hozzáadjuk {@code v} módosító listájához, azaz felkenődik a virológusra
	 * @param v a {@link Virologist}, akire felkenődik
	 */
	public void spread(Virologist v) {
		Logger.log(this, "spread", CALL, v);
		v.addModifier(this);
		Logger.log(this, "", RETURN);
		ProtoLogger.logMessage(String.format("Stun agent added with %d rounds left from its effect to %s", getTimeLeft(),getIdForObject(v)));
	}

	/**
	 * Készít egy felhasználható másolatot az ágensből
	 * @return felhasználható másolat
	 */
	public Agent clone(){
		Logger.log(this, "clone", CALL);
		Stun newAgent = new Stun();
		newAgent.setTimeLeft(3);
		Logger.log(this, "newAgent", RETURN);
		return newAgent;
	}
}
