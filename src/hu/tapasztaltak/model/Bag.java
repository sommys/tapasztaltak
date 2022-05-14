package hu.tapasztaltak.model;


import java.util.Random;

import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;

/**
 * A zsák felszerelést reprezentáló osztály.
 * Aktív viselésével megnő az inventory tároló mérete.
 */
public class Bag extends Suite {
	/**
	 * A zsák mérete, ami aktív hordás alatt, ennyivel növeli meg az {@link Inventory} méretét.
	 */
	private int size = 10;

	/**
	 * Aktív viselésre állítja a zsákot v-n.
	 * @param v a {@link Virologist}, aki viselni kezdi a felszerelést
	 */
	public void activate(Virologist v) {
		int currentSize = v.getInventory().getSize();
		v.getInventory().setSize(currentSize + size);
		setActive(true);
	}

	/**
	 * Kiveszi a zsákot a v aktívan viselt felszerelései közül.
	 * @param v a {@link Virologist}, akin megszünteti az aktív viselést
	 */
	public void deactivate(Virologist v) {
		clearInventoryFromBag(v.getInventory());
		int currentSize = v.getInventory().getSize();
		v.getInventory().setSize(currentSize - size);
		setActive(false);
	}

	/**
	 * Eltávolítja a zsákban lévő dolgokat {@code inv}-ből
	 * @param inv a tárhely, amiből ki kell venni a plusz dolgokat
	 */
	private void clearInventoryFromBag(Inventory inv) {
		if(active && inv.getUsedSize()>10){
			for(int i = 0; i < size-1; i++){
				Random r = new Random();
				if(inv.getMaterials().isEmpty()){
					inv.getSuites().remove(r.nextInt(inv.getSuites().size()));
				} else if(inv.getSuites().isEmpty()){
					inv.getMaterials().remove(r.nextInt(inv.getMaterials().size()));
				} else {
					int choice = r.nextInt(2);
					if(choice == 0){
						inv.getSuites().remove(r.nextInt(inv.getSuites().size()));
					} else {
						inv.getMaterials().remove(r.nextInt(inv.getMaterials().size()));
					}
				}
			}
		}
	}

	/**
	 * Eltávolítják a zsákot az inventoryból, így elvesznek a benne levő dolgok
	 * @param inv az {@link Inventory}, aminek a suites listájából elveszi a felszerelést.
	 */
	@Override
	public void remove(Inventory inv){
		clearInventoryFromBag(inv);
		inv.removeSuite(this);
	}

	//region GETTEREK ÉS SETTEREK

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	//endregion
}
