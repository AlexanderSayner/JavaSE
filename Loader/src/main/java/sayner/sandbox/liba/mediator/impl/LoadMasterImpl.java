package sayner.sandbox.liba.mediator.impl;

import lombok.RequiredArgsConstructor;
import sayner.sandbox.liba.entities.Airport;
import sayner.sandbox.liba.entities.Leaving;
import sayner.sandbox.liba.entities.Waybill;
import sayner.sandbox.liba.entities.sectionsimpl.HermeticSection;
import sayner.sandbox.liba.entities.sectionsimpl.StableTemperatureSection;
import sayner.sandbox.liba.functions.Wrapper;
import sayner.sandbox.liba.mediator.LoadMaster;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public final class LoadMasterImpl implements LoadMaster {

    private final Airport airport;
    private Set<Waybill> waybills=new HashSet<>();
    private Set<Leaving> leavings=new HashSet<>();

    @Override
    public Boolean addWaybill(Waybill waybill) {
        this.waybills.add(waybill);
        return this.waybills.contains(waybill);
    }

    @Override
    public Boolean dispatchOrder(Waybill waybill) {


        return false;
    }

    @Override
    public void wrap(Wrapper wrapper) {
    }
}
