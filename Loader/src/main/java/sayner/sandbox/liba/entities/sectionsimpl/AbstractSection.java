package sayner.sandbox.liba.entities.sectionsimpl;

import lombok.Getter;
import lombok.Setter;
import sayner.sandbox.liba.entities.Cargo;
import sayner.sandbox.liba.entities.Section;

import java.util.HashSet;
import java.util.Set;

@Getter
public abstract class AbstractSection implements Section {

    // Груз на борту
    private Set<Cargo> payload;

    // Объём секции
    @Setter
    private Float volume;

    /**
     * Инициализируется вместительность секции
     *
     * @param volume
     */
    protected AbstractSection(Float volume) {

        this.payload = new HashSet<>();
        this.volume = volume;
    }
}
