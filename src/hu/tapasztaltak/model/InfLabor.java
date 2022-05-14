package hu.tapasztaltak.model;

import hu.tapasztaltak.proto.ProtoLogger;
import static hu.tapasztaltak.proto.ProtoMain.getIdForObject;

public class InfLabor extends Labor{
    @Override
    public void getItem(Virologist v) throws Exception {
        super.getItem(v);
        Bear b = new Bear();
        v.spreadInitiation(b, v);
    }
}
