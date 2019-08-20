package sayner.sandbox.liba.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import sayner.sandbox.liba.observer.exd.PlaneDestinationObserver;

import java.util.HashMap;
import java.util.Map;

/**
 * Все аэропорты будут обсерверами
 */
@Getter
@Log4j2
public class Airport implements PlaneDestinationObserver {

    // Индентификатор аэропорта
    private String name;

    // Самолёт - пункт назначения. Самолёты, которые просто стоят и никуда не отправляются бессмысленны
    private Map<Plane, Airport> planeAirportMap;

    public Airport(String name) {
        this.name = name;
        this.planeAirportMap = new HashMap<>();
    }

    // Отдать самолёт в распоряжение аэропорта
    @Override
    public Airport add(Plane plane, Airport destination) {
        return this.planeAirportMap.put(plane, destination);
    }

    // Пусть будет пока
    @Override
    public Airport update(Plane plane, Airport destination) {
        return null;
    }

    // Забрать самолёт
    @Override
    public Airport remove(Plane plane) {
        return this.planeAirportMap.remove(plane);
    }

    @Override
    public void logEvent(String message) {
        log.info(String.format("В диспетчерскую %s поступило сообщение %s", this.name, message));
    }
}
