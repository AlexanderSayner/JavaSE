package sayner.sandbox.gruzchik.pattern.mediator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ColleagueMediator {

    private final Mediator mediator;

    public void send(String message, Colleague sender) {
        this.mediator.send(message, sender);
    }
}
