package org.designPatterns.AbstractFactory;

import org.springframework.stereotype.Component;

@Component
public class BursaFactoryProvider {
    public static BursaFactory getFactory(TipBursa tip) {
        switch (tip) {
            case MERIT:
                return new BursaMeritFactory();
            case SOCIALA:
                return new BursaSocialaFactory();
            case PERFORMANTA:
                return new BursaPerformantaFactory();
            case AJUTOR_OCAZIONAL:
                return new BursaAjutorOcazionalFactory();
            default:
                throw new IllegalArgumentException("Tip de bursa necunoscut: " + tip);
        }
    }
}
