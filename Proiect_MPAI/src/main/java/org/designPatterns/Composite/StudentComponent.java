package org.designPatterns.Composite;

import org.utils.model.Student;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface StudentComponent {
    void sort(Comparator<Student> comparator);
    List<Student> getStudents();
    void filter(Predicate<Student> predicate);
}

