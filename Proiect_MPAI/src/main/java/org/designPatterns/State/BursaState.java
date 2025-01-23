package org.designPatterns.State;

import org.utils.model.Student;
import org.utils.service.BursaService;

public interface BursaState {
    void handleState(Student student, BursaService service);
}
