package sayner.sandbox.liba.entities;

public interface Section {

    // Загрузить груз
    Cargo submerge(Cargo cargo);

    Float getVolume();
}
