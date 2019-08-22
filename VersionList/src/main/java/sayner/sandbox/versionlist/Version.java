package sayner.sandbox.versionlist;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Информация о версии
 */
@Data
@AllArgsConstructor
class Version {

    private final Generation generation; // Ветка
    private final Integer number; // Итерация изменения
    private final LocalDateTime localDateTime; // Дата

    public Version(LocalDateTime localDateTime) {
        this(new Generation(1), 0, localDateTime);
    }

    public Version(Integer number, LocalDateTime localDateTime) {
        this(new Generation(1), number, localDateTime);
    }

    public Version(Generation generation, LocalDateTime localDateTime) {
        this(generation, 1, localDateTime);
    }

    public Version nextVersion(LocalDateTime localDateTime) {
        return new Version(this.generation, this.number + 1, localDateTime);
    }

    public Version nextGeneration(LocalDateTime localDateTime) {
        return new Version(this.generation.nextGeneration(), 1, localDateTime);
    }

    @Override
    public String toString() {
        return "Версия: " +
                "ветка " + generation.getGeneration() +
                ", номер " + number +
                ", дата " + localDateTime.toLocalDate() +
                ", время " + localDateTime.toLocalTime() +
                '.';
    }

    public String toJsonString() {
        return "Version{" +
                "generation=" + generation.getGeneration() +
                ", number=" + number +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
