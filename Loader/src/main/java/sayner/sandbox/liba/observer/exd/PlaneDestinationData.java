package sayner.sandbox.liba.observer.exd;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import sayner.sandbox.liba.entities.Airport;
import sayner.sandbox.liba.entities.Plane;
import sayner.sandbox.liba.observer.Observable;

import java.util.HashSet;
import java.util.Set;

@Data
@Log4j2
public class PlaneDestinationData implements Observable<PlaneDestinationObserver> {

    private Set<PlaneDestinationObserver> observers = new HashSet<>();

    @Override
    public void registerObserver(PlaneDestinationObserver o) {
        this.observers.add(o);
    }

    @Override
    public void removeObserver(PlaneDestinationObserver o) {
        this.observers.remove(o);
    }

    // Обновит данные по всех observers
    @Override
    public void notifyObservers() {

    }

    public void addNewPlaneInTheAirport(Plane plane, Airport airport) {

        this.observers.forEach(planeDestinationObserver -> {
            planeDestinationObserver.add(airport,plane);
            planeDestinationObserver.logEvent(String.format("Самолёт %s, отправляющийся в %s добавлен", plane, airport.getName()));
        });
    }
}
