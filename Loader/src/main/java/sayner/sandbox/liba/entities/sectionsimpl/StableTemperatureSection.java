package sayner.sandbox.liba.entities.sectionsimpl;

import lombok.extern.log4j.Log4j2;
import sayner.sandbox.liba.entities.Cargo;

/**
 * Отсек с постоянной температурой для скоропортящегося груза
 */
@Log4j2
public class StableTemperatureSection extends AbstractSection {

    /**
     * Инициализируется вместительность секции
     *
     * @param volume
     */
    public StableTemperatureSection(Float volume) {
        super(volume);
    }

    @Override
    public Cargo submerge(Cargo cargo) {

        super.getPayload().add(cargo);
        log.info(String.format("Произведена загрузка %s в секцию стабильной температуры", cargo.getName()));
        return cargo;
    }
}
