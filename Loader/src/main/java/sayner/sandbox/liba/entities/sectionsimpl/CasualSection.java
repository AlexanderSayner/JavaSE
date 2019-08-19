package sayner.sandbox.liba.entities.sectionsimpl;

import lombok.extern.log4j.Log4j2;
import sayner.sandbox.liba.entities.Cargo;

/**
 * Обычный отсек для всего остального
 */
@Log4j2
public class CasualSection extends AbstractSection {

    /**
     * Инициализируется вместительность секции
     *
     * @param volume
     */
    public CasualSection(Float volume) {
        super(volume);
    }

    @Override
    public Cargo submerge(Cargo cargo) {

        super.getPayload().add(cargo);
        log.info(String.format("Произведена загрузка %s в казуальную секцию", cargo.getName()));
        return cargo;
    }
}
