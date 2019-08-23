package sayner.sandbox.gruzchik;

import lombok.extern.log4j.Log4j2;
import sayner.sandbox.liba.entities.*;
import sayner.sandbox.liba.entities.sectionsimpl.CasualSection;
import sayner.sandbox.liba.entities.sectionsimpl.HermeticSection;
import sayner.sandbox.liba.entities.sectionsimpl.StableTemperatureSection;
import sayner.sandbox.liba.mediator.LoadMaster;
import sayner.sandbox.liba.mediator.impl.LoadMasterImpl;
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

        // Есть аэропорты, есть в них и самолёты, которотые куда-то там спешат улететь
        // Пришла пора создать пару грузов
        Cargo cargo = Cargo.builder().name("Холодос").weight(80.0f).volume(2.0f).cargoState(CargoState.Casual).build();
        Cargo cargo1 = Cargo.builder().name("Палеты").weight(820.0f).volume(200.0f).cargoState(CargoState.Casual).build();
        Cargo cargo2 = Cargo.builder().name("Динамит").weight(180.0f).volume(20.0f).cargoState(CargoState.Casual).build();

        // Так уж вышло, что на 3 аэропорта есть 1 самолёт, и тот летит в одно и то же место
        // Сделаем так, чтобы только 2 груза смотли зайти на борт
        Waybill waybill = Waybill.builder().cargo(cargo).airport(airport).build();
        Waybill waybill1 = Waybill.builder().cargo(cargo1).airport(airport).build();
        Waybill waybill2 = Waybill.builder().cargo(cargo2).airport(airport2).build();

        // Есть аэропорт, самолёт, груз и заказ. Настало время выхода упаковщика
        // Пусть он будет в airport1 - там всё равно так же, как и в остальных plane в налчии
        LoadMaster loadMaster = new LoadMasterImpl(airport1);
        // Здесь много чего произойдёт, метод вернёт результат загрузки
        log.info(loadMaster.wrap(waybill));

    }

    public static void main(String[] args) {

        log.info("Application started");
        log.info("I'm the main project");

        Thread thread = new Thread(Application::externalLibExample);
        thread.setName("Пример библиотеки");

        thread.start();

        log.info("Application finished");
    }
}