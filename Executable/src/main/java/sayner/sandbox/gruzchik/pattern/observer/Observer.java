package sayner.sandbox.gruzchik.pattern.observer;

/**
 *  Интерфейс, с помощью которого наблюдатель получает оповещение
 */
public interface Observer {

    void update (float temperature, float humidity, int pressure);
}
