package sayner.sandbox.liba.mediator.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import sayner.sandbox.liba.entities.*;
import sayner.sandbox.liba.entities.sectionsimpl.CasualSection;
import sayner.sandbox.liba.entities.sectionsimpl.HermeticSection;
import sayner.sandbox.liba.entities.sectionsimpl.StableTemperatureSection;
import sayner.sandbox.liba.mediator.LoadMaster;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Log4j2
public final class LoadMasterImpl implements LoadMaster {

    private final Airport airport;
    private Set<Waybill> waybills = new HashSet<>();
    private Set<Leaving> leavings = new HashSet<>();

    // Загрузка на самолёт
    protected String loadIt(Cargo cargo, Plane plane) {

        // Сначала нужно определиться с подходящей секцией
        // Логика в следующем:
        // Если груз имеет специальные требования в загрузке
        // Значит тут что-то нехорошее, выясняем что именно
        if (cargo.getCargoState() == CargoState.Alive) {

            // Заглянем в самолёт
            for (Section section : plane.getSections()) {
                // Если секция подходит к данному грузу
                if (section instanceof HermeticSection) {
                    // На борт
                    if (section.submerge(cargo) != null) {
                        return String.format("Загружено в %s", section.getClass().toString());
                    } else {

                        // Операция по освобождению отсека
                        for (Section overloadedSection : plane.getSections()) {
                            if (overloadedSection instanceof HermeticSection) {

                                // Если операция вообще имеет смысл
                                if (overloadedSection.getVolume() <= cargo.getVolume()) {

                                    // Перед этим было бы неплохо сделать сортировку груза по объёму
                                    // Во всём коде имеет смысл для Set<Cargo> делать реализацию TreeSet<>
                                    for (Cargo overloadedCargo : overloadedSection.getPayload()) {

                                        if (overloadedCargo.getCargoState() != CargoState.Alive && overloadedSection.submerge(cargo) == null) {
                                            // Вот так неповезло overloadedCargo
                                            overloadedSection.getPayload().remove(overloadedCargo);
                                            // Сущности будут выбрасоваться безвозратно (временно решение, код не позволяет легко выйти из ситуации)
                                        }

                                    }
                                }
                            }
                        }

                        return String.format("Нашёлся подходящий отсек, но в нём нет места");
                    }
                }
            }

            return String.format("Здесь нет специального отсека для %s", cargo.getCargoState().toString());

        } else if (cargo.getCargoState() == CargoState.Perishable) {

            // то же самое по-сути
            for (Section section : plane.getSections()) {
                // Если секция подходит к данному грузу
                if (section instanceof StableTemperatureSection) {
                    // На борт
                    if (section.submerge(cargo) != null) {
                        return String.format("Загружено в %s", section.getClass().toString());
                    } else {

                        // Операция по освобождению отсека
                        for (Section overloadedSection : plane.getSections()) {
                            if (overloadedSection instanceof StableTemperatureSection) {

                                // Если операция вообще имеет смысл
                                if (overloadedSection.getVolume() <= cargo.getVolume()) {

                                    // Перед этим было бы неплохо сделать сортировку груза по объёму
                                    // Во всём коде имеет смысл для Set<Cargo> делать реализацию TreeSet<>
                                    for (Cargo overloadedCargo : overloadedSection.getPayload()) {

                                        if (overloadedCargo.getCargoState() != CargoState.Alive && overloadedSection.submerge(cargo) == null) {
                                            // Вот так неповезло overloadedCargo
                                            overloadedSection.getPayload().remove(overloadedCargo);
                                            // Сущности будут выбрасоваться безвозратно (временно решение, код не позволяет легко выйти из ситуации)
                                        }

                                    }
                                }
                            }
                        }

                        return String.format("Нашёлся подходящий отсек, но в нём нет места");
                    }
                }
            }

            return String.format("Здесь нет специального отсека для %s", cargo.getCargoState().toString());
        }

        // Теперь типо выбора нет, нету у груза требований к загрузке.
        // Однако, это значит, что приоритетом стоит казуальный отсек

        for (Section section : plane.getSections()) {
            // Если секция подходит к данному грузу
            if (section instanceof CasualSection) {
                // На борт
                if (section.submerge(cargo) != null) {
                    return String.format("Загружено в %s", section.getClass().toString());
                } else {
                    return String.format("Нашёлся подходящий отсек, но в нём нет места");
                }
            }
        }

        // Теперь ясно, что груз не имеет специальных требований к загрузке,
        // Однако в отсеке для обычных грузов нет места
        // По условию обычные грузы можно грузить куда угодно
        // Следовательно, грузим в любой
        for (Section section : plane.getSections()) {

            // На борт в первый попавшийся
            if (section.submerge(cargo) != null) {
                return String.format("Загружено в %s", section.getClass().toString());
            }
        }

        return String.format("Нет места");
    }

    // Отправка
    protected Leaving sendIt() {
        return null;
    }

    @Override
    public Boolean addWaybill(Waybill waybill) {
        this.waybills.add(waybill);
        return this.waybills.contains(waybill);
    }

    @Override
    public Boolean dispatchOrder(Waybill waybill) {


        return false;
    }

    // Примечание: было бы неплохо подумать как это всё в стиле ООП переписать
    @Override
    public String wrap(Waybill waybill) {

        Plane planeCandidate = null;

        // Поступил заказ, надо найти хотя бы один самолёт, который бы отправлялся в пункт назначения
        for (Map.Entry entry : this.airport.getPlaneAirportMap().entrySet()) {

            log.trace("Key: " + entry.getKey().toString() + " Value: " + entry.getValue().toString());

            // Если это чудо и есть аэропорт, то смотрим совпадает их индентификатор
            // (equals не самый надёжный путь, учитывая, что объект в waybill может изметиться (вылетел самолёт) прежде, чем тот же объект обновится здесь)
            if (entry.getValue() instanceof Airport && ((Airport) entry.getValue()).getName().equals(waybill.getAirport().getName())) {

                // Самолёт из мапы нашего (лоуд-мастера) вылетает в нужный нам (в заказе) аэропорт
                if (entry.getKey() instanceof Plane)
                    planeCandidate = (Plane) entry.getKey();
                // else страшные экзепшены и всё в таком духе
            }

            // Я делаю именно так, чтобы не нагромождать вложенных конструкций
            if (planeCandidate != null/*значит есть-таки нужный самолёт*/) {

                // Пошёл на загрузку
                return loadIt(waybill.getCargo(), planeCandidate);
            }
            // else продолжить цикл
        }

        // Цикл закончился, значит так и не нашёлся самолёт, отправляющийся в пункт назначения
        return String.format("Не нашёлся самолёт, отправляющийся в пункт назначения %s", waybill.getAirport().getName());
    }
}
