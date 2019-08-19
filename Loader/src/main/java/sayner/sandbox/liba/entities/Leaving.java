package sayner.sandbox.liba.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Сущность отправления
 */
@Data
@Builder
public class Leaving {

    // Что и куда отправилось
    Waybill waybill;

    // В каком самолёте
    Plane plane;

    // Когда отправилось
    LocalDateTime localDateTime;

    // Состаяние отправления
    LeavingState leavingState;
}
