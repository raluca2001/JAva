package org.designPatterns.AbstractFactory;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.utils.model.Bursa;

@Entity
@DiscriminatorValue("PERFORMANTA")
public class BursaPerformanta extends Bursa {
    public BursaPerformanta() {
        super(1000);
    }

}