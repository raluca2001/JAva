package org.utils.service;

import org.designPatterns.AbstractFactory.BursaFactory;
import org.designPatterns.AbstractFactory.BursaFactoryProvider;
import org.designPatterns.AbstractFactory.TipBursa;
import org.utils.model.Bursa;
import org.utils.model.Student;
import org.utils.repository.BursaRepository;
import org.designPatterns.State.State;
import org.designPatterns.Strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BursaService {
    private final BursaRepository bursaRepository;

    @Autowired
    public BursaService(BursaRepository bursaRepository) {
        this.bursaRepository = bursaRepository;
    }

    public List<Bursa> getAllBurse() {
        return bursaRepository.findAll();
    }

    public void deleteAllBurse() {
        bursaRepository.deleteAll();
    }


    public void createAndSaveBursa(Student student, TipBursa tipBursa, State state) {
        BursaFactory factory = BursaFactoryProvider.getFactory(tipBursa);

        Bursa bursa = factory.createBursa();
        BursaStrategy strategy = selectStrategyFor(tipBursa);

        bursa.setStudent(student);
        bursa.setSumaAlocata(strategy.calculateSumaAlocata(student,bursa.getSumaAlocata()));

        bursa.setState(state);

        bursaRepository.save(bursa);
    }

    private BursaStrategy selectStrategyFor(TipBursa tip) {
        switch (tip) {
            case MERIT:
                return new BursaMeritStrategy();
            case PERFORMANTA:
                return new BursaPerformantaStrategy();
            case SOCIALA:
                return new BursaSocialaStrategy();
            case AJUTOR_OCAZIONAL:
                return new AjutorOcazionalStrategy();
            default:
                throw new IllegalArgumentException("Strategie necunoscută pentru tipul de bursa: " + tip);
        }
    }
}
