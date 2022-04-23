package hu.tapasztaltak.model;

import static hu.tapasztaltak.proto.ProtoMain.getGeneId;

public class InfLabor extends Labor{
    @Override
    public void getItem(Virologist v) {
        v.learn(gene);
        Bear b = new Bear();
        v.useAgent(b,v);
    }
}
