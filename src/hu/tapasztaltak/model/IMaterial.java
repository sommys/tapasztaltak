package hu.tapasztaltak.model;

public interface IMaterial extends IStealable {
	boolean isCompatible(IMaterial m);

	/**
	 *
	 *
	 */
	default void add(Inventory inv) {
		inv.getMaterials.add(this);
	}
	/**
	 *
	 *
	 */
	default void remove(Inventory inv) {
		inv.getMaterials.remove(this);
	}
}
