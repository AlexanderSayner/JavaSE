package sayner.sandbox.liba.observer.exd;

import sayner.sandbox.liba.entities.Airport;
import sayner.sandbox.liba.entities.Plane;
import sayner.sandbox.liba.observer.Observer;

/**
 * Интерфейс, с помощью которого наблюдатель получает оповещение
 */
public interface PlaneDestinationObserver extends Observer<Plane, Airport> {

    @Override
    Airport add(Plane plane, Airport destination);

    @Override
    Airport update(Plane plane, Airport destination);

    @Override
    Airport remove(Plane plane);
}
