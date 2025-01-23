package org.designPatterns.Strategy;

import org.utils.model.Student;

public interface BursaStrategy {
    double calculateSumaAlocata(Student student, double sumaInitiala);
}
