package org.designPatterns.Composite;

import org.utils.model.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentComposite implements StudentComponent {
    private List<StudentComponent> components = new ArrayList<>();

    public void addComponent(StudentComponent component) {
        components.add(component);
    }

    public void removeComponent(StudentComponent component) {
        components.remove(component);
    }

    @Override
    public void sort(Comparator<Student> comparator) {
        components.forEach(component -> component.sort(comparator));
        Collections.sort(components, Comparator.comparing(c -> c.getStudents().get(0), comparator));
    }

    @Override
    public List<Student> getStudents() {
        return components.stream()
                .flatMap(c -> c.getStudents().stream())
                .collect(Collectors.toList());
    }

    @Override
    public void filter(Predicate<Student> predicate) {
        components.forEach(c -> c.filter(predicate));
        components.removeIf(c -> c.getStudents().isEmpty());
    }

    public void clear() {
        components.clear();
    }
}

