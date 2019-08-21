package sayner.sandbox.versionlist;

import lombok.Data;

/**
 * Класс сохраняет в себе изменение, которое провели над списком
 */
@Data
class Modification<O> {

    private Version version;
    private Action action;
    private O object;
    private Integer index;
}
