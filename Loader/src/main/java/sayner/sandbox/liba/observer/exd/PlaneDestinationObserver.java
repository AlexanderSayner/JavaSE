package sayner.sandbox.liba.observer.exd;

import sayner.sandbox.liba.entities.Airport;
import sayner.sandbox.liba.entities.Plane;
import sayner.sandbox.liba.observer.Observer;

/**
 * Интерфейс, с помощью которого наблюдатель получает оповещение
 */
public interface PlaneDestinationObserver extends Observer<Airport, Plane> {

    @Override
    Plane add(Airport plane, Plane destination);

    @Override
    Plane update(Airport plane, Plane destination);

    @Override
    Plane remove(Airport plane);
}
