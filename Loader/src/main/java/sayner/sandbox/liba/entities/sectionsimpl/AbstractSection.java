package sayner.sandbox.liba.entities.sectionsimpl;

import lombok.Getter;
import lombok.Setter;
import sayner.sandbox.liba.entities.Cargo;
import sayner.sandbox.liba.entities.Section;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
public abstract class AbstractSection implements Section {

    // Груз на борту
    private List<Cargo> payload;

    // Объём секции
    @Setter
    private Float volume;

    // Переменная величина оставшегося места
    private Float volumeRemaining;

    // Рассчитать сколько ещё есть места в
    @Override
    public Float getVolumeRemaining() {

        if (this.volumeRemaining == null /* индикатор того, что грузов ещё нет */) {
            this.volumeRemaining = this.volume;
        }
        // else ничего не делать, идти дальше

        // Пройтись по списку того, что уже есть
        for (Cargo cargo : payload) {
            // Отнимать от того места, что остаётся объём
            this.volumeRemaining -= cargo.getVolume();

            if (this.volumeRemaining < 0.0f) {
                // Ссстрашный эксепшн, отрицательного значения быть не должно
                // Ответственность лежит на том, кто так полоожил груз
            }
        }

        return this.volumeRemaining;
    }

    /**
     * Инициализируется вместительность секции
     *
     * @param volume
     */
    protected AbstractSection(Float volume) {

        this.payload = new LinkedList<>();
        this.volume = volume;
    }
}
