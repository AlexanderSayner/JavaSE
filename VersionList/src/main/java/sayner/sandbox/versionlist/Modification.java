package sayner.sandbox.versionlist;

import lombok.Data;

import java.util.List;

/**
 * Класс сохраняет в себе изменение, которое провели над списком
 */
@Data
class Modification<O> {

    private final Version version;
    private final Action action;
    private final List<O> objects;
    private final Integer index;
}
