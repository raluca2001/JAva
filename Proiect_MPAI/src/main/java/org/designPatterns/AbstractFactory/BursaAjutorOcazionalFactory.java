package org.designPatterns.AbstractFactory;

import org.utils.model.Bursa;

public class BursaAjutorOcazionalFactory implements BursaFactory{
    @Override
    public Bursa createBursa() {
        BursaAjutorOcazional bursa = new BursaAjutorOcazional();
        bursa.setTip(TipBursa.AJUTOR_OCAZIONAL);
        return bursa;
    }
}
