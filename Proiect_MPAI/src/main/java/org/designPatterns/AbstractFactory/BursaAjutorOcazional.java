package org.designPatterns.AbstractFactory;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.utils.model.Bursa;

@Entity
@DiscriminatorValue("AJUTOR_OCAZIONAL")
public class BursaAjutorOcazional extends Bursa {
    public BursaAjutorOcazional() {
        super(600);
    }
}
