package sayner.sandbox.liba.entities;

import lombok.Builder;
import lombok.Data;

/**
 * Путевой лист
 */
@Data
@Builder
public class Waybill {

    // Каждому - свой путевой лист
    private Cargo cargo;

    // Пункт назначения обычно всегда один
    private Airport airport;
}
