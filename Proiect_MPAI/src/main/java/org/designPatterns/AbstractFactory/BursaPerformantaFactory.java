package org.designPatterns.AbstractFactory;

import org.utils.model.Bursa;

public class BursaPerformantaFactory implements BursaFactory{
    @Override
    public Bursa createBursa() {
        BursaPerformanta bursa = new BursaPerformanta();
        bursa.setTip(TipBursa.PERFORMANTA);
        return bursa;
    }
}
