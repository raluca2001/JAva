package org.designPatterns.AbstractFactory;

import org.utils.model.Bursa;

public class BursaSocialaFactory implements BursaFactory{
    @Override
    public Bursa createBursa() {
        BursaSociala bursa =  new BursaSociala();
        bursa.setTip(TipBursa.SOCIALA);
        return bursa;
    }
}
