package mfti.java.hw1;
import mfti.java.hw1.Student;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    @Test
    void testStudentCreateWithName()
    {
        String name = "Liz";
        Student<Integer> student = new Student<Integer>(name);
        Assertions.assertEquals(student.getName(), name);
    }

    @Test
    void testStudentSetName()
    {
        String name = "Liz";
        Student<Integer> student = new Student<Integer>(name);
        String newName = "Erica";
        student.setName(newName);
        Assertions.assertEquals(student.getName(), newName);
    }

    @Test
    void testStudentWithMarks()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        Student<Integer> student = new Student<Integer>(name, marks);

        Assertions.assertEquals(marks, student.getMarks());
    }

    @Test
    void testStudentPopMark()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        Student<Integer> student = new Student<Integer>(name, marks);

        Integer lastMark = student.popLastMark();
        Assertions.assertEquals(5, lastMark);
    }

    @Test
    void testStudentWithCorrectMarks()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        Predicate<Integer> isCorrectMark = mark -> mark >= 1 && mark <= 5;
        Student<Integer> student = assertDoesNotThrow(() -> new Student<Integer>(name, marks, isCorrectMark));
    }

    @Test
    void testStudentWithWrongMarks()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        Predicate<Integer> isCorrectMark = mark -> mark >= 1 && mark <= 4;
        assertThrows(IllegalArgumentException.class, () -> new Student<Integer>(name, marks, isCorrectMark));
    }

    @Test
    void testStudentAddingMarkWithoutCorrectMarks()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        Student<Integer> student = new Student<Integer>(name, marks);

        student.addLastMark(4);

        Assertions.assertEquals(4, student.popLastMark());
    }

    @Test
    void testNotEqualStudents()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        Predicate<Integer> isCorrectMark = mark -> mark >= 1 && mark <= 5;
        Student<Integer> student1 = new Student(name, marks, isCorrectMark);

        System.out.print(student1);

        String name2 = "John";
        Student<Integer> student2 = new Student(name2, marks, isCorrectMark);
        assertNotEquals(student1, student2);

        System.out.print(student1);
    }

    @Test
    void testEqualStudents()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        Predicate<Integer> isCorrectMark = mark -> mark >= 1 && mark <= 5;
        Student<Integer> student1 = new Student(name, marks, isCorrectMark);

        String name2 = "Liz";
        List<Integer> marks2 = new ArrayList<Integer>(List.of(3,4,5));
        Predicate<Integer> isCorrectMark2 = mark -> mark >= 1 && mark <= 5;
        Student<Integer> student2 = new Student(name2, marks2, isCorrectMark2);
        assertEquals(student1, student2);
    }

    @Test
    void testRemovingMark()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        Predicate<Integer> isCorrectMark = mark -> mark >= 1 && mark <= 5;
        Student<Integer> student1 = new Student(name, marks, isCorrectMark);

        Integer mark = student1.popLastMark();
        Assertions.assertEquals(5, mark);
    }

    @Test
    void testRemovingMarkFromEmptyStudent()
    {
        String name = "Liz";
        Student<Integer> student1 = new Student(name);

        Integer mark = student1.popLastMark();
        Assertions.assertEquals(null, mark);
    }

    @Test
    void testUndo()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        Predicate<Integer> isCorrectMark = mark -> mark >= 1 && mark <= 5;
        Student<Integer> student1 = new Student(name, marks, isCorrectMark);

        Integer mark = student1.popLastMark();
        student1.undo();
        assertEquals(mark, student1.popLastMark());
    }

    @Test
    void testMap() {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(2,3,4));
        Predicate<Integer> isCorrectMark = mark -> mark >= 1 && mark <= 5;
        Student<Integer> student = new Student(name, marks, isCorrectMark);
        Student<Integer> student2 = student.mapAllStudentMarks((
                elem) -> elem + 1, isCorrectMark);

        String nameRes = "Liz";
        List<Integer> marksRes= new ArrayList<Integer>(List.of(3,4,5));
        Predicate<Integer> isCorrectMarkRes = mark -> mark >= 1 && mark <= 5;
        Student<Integer> studentRes = new Student(nameRes,marksRes, isCorrectMarkRes);
        assertEquals(studentRes, student2);

    }

    @Test
    void testToString() {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(2,3,4));
        Student<Integer> student = new Student(name, marks);

        assertEquals(name + "\t" + marks, student.toString());

    }
}
