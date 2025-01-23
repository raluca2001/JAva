package org.designPatterns.Strategy;

import org.utils.model.Student;

public class BursaMeritStrategy implements BursaStrategy {
    @Override
    public double calculateSumaAlocata(Student student, double sumaInitiala) {
        return sumaInitiala;
    }
}
