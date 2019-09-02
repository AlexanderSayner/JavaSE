package sayner.sandbox.gruzchik;

import lombok.extern.log4j.Log4j2;
import sayner.sandbox.liba.entities.*;
import sayner.sandbox.liba.entities.sectionsimpl.CasualSection;
import sayner.sandbox.liba.entities.sectionsimpl.HermeticSection;
import sayner.sandbox.liba.entities.sectionsimpl.StableTemperatureSection;
import sayner.sandbox.liba.mediator.LoadMaster;
import sayner.sandbox.liba.mediator.impl.LoadMasterImpl;
import sayner.sandbox.liba.observer.exd.PlaneDestinationData;
import sayner.sandbox.versionlist.VersionList;
import sayner.sandbox.versionlist.VersionalList;

import java.util.*;

@Log4j2
public class Application {

    public static void iteratorTest() {

        String prey = "Prey";
        String the = "The";
        String neighbourhood = "Neighbourhood";
        String wiped = "Wiped";
        String out = "Out";
        String exclamationMark = "!";

        List<String> sourceList = new ArrayList<>();
        sourceList.add(prey);
        sourceList.add(the);
        sourceList.add(neighbourhood);
        sourceList.add(wiped);
        sourceList.add(out);
        sourceList.add(exclamationMark);

        VersionalList<String> versionalList = new VersionList<>();
        versionalList.add(prey);
        versionalList.add(the);
        versionalList.add(neighbourhood);
        versionalList.add(wiped);
        versionalList.add(out);
        versionalList.add(exclamationMark);

        Iterator iterator = versionalList.listIterator();

        int i = 0;

        while (iterator.hasNext()) {

            log.warn(sourceList.get(i++), (String) iterator.next());
        }
    }

    private static void externalLibExample() {

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

        List<Section> sections = new ArrayList<>();
        sections.add(new CasualSection(200.0f));
        sections.add(new HermeticSection(200.0f));
        sections.add(new StableTemperatureSection(200.0f));

        Plane plane = Plane.builder().ownWeight(5000.0f).carrying(2000.0f).sections(sections).build();

        Cargo cargo12 = Cargo.builder().name("Дичь").weight(80.0f).volume(2.0f).cargoState(CargoState.Casual).build();
        Cargo cargo13 = Cargo.builder().name("Ещё Дичь").weight(800.0f).volume(2.0f).cargoState(CargoState.Casual).build();
        Cargo cargo14 = Cargo.builder().name("И ещё Дичь").weight(8000.0f).volume(2.0f).cargoState(CargoState.Casual).build();
        Cargo cargo122 = Cargo.builder().name("Дичь").weight(80.0f).volume(2.0f).cargoState(CargoState.Alive).build();
        Cargo cargo133 = Cargo.builder().name("Ещё Дичь").weight(800.0f).volume(2.0f).cargoState(CargoState.Alive).build();
        Cargo cargo144 = Cargo.builder().name("И ещё Дичь").weight(8000.0f).volume(2.0f).cargoState(CargoState.Alive).build();
        Cargo cargo112 = Cargo.builder().name("Дичь").weight(80.0f).volume(2.0f).cargoState(CargoState.Dangerous).build();
        Cargo cargo113 = Cargo.builder().name("Ещё Дичь").weight(800.0f).volume(2.0f).cargoState(CargoState.Perishable).build();
        Cargo cargo114 = Cargo.builder().name("И ещё Дичь").weight(8000.0f).volume(2.0f).cargoState(CargoState.Perishable).build();

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
        Waybill waybill2 = Waybill.builder().cargo(cargo12).airport(airport2).build();
        Waybill waybill3 = Waybill.builder().cargo(cargo13).airport(airport).build();
        Waybill waybill4 = Waybill.builder().cargo(cargo14).airport(airport).build();
        Waybill waybill5 = Waybill.builder().cargo(cargo122).airport(airport).build();
        Waybill waybill6 = Waybill.builder().cargo(cargo133).airport(airport).build();
        Waybill waybill7 = Waybill.builder().cargo(cargo144).airport(airport).build();
        Waybill waybill8 = Waybill.builder().cargo(cargo112).airport(airport).build();
        Waybill waybill9 = Waybill.builder().cargo(cargo113).airport(airport).build();
        Waybill waybill91 = Waybill.builder().cargo(cargo114).airport(airport).build();
        Waybill waybill92 = Waybill.builder().cargo(cargo2).airport(airport).build();

        // Есть аэропорт, самолёт, груз и заказ. Настало время выхода упаковщика
        // Пусть он будет в airport1 - там всё равно так же, как и в остальных plane в налчии
        LoadMaster loadMaster = new LoadMasterImpl(airport1);
        // Здесь много чего произойдёт, метод вернёт результат загрузки
        log.info(loadMaster.wrap(waybill));
        log.info(loadMaster.wrap(waybill1));
        log.info(loadMaster.wrap(waybill2));
        log.info(loadMaster.wrap(waybill3));
        log.info(loadMaster.wrap(waybill4));
        log.info(loadMaster.wrap(waybill5));
        log.info(loadMaster.wrap(waybill6));
        log.info(loadMaster.wrap(waybill7));
        log.info(loadMaster.wrap(waybill8));
        log.info(loadMaster.wrap(waybill9));
        log.info(loadMaster.wrap(waybill91));
        log.info(loadMaster.wrap(waybill92));

        log.info("=== Plane - " + plane.toString());
        for (Section section : plane.getSections()) {
            for (Cargo c : section.getPayload()) {
                log.info(String.format("Секция %s с грузом %s", section, c.getName()));
            }
        }

    }

    public static void main(String[] args) {

        log.info("Application started");
        log.info("I'm the main project");

        Thread thread = new Thread(Application::externalLibExample);
        thread.setName("Пример библиотеки");
        Thread thread1 = new Thread(Application::iteratorTest);
        thread.setName("тестирование итератора");

        thread.start();
        thread1.start();

        log.info("Application finished");
    }
}