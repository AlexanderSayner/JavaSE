package sayner.sandbox.liba.mediator;

import sayner.sandbox.liba.entities.Waybill;

/**
 * Упаковщику доступны все самолёты, что есть в аэропорту
 */
public interface LoadMaster {

    // Отдать упаковщику новый заказ
    Boolean addWaybill(Waybill waybill);

    // Отправить
    Boolean dispatchOrder(Waybill waybill);

    // Упаковщик знает сколько места остаётся в самолёте после упаковки
    String wrap(Waybill waybill);
}
