package sayner.sandbox.liba.functions;

/**
 * По логике должен поместить одну вещь в другую
 *
 * @param <T> то, что нужно положить
 * @param <B> куда положить
 */
@FunctionalInterface
public interface Wrapper<T, B> {

    // скажет, поместилось или нет
    Boolean wrap(T thing, B box);
}
