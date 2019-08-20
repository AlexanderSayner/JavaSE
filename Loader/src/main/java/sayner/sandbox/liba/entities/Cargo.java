package sayner.sandbox.liba.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Cargo {

    // Наименование груза
    private String name;

    // Масса
    private Float weight;

    // Объём
    private Float volume;

    private CargoState cargoState;
}
