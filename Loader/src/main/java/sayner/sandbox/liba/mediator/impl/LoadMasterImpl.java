package sayner.sandbox.liba.mediator.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import sayner.sandbox.liba.entities.*;
import sayner.sandbox.liba.entities.reportImpl.Denial;
import sayner.sandbox.liba.entities.reportImpl.Leaving;
import sayner.sandbox.liba.entities.sectionsimpl.CasualSection;
import sayner.sandbox.liba.entities.sectionsimpl.HermeticSection;
import sayner.sandbox.liba.entities.sectionsimpl.StableTemperatureSection;
import sayner.sandbox.liba.mediator.LoadMaster;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Log4j2
public final class LoadMasterImpl implements LoadMaster {

    // Журнал самолётов, готовых к вылету
    private final Airport airport;

    // Журнал поступивших заказов
    private Set<Waybill> waybills = new HashSet<>();

    /**
     * Находит список свободных самолётов
     *
     * @param aimAirport
     * @return
     * @throws IllegalArgumentException
     */
    private Set<Plane> getFreePlanes(Airport aimAirport) throws IllegalArgumentException {

        if (aimAirport.equals(this.airport)) {
            throw new IllegalArgumentException("O_o");
        }

        Map<Airport, Plane> airportPlaneMap = this.airport.getAirportPlaneMap();

        Set<Plane> planes = new HashSet<>();

        for (Map.Entry entry : airportPlaneMap.entrySet()) {

            //получить ключ
            Airport destination = (Airport) entry.getKey();

            if (destination.equals(aimAirport)) {

                planes.add((Plane) entry.getValue());
            }
        }

        return planes;
    }

    /**
     * Загружает груз в самолёт
     * с учётом приоритетности:
     * живой груз может перевозиться только в особом герметичном отсеке,
     * скоропортящийся - в отсеке, где поддерживается постоянная температура,
     * опасный и обычный - в любом отсеке.
     *
     * @param plane
     * @param cargo
     * @return успешность операции
     */
    private Boolean loadPlane(Plane plane, Cargo cargo) throws IllegalArgumentException {

        // Неизвестный тип груза
        CargoState state = cargo.getCargoState();

        int i = 0;
        // Перебираем все секции
        for (Section section : plane.getSections()) {

            // Надо установить соответствие между секцией и типом груза
            if ((cargo.getCargoState() == CargoState.Casual || cargo.getCargoState() == CargoState.Dangerous)
                    && section instanceof CasualSection) {

                return stateCargoSectionLoadingLogic(section, cargo);

            } else if ((cargo.getCargoState() == CargoState.Alive ||
                    cargo.getCargoState() == CargoState.Casual || cargo.getCargoState() == CargoState.Dangerous)
                    && section instanceof HermeticSection) {

                return stateCargoSectionLoadingLogic(section, cargo);

            } else if ((cargo.getCargoState() == CargoState.Perishable ||
                    cargo.getCargoState() == CargoState.Casual || cargo.getCargoState() == CargoState.Dangerous)
                    && section instanceof StableTemperatureSection) {

                return stateCargoSectionLoadingLogic(section, cargo);
            }
        }

        return false;
    }

    private Boolean stateCargoSectionLoadingLogic(Section casualSection, Cargo cargo) throws IllegalArgumentException {

        return casualSection.submerge(cargo) != null;
    }

