package sayner.sandbox.liba.entities;

import java.util.Set;

public interface Section {

    // Загрузить груз
    Cargo submerge(Cargo cargo);

    Float getVolumeRemaining();

    Float getVolume();

    Set<Cargo> getPayload();
}
