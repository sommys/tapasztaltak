package hu.tapasztaltak.model;

public class Nucleotid implements IMaterial {
	public boolean isCompatible(IMaterial m) {
		if(m.getClass() == Nucleotid.class)
			return true;
		return false;
	}
}
