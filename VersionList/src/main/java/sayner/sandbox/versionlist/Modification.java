package sayner.sandbox.versionlist;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Класс сохраняет в себе изменение, которое провели над списком
 * Само изменение хранится как совокупность изменений:
 * действие - список объектов, над которыми произвели это действие
 */
@Data
public class Modification<O> {

    private final Version version;
    private final Map<Action, List<O>> actionObjectsMap;
    private final Integer indexFrom;
    private final Integer indexTo;
}
