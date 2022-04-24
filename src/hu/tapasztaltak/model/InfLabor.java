package hu.tapasztaltak.model;

import hu.tapasztaltak.proto.ProtoLogger;

import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;

public class InfLabor extends Labor{
    @Override
    public void getItem(Virologist v) throws Exception {
        ProtoLogger.logMessage(String.format("%s tries to infect %s with Bear", getIdForObject(v), getIdForObject(gene)));
        v.learn(gene);
        Bear b = new Bear();
        v.useAgent(b,v);
    }
}
