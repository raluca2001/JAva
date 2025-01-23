package org.designPatterns.Strategy;

import org.utils.model.Student;

public class BursaSocialaStrategy implements BursaStrategy {
    @Override
    public double calculateSumaAlocata(Student student, double sumaInitiala) {
        double sumaAlocata = sumaInitiala;

        if (student.isZonaDefavorizata()) {
            sumaAlocata += 200;
        }


        if (student.isSituatieExceptionala()) {
            sumaAlocata += 500;
        }

        return sumaAlocata;
    }
}

