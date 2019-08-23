package sayner.sandbox.liba.entities;

import java.util.List;
import java.util.Set;

public interface Section {

    // Загрузить груз
    Cargo submerge(Cargo cargo)throws NullPointerException;

    Cargo unloadOne(Cargo cargo) throws NullPointerException;

    Float getVolumeRemaining();

    Float getVolume();

    List<Cargo> getPayload();
}
