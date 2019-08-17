package sayner.sandbox.liba.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public final class Plane {

    // Собственная масса самотёта
    private Float ownWeight;

    // Грузоподьёмность
    private Float carrying;

    // Вместительность
    private Float tonnage;

    // Груз на борту
    private Set<Cargo> payload;
}
