package org.designPatterns.Strategy;

import org.utils.model.Student;

public class AjutorOcazionalStrategy implements BursaStrategy {
    @Override
    public double calculateSumaAlocata(Student student, double sumaInitiala) {
        double sumaAlocata = sumaInitiala;

        if (student.isDecesInFamilie() && student.isProblemeMedicale()) {
            sumaAlocata += 300;
        }

        return sumaAlocata;
    }
}
