package mfti.java.hw1;

import java.util.*;
import java.util.function.Predicate;

/**
 * Main has some usual scenarios of using Student and Flow
 */
public class Main {
    static void main() {
        // 1. Student
        // Student creature with acceptable marks
        Predicate<Integer> isCorectMark = mark -> mark >= 1 && mark <= 5;
        Student<Integer> student = new Student<Integer>("Liz", List.of(4, 5), isCorectMark);

        // Student creature with wrong marks
        try {
            Student<Integer> student2 = new Student<Integer>("Mike", List.of(4, 6), isCorectMark);
        } catch (IllegalArgumentException e) {
            String errMessage = e.getMessage();
        }

        // Equals student
        Student<Integer> studentCopy = new Student<Integer>("Liz", List.of(4, 5), isCorectMark);
        boolean resEq = student.equals(studentCopy);

        // Not equals student
        Student<Integer> student2 = new Student<Integer>("John", List.of(4, 5), isCorectMark);
        boolean resNotEq = student.equals(student2);

        // Student adding new right mark
        student.addLastMark(5);

        // Student adding new wrong mark
        try {
            student.addLastMark(6);
        } catch (IllegalArgumentException e) {
            String errMessage = e.getMessage();
        }

        // Student removing last mark
        Integer mark = student.popLastMark();

        // Student undo last operation
        student.undo();

        // Student transform marks from Integer to String
        student.mapAllStudentMarks((elem) -> String.valueOf(elem), (elem) -> Integer.parseInt(elem) >= 1 &&
                Integer.parseInt(elem) <= 5);

        // 2. Flow
        // Create flow using List
        Flow<Integer> flow1 = new Flow<Integer>(List.of(-2, 4, 6));

        // Create flow using varargs
        Flow<Integer> flow2 = new Flow<Integer>(-2, 4, 6);

        // Create flow using first elem, step function and end condition value
        Flow<Integer> flow3 = new Flow<Integer>(2, (el) -> el + 2, 8);

        // Using map, filter and reduce for getting value
        Integer res1 = flow1
                .map(elem -> elem * 2)
                .filter(elem -> elem >= 0)
                .reduce(Integer::sum);

        // Using map, filter and collect for getting values in Set
        Set<Integer> resSet= flow2
                .map(elem -> elem * 2)
                .filter(elem -> elem >= 0)
                .collect(() -> new HashSet<Integer>(), (set, elem) -> set.add(elem));

        // Using flow3 and map, filter and reduce for getting value
        Integer res3 = flow3
                .map(elem -> elem * 2)
                .filter(elem -> elem >= 0)
                .reduce(Integer::sum);
    }
}
