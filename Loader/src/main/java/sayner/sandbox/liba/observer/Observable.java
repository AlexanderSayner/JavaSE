package sayner.sandbox.liba.observer;

/**
 * Интерфейс, определяющий методы для добавления, удаления и оповещения наблюдателей
 */
public interface Observable<B extends Observer> {

    void registerObserver(B o);

    void removeObserver(B o);

    void notifyObservers();
}
