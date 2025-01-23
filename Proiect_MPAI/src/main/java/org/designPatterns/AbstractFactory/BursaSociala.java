package org.designPatterns.AbstractFactory;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.utils.model.Bursa;

@Entity
@DiscriminatorValue("SOCIALA")
public class BursaSociala extends Bursa {
    public BursaSociala() {
        super(700);
    }
}
