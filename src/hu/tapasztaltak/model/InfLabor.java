package hu.tapasztaltak.model;

public class InfLabor extends Labor{
    @Override
    public void getItem(Virologist v) {
        v.learn(gene);
        Bear b = new Bear();
        v.useAgent(b,v);
    }
}
