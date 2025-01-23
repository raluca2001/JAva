package org.designPatterns.State;

import org.utils.model.Student;
import org.utils.service.BursaService;


public class StareRespinsa implements BursaState {
    private String reason;

    public StareRespinsa(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public void handleState(Student student, BursaService bursaService) {
        System.out.println("Student " + student.getNume() + " a fost respins: " + reason);
        notificareStudent();
    }

        public void notificareStudent(){
        System.out.println("Trimitere notificare catre student...");
    }
}
