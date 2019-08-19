package sayner.sandbox.gruzchik.pattern.mediator;

@FunctionalInterface
public interface Mediator {

    void send(String message, Colleague sender);
}
