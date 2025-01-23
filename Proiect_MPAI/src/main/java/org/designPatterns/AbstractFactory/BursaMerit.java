package org.designPatterns.AbstractFactory;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.utils.model.Bursa;

@Entity
@DiscriminatorValue("MERIT")
public class BursaMerit extends Bursa {
    public BursaMerit() {
        super(800);
    }
}
