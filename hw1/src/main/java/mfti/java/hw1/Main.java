package mfti.java.hw1;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

/**
 * Main has some usual scenarios of using Student and Flow
 */
public class Main {
    static void main() {
        // 1. Student
        // Student creature with acceptable marks
        Student<Integer> student = new Student<Integer>("Liz", List.of(4, 5), List.of(1, 2, 3, 4, 5));
        System.out.println(student);

        // Student creature with wrong marks
        try {
            Student<Integer> student2 = new Student<Integer>("Mike", List.of(4, 6), List.of(1, 2, 3, 4, 5));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Equals student
        Student<Integer> studentCopy = new Student<Integer>("Liz", List.of(4, 5), List.of(1, 2, 3, 4, 5));
        System.out.println(student.equals(studentCopy));

        // Not equals student
        Student<Integer> student2 = new Student<Integer>("John", List.of(4, 5), List.of(1, 2, 3, 4, 5));
        System.out.println(student.equals(student2));

        // Student adding new right mark
        student.addLastMark(5);
        System.out.println(student);

        // Student adding new wrong mark
        try {
            student.addLastMark(6);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Student removing last mark
        Integer mark = student.popLastMark();
        System.out.println("Popped mark: " + mark);
        System.out.println(student);

        // Student undo last operation
        student.undo();
        System.out.println(student);

        // Student transform marks from Integer to String
        student.mapAllStudentMarks((elem) -> String.valueOf(elem), (elem) -> String.valueOf(elem));
        System.out.println(student);

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
        System.out.println(res1);

        // Using map, filter and collect for getting values in Set
        Set<Integer> resSet= flow2
                .map(elem -> elem * 2)
                .filter(elem -> elem >= 0)
                .collect(() -> new HashSet<Integer>(), (set, elem) -> set.add(elem));
        System.out.println(resSet);
        System.out.println(resSet.getClass());

        // Using flow3 and map, filter and reduce for getting value
        Integer res3 = flow3
                .map(elem -> elem * 2)
                .filter(elem -> elem >= 0)
                .reduce(Integer::sum);
        System.out.println(res3);
    }
}
