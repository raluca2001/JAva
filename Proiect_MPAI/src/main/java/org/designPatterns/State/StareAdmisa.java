package org.designPatterns.State;

import org.designPatterns.AbstractFactory.TipBursa;
import org.utils.model.Student;
import org.utils.service.BursaService;

public class StareAdmisa implements BursaState {
    private TipBursa tipBursa;

    public StareAdmisa(TipBursa tipBursa) {
        this.tipBursa = tipBursa;
    }


    public void handleState(Student student, BursaService bursaService) {
        bursaService.createAndSaveBursa(student, tipBursa, State.ADMISA);
    }
}