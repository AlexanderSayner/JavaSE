package sayner.sandbox.gruzchik.pattern.observer;

/**
 * Интерфейс, определяющий методы для добавления, удаления и оповещения наблюдателей
 */
public interface Observable {

    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers();
}
