package org.Main;

import org.designPatterns.Composite.StudentComposite;
import org.designPatterns.Composite.StudentLeaf;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.utils.model.Student;
import org.utils.service.StudentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.utils", "org.designPatterns"})
@EnableJpaRepositories(basePackages = "org.utils.repository")
@EntityScan(basePackages = {"org.utils", "org.designPatterns"})
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        StudentService studentService = context.getBean(StudentService.class);

        StudentComposite allStudents = initializeStudentComposite(studentService);

        Scanner scanner = new Scanner(System.in);
        try {
            mainMenu(scanner, studentService, allStudents, context);
        } finally {
            scanner.close();
        }
    }

    private static StudentComposite initializeStudentComposite(StudentService studentService) {
        StudentComposite allStudents = new StudentComposite();
        List<Student> students = studentService.findAllStudents();
        students.forEach(s -> allStudents.addComponent(new StudentLeaf(s)));
        displayStudents(allStudents.getStudents());
        return allStudents;
    }

    private static void mainMenu(Scanner scanner, StudentService studentService, StudentComposite allStudents, ApplicationContext context) {
        int input = 0;
        do {
            displayMainMenu();
            input = scanner.nextInt();
            processUserInput(input, scanner, studentService, allStudents, context);
        } while (input != 6);
    }

    private static void displayMainMenu() {
        System.out.println("\nAlege o opțiune de sortare, alocare burse, sau filtrare:");
        System.out.println("1. Sort by Name");
        System.out.println("2. Sort by Year");
        System.out.println("3. Sort by Medie");
        System.out.println("4. Alocă burse și oferă opțiuni suplimentare");
        System.out.println("5. Filtrare după medie");
        System.out.println("6. Ieșire");
    }

    private static void processUserInput(int input, Scanner scanner, StudentService studentService, StudentComposite allStudents, ApplicationContext context) {
        switch (input) {
            case 1:
            case 2:
            case 3:
                allStudents.sort(getComparator(input));
                displayStudents(allStudents.getStudents());
                break;
            case 4:
                studentService.startBursaAllocation();
                updateStudentsComposite(allStudents, studentService.findAllStudentsWithBursa());
                displayStudentsWithBursa(allStudents.getStudents());
                afterBurseOptions(scanner, allStudents, studentService);
                break;
            case 5:
                double minMedia = readValidMinMedia(scanner);
                allStudents.filter(s -> s.getMedia() >= minMedia);
                displayStudents(allStudents.getStudents());
                break;
            case 6:
                System.out.println("Ieșire din program.");
                cleanUp(scanner, context);
                break;
            default:
                System.out.println("Opțiune invalidă. Te rog să introduci un număr între 1 și 6.");
                break;
        }
    }

    private static void processUserInputAfter(int input, Scanner scanner, StudentService studentService, StudentComposite allStudents) {
        switch (input) {
            case 1:
            case 2:
            case 3:
                allStudents.sort(getComparator(input));
                displayStudentsWithBursa(allStudents.getStudents());
                break;
            case 4:
                reloadStudentsWithBursa(studentService, allStudents);
                double minMedia = readValidMinMedia(scanner);
                allStudents.filter(s -> s.getMedia() >= minMedia);
                displayStudentsWithBursa(allStudents.getStudents());
                break;
            default:
                System.out.println("Opțiune invalidă. Te rog să introduci un număr între 1 și 4.");
                break;
        }
    }


    private static Comparator<Student> getComparator(int sortOption) {
        switch (sortOption) {
            case 1: return Comparator.comparing(Student::getNume);
            case 2: return Comparator.comparing(Student::getAnulStudiu);
            case 3: return Comparator.comparing(Student::getMedia);
            default: throw new IllegalArgumentException("Invalid sort option");
        }
    }

    private static void updateStudentsComposite(StudentComposite allStudents, List<Student> updatedStudents) {
        allStudents.clear();
        updatedStudents.forEach(s -> allStudents.addComponent(new StudentLeaf(s)));
    }

    private static void afterBurseOptions(Scanner scanner, StudentComposite allStudents, StudentService studentService) {
        int choice;
        do {
            displayOptions();
            choice = scanner.nextInt();
            if (choice == 5) {
                System.out.println("Ieșire din program.");
                break;
            }
            processUserInputAfter(choice, scanner, studentService, allStudents);
        } while (choice != 5);
    }


    private static void displayOptions() {
        System.out.println("\nAlege o opțiune de sortare sau filtrare:");
        System.out.println("1. Sort by Name");
        System.out.println("2. Sort by Year");
        System.out.println("3. Sort by Medie");
        System.out.println("4. Filtrare după medie");
    }

    private static void displayStudents(List<Student> students) {
        System.out.println("Lista de studenți:");
        students.forEach(student -> System.out.println(
                "Nume: " + student.getNume() +
                        ", Media: " + student.getMedia() +
                        ", Venit Familial: " + student.getVenitFamilie() +
                        ", Anul Studiului: " + student.getAnulStudiu() +
                        ", Membrii Familie: " + student.getMembriFamilie() +
                        ", Zona Defavorizata: " + student.isZonaDefavorizata() +
                        ", Probleme Medicale: " + student.isProblemeMedicale() +
                        ", Deces in Familie: " + student.isDecesInFamilie() +
                        ", Situatie Exceptionala: " + student.isSituatieExceptionala()));
    }

    private static void displayStudentsWithBursa(List<Student> students){
        System.out.println("Repartizare studenti:");
        students.forEach(student -> System.out.println("Nume: " + student.getNume() + ", Media: " + student.getMedia() +
                ", Venit Familial: " + student.getVenitFamilie() + ", Anul Studiului: " + student.getAnulStudiu() +
                ", Burse: " + student.getTipuriBurse()));
    }

    public static double readValidMinMedia(Scanner scanner) {
        double minMedia = 0;
        while (true) {
            System.out.print("Introduceți valoarea minimă pentru medie (între 1 și 10): ");
            if (scanner.hasNextDouble()) {
                minMedia = scanner.nextDouble();
                if (minMedia >= 1 && minMedia <= 10) {
                    break;
                } else {
                    System.out.println("Valoarea trebuie să fie între 1 și 10. Vă rugăm să încercați din nou.");
                }
            } else {
                System.out.println("Ați introdus un caracter non-numeric. Vă rugăm să introduceți un număr.");
                scanner.next();
            }
        }
        return minMedia;
    }

    private static void cleanUp(Scanner scanner, ApplicationContext context) {
        scanner.close();
        SpringApplication.exit(context, () -> 0);
        System.exit(0);
    }

    private static void reloadStudentsWithBursa(StudentService studentService, StudentComposite allStudents) {
        List<Student> studentsWithBursa = studentService.findAllStudentsWithBursa();
        allStudents.clear();
        studentsWithBursa.forEach(s -> allStudents.addComponent(new StudentLeaf(s)));
    }

}



