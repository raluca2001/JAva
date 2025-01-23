package org.utils.service;

import org.designPatterns.AbstractFactory.TipBursa;
import org.utils.model.Student;
import org.utils.repository.StudentRepository;
import org.designPatterns.State.StareAdmisa;
import org.designPatterns.State.StareInEvaluare;
import org.designPatterns.State.StareRespinsa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final BursaService bursaService;

    @Autowired
    public StudentService(StudentRepository studentRepository, BursaService bursaService) {
        this.studentRepository = studentRepository;
        this.bursaService = bursaService;
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> findAllStudentsWithBursa() {
        return studentRepository.findAllWithBursa();
    }

    public void startBursaAllocation() {
        bursaService.deleteAllBurse();
        Map<Integer, List<Student>> studentsByYear = groupStudentsByYear();
        processStudentsByYear(studentsByYear);
    }

    private Map<Integer, List<Student>> groupStudentsByYear() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .collect(Collectors.groupingBy(Student::getAnulStudiu,
                        Collectors.collectingAndThen(Collectors.toList(),
                                list -> {
                                    list.sort(Comparator.comparingDouble(Student::getMedia).reversed());
                                    return list;
                                })));
    }

    private void processStudentsByYear(Map<Integer, List<Student>> studentsByYear) {
        studentsByYear.forEach((year, students) -> {
            for (int i = 0; i < students.size(); i++) {
                allocateBursa(students.get(i), i, students.size());
            }
        });
    }

    private void allocateBursa(Student student, int index, int totalStudents) {
        List<String> rejectionReasons = new ArrayList<>();
        boolean isRejected = true;

        isRejected &= !evaluatePerformanceMeritBursa(student, index, totalStudents, rejectionReasons);
        isRejected &= !evaluateSocialBursa(student, rejectionReasons);
        isRejected &= !evaluateOccasionalAidBursa(student, rejectionReasons);

        if (isRejected) {
            new StareRespinsa(String.join(", ", rejectionReasons)).handleState(student, bursaService);
            student.setRejectionReasons(rejectionReasons);
        }
    }

    private boolean evaluatePerformanceMeritBursa(Student student, int index, int totalStudents, List<String> rejectionReasons) {
        if (student.isEligibleForPerformance(index, totalStudents)) {
            new StareAdmisa(TipBursa.PERFORMANTA).handleState(student, bursaService);
            return true;
        } else if (student.isEligibleForMerit(index, totalStudents)) {
            new StareAdmisa(TipBursa.MERIT).handleState(student, bursaService);
            return true;
        } else {
            rejectionReasons.add("Medie prea mică pentru bursa de performanță/merit");
            return false;
        }
    }

    private boolean evaluateSocialBursa(Student student, List<String> rejectionReasons) {
        double venitPerCapita = student.getVenitFamilie() / student.getMembriFamilie();
        if (venitPerCapita < 500) {
            new StareInEvaluare(TipBursa.SOCIALA).handleState(student, bursaService);
            return true;
        } else if (venitPerCapita < 1500) {
            new StareAdmisa(TipBursa.SOCIALA).handleState(student, bursaService);
            return true;
        } else {
            rejectionReasons.add("Venit prea mare pentru bursa socială");
            return false;
        }
    }

    private boolean evaluateOccasionalAidBursa(Student student, List<String> rejectionReasons) {
        if (student.isProblemeMedicale() || student.isDecesInFamilie()) {
            new StareInEvaluare(TipBursa.AJUTOR_OCAZIONAL).handleState(student, bursaService);
            return true;
        } else {
            rejectionReasons.add("Condiții nesatisfăcute pentru bursa ocazională");
            return false;
        }
    }
}
