package org.designPatterns.Composite;

import org.utils.model.Student;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class StudentLeaf implements StudentComponent {
    private Student student;
    private boolean isVisible = true;

    public StudentLeaf(Student student) {
        this.student = student;
    }

    @Override
    public void sort(Comparator<Student> comparator) {
    }

    @Override
    public List<Student> getStudents() {
        return isVisible ? Collections.singletonList(student) : Collections.emptyList();
    }

    @Override
    public void filter(Predicate<Student> predicate) {
        if (!predicate.test(student)) {
            isVisible = false;
        }
    }

    public Student getStudent() {
        return student;
    }
}


