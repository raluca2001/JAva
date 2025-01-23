package org.designPatterns.Strategy;

import org.utils.model.Student;

public class BursaPerformantaStrategy implements BursaStrategy{
    @Override
    public double calculateSumaAlocata(Student student, double sumaInitiala) {
        if(student.isParticipantCompetitii()){
            return sumaInitiala + 400;
        }
        return sumaInitiala;
    }
}
