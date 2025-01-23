package org.designPatterns.State;

import org.designPatterns.AbstractFactory.TipBursa;
import org.utils.model.Student;
import org.utils.service.BursaService;

public class StareInEvaluare implements BursaState {
    private TipBursa tipBursa;

    public StareInEvaluare(TipBursa tipBursa) {
        this.tipBursa = tipBursa;
    }
    @Override
    public void handleState(Student student, BursaService bursaService) {
        bursaService.createAndSaveBursa(student, tipBursa, State.IN_EVALUARE);
    }
}
