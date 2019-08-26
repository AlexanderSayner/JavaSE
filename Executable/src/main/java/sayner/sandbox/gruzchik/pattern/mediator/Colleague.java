package sayner.sandbox.gruzchik.pattern.mediator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
@AllArgsConstructor
public class Colleague {

    private String name;

    public void notify(String message) {
        log.info("Коллега " + this.getName() + " получил сообщение: " + message);
    }
}
