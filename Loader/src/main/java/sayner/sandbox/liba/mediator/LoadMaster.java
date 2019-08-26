package sayner.sandbox.liba.mediator;

import sayner.sandbox.liba.entities.Report;
import sayner.sandbox.liba.entities.Waybill;

/**
 * Упаковщику доступны все самолёты, что есть в аэропорту
 */
public interface LoadMaster {

    // Отдать упаковщику новый заказ
    Boolean addWaybill(Waybill waybill) throws IllegalArgumentException;

    // Упаковать и отправить
    Report wrap(Waybill waybill) throws IllegalArgumentException;
}
