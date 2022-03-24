package hu.tapasztaltak.model;

public interface IMaterial extends IStealable {
	boolean isCompatible(IMaterial m);
	default void add(Inventory inv) {
		inv.getMaterials().Add(this);
	}
	
	default void remove(Inventory inv) {
		inv.getMaterials.Remove(this);
	}
}
