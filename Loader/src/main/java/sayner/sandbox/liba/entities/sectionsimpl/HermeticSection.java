package sayner.sandbox.liba.entities.sectionsimpl;

import lombok.extern.log4j.Log4j2;
import sayner.sandbox.liba.entities.Cargo;

/**
 * Особый герметичный отсек для живого груза
 */
@Log4j2
public class HermeticSection extends AbstractSection {

    /**
     * Инициализируется вместительность секции
     *
     * @param volume
     */
    public HermeticSection(Float volume) {
        super(volume);
    }

    @Override
    public Cargo submerge(Cargo cargo) {
        if (getVolumeRemaining() >= cargo.getVolume()) {

            // Всё ок, грузим
            super.getPayload().add(cargo);
            log.info(String.format("Произведена загрузка %s в герметичную секцию", cargo.getName()));
            return cargo;

        } else /* Экзепшен */ {

            log.info(String.format("В герметичной секции не хватило места для %s", cargo.getName()));
            return null;
        }
    }

    @Override
    public Boolean unloadOne(Cargo cargo) throws NullPointerException {

        Boolean status = super.getPayload().remove(cargo);

        if (!status)
            throw new IllegalArgumentException("Не удалилось из герметичной секции: " + cargo.toString());

        log.info(String.format("Удалили %s из герметичной секции", cargo.getName()));

        return status;
    }

    @Override
    public String toString() {
        return "HermeticSection";
    }
}
