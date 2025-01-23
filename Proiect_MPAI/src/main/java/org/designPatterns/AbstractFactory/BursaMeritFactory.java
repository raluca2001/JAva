package org.designPatterns.AbstractFactory;

import org.utils.model.Bursa;

public class BursaMeritFactory implements BursaFactory{
    @Override
    public Bursa createBursa() {
        BursaMerit bursa =  new BursaMerit();
        bursa.setTip(TipBursa.MERIT);
        return bursa;
    }
}
