package sayner.sandbox.gruzchik;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sayner.sandbox.liba.Simple;

public class Application {

    static Logger log = LogManager.getLogger(
            "MainLogger"
    );

    public static void main(String[] args) {

        log.info("Application started");
        log.info("I'm the main project");

        Simple simple = new Simple();
        simple.setValue(666999);

        log.info("Из моей личной библиотеки: " + simple.getValue());

        log.info("Application finished");
    }
}
