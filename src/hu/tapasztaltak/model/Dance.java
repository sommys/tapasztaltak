package hu.tapasztaltak.model;

import hu.tapasztaltak.proto.ProtoLogger;
import hu.tapasztaltak.proto.ProtoMain;
import hu.tapasztaltak.skeleton.Logger;
import hu.tapasztaltak.skeleton.TestSetup;
import java.util.List;
import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;

/**
 * A játékban lévő vitustánc ágens reprezentációja.
 */
public class Dance extends Agent implements SpecialModifier {
	/**
	 * v-t egy random szomszédos mezőre mozgatja.
	 * @param v {@link Virologist}, akire kifejti a hatását
	 */

	//[dnc1 effect] vir1 moved to wrh1
	public void effect(Virologist v) {
		if(!v.isStunned()) {
			Field f = v.getField();
			Field randField = f.getRandomNeighbour();
			if(randField != f){
				f.removeVirologist(v);
				randField.addVirologist(v);
				v.setField(randField);
				ProtoLogger.logMessage(String.format("[%s effect] %s moved to %s", getIdForObject(this), getIdForObject(v), getIdForObject(randField)));
			}
		}
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
		Nucleotid danceN = new Nucleotid();
		Aminoacid danceA = new Aminoacid();
		return List.of(danceN, danceA);
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
		Dance newAgent = new Dance();
		newAgent.setTimeLeft(3);
		return newAgent;
	}
}
