package sayner.sandbox.liba.entities;

import java.util.List;

public interface Section {

    // Загрузить груз
    Cargo submerge(Cargo cargo) throws NullPointerException;

    Boolean unloadOne(Cargo cargo) throws NullPointerException;

    Float getVolumeRemaining();

    Float getVolume();

    List<Cargo> getPayload();
}
