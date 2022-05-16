package hu.tapasztaltak.model;

public class InfLabor extends Labor{
    @Override
    public void getItem(Virologist v) {
        super.getItem(v);
        Bear b = new Bear();
        v.spreadInitiation(b, v);
    }
}
