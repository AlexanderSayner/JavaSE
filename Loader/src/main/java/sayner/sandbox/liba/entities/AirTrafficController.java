package sayner.sandbox.liba.entities;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Связывает какой самолёт куда отправляется
 */
@Data
public class AirTrafficController {

    private Map<Plane, Airport> planeAirportMap = new HashMap<>();
}
