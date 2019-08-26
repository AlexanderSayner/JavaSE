package sayner.sandbox.liba.entities;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import sayner.sandbox.liba.observer.exd.PlaneDestinationObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Все аэропорты будут обсерверами
 */
@Getter
@Log4j2
public class Airport implements PlaneDestinationObserver {

    // Индентификатор аэропорта
    private String name;

    // Самолёт и пункт назначения. Самолёты, которые просто стоят и никуда не отправляются бессмысленны
    private Map<Airport,Plane> airportPlaneMap;

    public Airport(String name) {
        this.name = name;
        this.airportPlaneMap = new HashMap<>();
    }

    // Отдать самолёт в распоряжение аэропорта
    @Override
    public Plane add(Airport destination,Plane plane) {
        return this.airportPlaneMap.put(destination,plane);
    }

    // Пусть будет пока
    @Override
    public Plane update(Airport destination,Plane plane) {
        return null;
    }

    // Забрать самолёт
    @Override
    public Plane remove(Airport airport) {
        return this.airportPlaneMap.remove(airport);
    }

    @Override
    public void logEvent(String message) {
        log.info(String.format("В диспетчерскую %s поступило сообщение %s", this.name, message));
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) return true;

        if (object == null || getClass() != object.getClass()) return false;

        Airport airport = (Airport) object;

        return Objects.equals(name, airport.name); // Именно по имени различают аэропорты
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