    /**
     * Будет разгружать грузы типов, несоответствующих отсеку
     *
     * @param plane
     * @param cargo
     * @return Список непоместившихся грузов
     * @throws NullPointerException
     */
    private List<Cargo> forcedAliveOrPerishablePlaneLoading(Plane plane, Cargo cargo) throws NullPointerException {

        // Неизвестный тип груза
        CargoState state = cargo.getCargoState();

        if (cargo.getCargoState() != CargoState.Alive || cargo.getCargoState() != CargoState.Perishable) {
            // Тогда смысла нет с этим работать
            return null;
        }

        int i = 0;
        // Перебираем все секции
        for (Section section : plane.getSections()) {

            // Совпадение типов нужно проверить обязяательно
            if ((cargo.getCargoState() == CargoState.Alive && section instanceof HermeticSection) ||
                    (cargo.getCargoState() == CargoState.Perishable && section instanceof StableTemperatureSection)) {

                if (section.getVolume() < cargo.getVolume()) {
                    // Тогда даже начинать не стоит
                    continue;
                }

                List<Cargo> unloadedCargo = unloadAllCasualCargo(section);

                if (unloadedCargo.size() == 0) {
                    // Значит, ничего и не выгрузилось
                    continue;
                }

                // Места должно хватить
                Cargo loadStatus = section.submerge(cargo);

                // В любом случае, положить всё как было
                List<Cargo> lost = loadAgain(unloadedCargo, plane);

                if (loadStatus == null) {
                    // Не загрузилось
                    continue;
                }

                // Вернуть то, что пришлось выбросить
                return lost;
            }
        }

        throw new IllegalStateException("Что-то тут вообще не задалось или что-то пропустил");
    }

    /**
     * Готовит список обычного груза
     *
     * @param section который лежит в одной секции (очень удобно)
     * @return если null, ичего нету
     */
    private List<Cargo> unloadAllCasualCargo(Section section) {

        List<Cargo> removed = new LinkedList<>();

        for (Cargo cargo : section.getPayload()) {

            if (cargo.getCargoState() == CargoState.Casual || cargo.getCargoState() == CargoState.Dangerous) {

                if (section.unloadOne(cargo)) {

                    removed.add(cargo);

                } else {
                    throw new IllegalArgumentException("Там чёт не то, удалить это нельзя");
                }
            }
        }

        return removed;
    }

    /**
     * Загружает обратно в самолёт (перерастпределение груза)
     *
     * @param cargos то, что надо загрузить
     * @return то, что не поместилось
     */
    private List<Cargo> loadAgain(List<Cargo> cargos, Plane plane) {

        List<Cargo> unloaded = new LinkedList<>();

        for (Cargo cargo : cargos) {
            if (!loadPlane(plane, cargo)) {
                unloaded.add(cargo);
            }
        }

        return unloaded;
    }

    @Override
    public Boolean addWaybill(Waybill waybill) {
        this.waybills.add(waybill);
        return this.waybills.contains(waybill);
    }

    @Override
    public Report wrap(Waybill waybill) {

        // Закончим по-быстрому
        // По-любому надо проверять размер, getFreePlanes() не кинет NullPointerException
        if (getFreePlanes(waybill.getAirport()).size() == 0) {

            return new Denial("Нет самолётов, вылетающих в запрошенный пункт назначения");
        }

        // Надо быих сохранить себе на всякий случай
        Set<Plane> planeCandidates = new HashSet<>();

        // Поступил заказ, надо найти хотя бы один самолёт, который бы отправлялся в пункт назначения
        for (Plane planeCandidate : getFreePlanes(waybill.getAirport())) {

            // Когда есть свободный самолёт, грузим
            // Если загрузка удалась
            if (loadPlane(planeCandidate, waybill.getCargo())) {
                // Отдаём разрешение на отправку
                return Leaving.builder().waybill(waybill).plane(planeCandidate).leavingState(LeavingState.Shipped).localDateTime(LocalDateTime.now()).build();
            }

            planeCandidates.add(planeCandidate);
        }

        // Груз не удалось загрузить ни в один из доступных самолётов
        // Теперь будет применяться алгоритм принудительной загрузки

        if (waybill.getCargo().getCargoState() == CargoState.Alive || waybill.getCargo().getCargoState() == CargoState.Perishable) {
            for (Plane plane : planeCandidates) {
                forcedAliveOrPerishablePlaneLoading(plane, waybill.getCargo());
            }
        }

        return new Denial("Не загрузилось");
    }
}
