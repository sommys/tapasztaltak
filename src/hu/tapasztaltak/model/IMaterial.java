package hu.tapasztaltak.model;

public interface IMaterial extends IStealable {
	boolean isCompatible(IMaterial m);
	default void add(Inventory inv) {
	}
	
	default void remove(Inventory inv) {
	}
}
