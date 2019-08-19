package sayner.sandbox.gruzchik;

import lombok.extern.log4j.Log4j2;
import sayner.sandbox.gruzchik.pattern.mediator.Colleague;
import sayner.sandbox.gruzchik.pattern.mediator.ColleagueMediator;
import sayner.sandbox.gruzchik.pattern.observer.Observer;
import sayner.sandbox.gruzchik.pattern.observer.impl.CurrentConditionsDisplay;
import sayner.sandbox.gruzchik.pattern.observer.impl.WeatherData;
import sayner.sandbox.liba.entities.Airport;
import sayner.sandbox.liba.entities.Plane;
import sayner.sandbox.liba.entities.Section;
import sayner.sandbox.liba.entities.sectionsimpl.CasualSection;
import sayner.sandbox.liba.entities.sectionsimpl.HermeticSection;
import sayner.sandbox.liba.entities.sectionsimpl.StableTemperatureSection;
import sayner.sandbox.liba.observer.exd.PlaneDestinationData;

import java.util.HashSet;
import java.util.Set;

@Log4j2
public class Application {

    protected static void externalLibExample() {

        // Инициализация обсерверов
        Airport airport = new Airport("раз");
        Airport airport1 = new Airport("два");
        Airport airport2 = new Airport("тры");

        // Сейчас обсы безполезны
        // Но стоит их отправить к надзирателю
        PlaneDestinationData planeDestinationData = new PlaneDestinationData();
        planeDestinationData.registerObserver(airport);
        planeDestinationData.registerObserver(airport1);
        planeDestinationData.registerObserver(airport2);
        // как дело принимает интересный оборот

        Set<Section> sections = new HashSet<>();
        sections.add(new CasualSection(200.0f));
        sections.add(new HermeticSection(200.0f));
        sections.add(new StableTemperatureSection(200.0f));

        Plane plane = Plane.builder().ownWeight(5000.0f).carrying(2000.0f).sections(sections).build();
        // Добавив данные сюда
        planeDestinationData.addNewPlaneInTheAirport(plane, airport);

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

    protected static void mediatorPatternExample() {

        /**
         * 1.
         * Есть какие-то там объекты бизнес-логики
         * Я им даю имена
         * А ещё они умеют говорить в логах
         */
        Colleague colleague = new Colleague("Один");
        Colleague colleague1 = new Colleague("Два");
        Colleague colleague2 = new Colleague("Тры");

        /**
         * Это класс конфигурации
         * Он должным образом настраивает посредника, который говорит как и что нужно делать
         */
        ColleagueMediator colleagueMediator = new ColleagueMediator((message, sender) -> sender.notify(message));

        /**
         * Теперь используем его функционал
         */
        colleagueMediator.send("сообщение 1", colleague);
        colleagueMediator.send("сообщение 2", colleague1);
        colleagueMediator.send("сообщение 3", colleague2);

    }

    public static void main(String[] args) {

        log.info("Application started");
        log.info("I'm the main project");

        Thread thread = new Thread(Application::externalLibExample);
        thread.setName("Пример библиотеки");

        Thread thread1 = new Thread(Application::observerPatternExample);
        thread1.setName("PlaneObserver pattern example");

        Thread thread2 = new Thread(Application::mediatorPatternExample);
        thread2.setName("Mediator pattern example");

        thread.start();
        thread1.start();
        thread2.start();

        log.info("Application finished");
    }
}
