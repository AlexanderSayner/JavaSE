package sayner.sandbox.liba.entities.reportImpl;

import lombok.Builder;
import lombok.Data;
import sayner.sandbox.liba.entities.LeavingState;
import sayner.sandbox.liba.entities.Plane;
import sayner.sandbox.liba.entities.Report;
import sayner.sandbox.liba.entities.Waybill;

import java.time.LocalDateTime;

/**
 * Сущность отправления
 */
@Data
@Builder
public class Leaving implements Report {

    // Что и куда отправилось
    Waybill waybill;

    // В каком самолёте
    Plane plane;

    // Когда отправилось
    LocalDateTime localDateTime;

    // Состаяние отправления
    LeavingState leavingState;

    @Override
    public String getMessage() {
        return String.format("Успешное отправление груза %s на самолёте %s состоялось", waybill.getCargo().getName(), plane.toString());
    }

    @Override
    public String toString() {
        return "LEAVING: " + getMessage();
    }
}
