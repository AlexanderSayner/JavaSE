package sayner.sandbox.liba.entities.sectionsimpl;

import lombok.extern.log4j.Log4j2;
import sayner.sandbox.liba.entities.Cargo;

import java.util.Comparator;

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

        if (getVolumeRemaining() >= cargo.getVolume()) {

            // Всё ок, грузим
            super.getPayload().add(cargo);
            log.info(String.format("Произведена загрузка %s в казуальную секцию", cargo.getName()));
            return cargo;

        } else /* Экзепшен */ {

            log.info(String.format("В казуальной секции не хватило места для %s", cargo.getName()));
            return null;
        }
    }

    @Override
    public Boolean unloadOne(Cargo cargo) throws NullPointerException {

        return unloadTheBiggestOne();
    }

    public Boolean unloadTheBiggestOne() {

        super.getPayload().sort(Comparator.comparing(Cargo::getVolume, Comparator.reverseOrder()));

        Cargo removed = super.getPayload().get(0);

        Boolean status = super.getPayload().remove(removed);

        if (!status)
            throw new IllegalArgumentException("Не удалилось из казуальной секции: " + removed.toString());

        log.info(String.format("Удалили %s из казуальной секции", removed.getName()));

        return status;
    }

    @Override
    public String toString() {
        return "CasualSection";
    }
}
