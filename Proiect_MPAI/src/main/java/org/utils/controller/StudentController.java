package org.utils.controller;

import org.designPatterns.Composite.StudentComposite;
import org.designPatterns.Composite.StudentLeaf;
import org.utils.model.Student;
import org.utils.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String getStudents(Model model, @RequestParam(required = false) String sortBy, @RequestParam(required = false) Double minMedia) {
        StudentComposite allStudents = new StudentComposite();
        List<Student> retrievedStudents = studentService.findAllStudents();
        retrievedStudents.forEach(s -> allStudents.addComponent(new StudentLeaf(s)));

        if (minMedia != null) {
            allStudents.filter(s -> s.getMedia() >= minMedia);
        }

        if (sortBy != null) {
            Comparator<Student> comparator = switch (sortBy) {
                case "name" -> Comparator.comparing(Student::getNume);
                case "year" -> Comparator.comparing(Student::getAnulStudiu);
                case "medie" -> Comparator.comparing(Student::getMedia);
                default -> throw new IllegalArgumentException("Invalid sort parameter");
            };
            allStudents.sort(comparator);
        }

        model.addAttribute("students", allStudents.getStudents());
        model.addAttribute("minMedia", minMedia);
        return "students";
    }

    @PostMapping("/alocare-burse")
    public String alocareBurse() {
        studentService.startBursaAllocation();
        return "redirect:/students/burse";
    }

    @GetMapping("/burse")
    public String listStudentsBurse(Model model, @RequestParam(required = false) String sortBy, @RequestParam(required = false) Double minMedia) {
        StudentComposite allStudents = new StudentComposite();
        List<Student> retrievedStudents = studentService.findAllStudentsWithBursa();
        retrievedStudents.forEach(s -> allStudents.addComponent(new StudentLeaf(s)));

        if (minMedia != null) {
            allStudents.filter(s -> s.getMedia() >= minMedia);
            model.addAttribute("minMedia", minMedia);
        }

        if (sortBy != null) {
            Comparator<Student> comparator = switch (sortBy) {
                case "name" -> Comparator.comparing(Student::getNume);
                case "year" -> Comparator.comparing(Student::getAnulStudiu);
                case "medie" -> Comparator.comparing(Student::getMedia);
                default -> throw new IllegalArgumentException("Invalid sort parameter");
            };
            allStudents.sort(comparator);
        }

        model.addAttribute("students", allStudents.getStudents());
        return "students-burse";
    }


}



