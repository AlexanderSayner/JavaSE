package sayner.sandbox.gruzchik;

import lombok.extern.log4j.Log4j2;
import sayner.sandbox.gruzchik.pattern.observer.Observer;
import sayner.sandbox.gruzchik.pattern.observer.impl.CurrentConditionsDisplay;
import sayner.sandbox.gruzchik.pattern.observer.impl.WeatherData;
import sayner.sandbox.liba.Simple;

@Log4j2
public class Application {

    protected static void externalLibExample() {

        Simple simple = new Simple();
        simple.setValue(666999);

        log.info("Из моей личной библиотеки: " + simple.getValue());

    }

    protected static void observerPatternExample() {

        // Тут инициализируются обсы
        WeatherData weatherData = new WeatherData();

        // Обсервер хранит в себе информацию о "хранилице обсерверов"
        Observer observer = new CurrentConditionsDisplay(weatherData);
        Observer observer1 = new CurrentConditionsDisplay(weatherData);
        Observer observer2 = new CurrentConditionsDisplay(weatherData);

        // Установить текущие значения, затем они обновляются во всех обсерверах
        weatherData.setMeasurements(25, 80, 654);
    }

    public static void main(String[] args) {

        log.info("Application started");
        log.info("I'm the main project");

        Thread thread = new Thread(Application::externalLibExample);
        thread.setName("Пример библиотеки");

        Thread thread1 = new Thread(Application::observerPatternExample);
        thread1.setName("Observer pattern example");

        thread.start();
        thread1.start();

        log.info("Application finished");
    }
}
