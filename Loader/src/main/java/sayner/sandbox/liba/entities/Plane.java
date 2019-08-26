package sayner.sandbox.liba.entities;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Data
@Builder
public final class Plane {

    // Собственная масса самотёта
    private Float ownWeight;

    // Грузоподьёмность
    private Float carrying;

    // Вместительность
    @Setter(AccessLevel.PRIVATE)
    private Float tonnage;

    // Было бы круто, чтобы прям самолёт открывал доступ к определённым секциям
    private List<Section> sections;

    /**
     * Рассчитать вместительность самолёта
     *
     * @return
     */
    private Float calculateTonnage() {

        AtomicReference<Float> tonnage = new AtomicReference<>(0.0f);

        this.getSections().forEach(section -> {
            tonnage.updateAndGet(v -> v + section.getVolume());
        });

        this.tonnage = tonnage.get();

        return this.tonnage;
    }

    /**
     * lombok не сделает такой getter
     *
     * @return
     */
//    public Float getTonnage() {
//
//        Optional<Float> tonnOptional = Optional.of(this.tonnage);
//
//        return tonnOptional.orElse(this.calculateTonnage());
//    }
}
