package sayner.sandbox.versionlist;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class Generation {

    private final Integer generation;

    public Generation nextGeneration() {
        return new Generation(this.generation + 1);
    }
}
