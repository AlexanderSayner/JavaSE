package sayner.sandbox.liba.observer;

/**
 * Интерфейс, с помощью которого наблюдатель получает оповещение (например)
 */
public interface Observer<T, U> {

    U add(T t, U u);

    U update(T t, U u);

    U remove(T t);

    void logEvent(String message);
}
