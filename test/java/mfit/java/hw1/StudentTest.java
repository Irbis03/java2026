package mfit.java.hw1;
import mfti.java.hw1.Student;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

        marks = marks.reversed();
        for(Integer mark: marks) {
            Integer cur_student_mark = student.popLastMark();
            Assertions.assertEquals(cur_student_mark, mark);
        }
    }

    @Test
    void testStudentWithCorrectMarks()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        List<Integer> correct_marks = new ArrayList<Integer>(List.of(1,2,3,4,5));
        Student<Integer> student = new Student<Integer>(name, marks, correct_marks);

        correct_marks = correct_marks.reversed();
        for(Integer correct_mark: correct_marks) {
            Integer cur_correct_student_mark = student.popLastCorrectMark();
            Assertions.assertEquals(cur_correct_student_mark, correct_mark);
        }
    }

    @Test
    void testStudentWithWrongMarks()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        List<Integer> correct_marks = new ArrayList<Integer>(List.of(1,2,3,4));
        assertThrows(IllegalArgumentException.class, () -> {new Student<Integer>(name, marks, correct_marks);});
    }

    @Test
    void testNotEqualStudents()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        List<Integer> correct_marks = new ArrayList<Integer>(List.of(1,2,3,4,5));
        Student<Integer> student1 = new Student(name, marks, correct_marks);

        String name2 = "John";
        Student<Integer> student2 = new Student(name2, marks, correct_marks);
        assertNotEquals(student1, student2);
    }

    @Test
    void testEqualStudents()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        List<Integer> correct_marks = new ArrayList<Integer>(List.of(1,2,3,4,5));
        Student<Integer> student1 = new Student(name, marks, correct_marks);

        String name2 = "Liz";
        Student<Integer> student2 = new Student(name2, marks, correct_marks);
        assertEquals(student1, student2);
    }

    @Test
    void testRemovingMark()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        List<Integer> correct_marks = new ArrayList<Integer>(List.of(1,2,3,4,5));
        Student<Integer> student1 = new Student(name, marks, correct_marks);

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
    void testRemovingCorrectMark()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        List<Integer> correct_marks = new ArrayList<Integer>(List.of(1,2,3,4,5));
        Student<Integer> student1 = new Student(name, marks, correct_marks);

        Integer correct_mark = student1.popLastCorrectMark();
        Assertions.assertEquals(5, correct_mark);
    }

    @Test
    void testRemovingCorrectMarkFromEmptyStudent()
    {
        String name = "Liz";
        Student<Integer> student1 = new Student(name);

        Integer correct_mark = student1.popLastCorrectMark();
        Assertions.assertEquals(null, correct_mark);
    }

    @Test
    void testAddingCorrectMark()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        List<Integer> correct_marks = new ArrayList<Integer>(List.of(1,2,3,4,5));
        Student<Integer> student1 = new Student(name, marks, correct_marks);

        student1.addLastCorrectMark(2);
        Integer correct_mark = student1.popLastCorrectMark();
        Assertions.assertEquals(2, correct_mark);
    }

    @Test
    void testUndo()
    {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(3,4,5));
        List<Integer> correct_marks = new ArrayList<Integer>(List.of(1,2,3,4,5));
        Student<Integer> student1 = new Student(name, marks, correct_marks);

        Integer mark = student1.popLastMark();
        student1.undo();
        assertEquals(mark, student1.popLastMark());
    }

    @Test
    void testMap() {
        String name = "Liz";
        List<Integer> marks = new ArrayList<Integer>(List.of(2,3,4));
        List<Integer> correct_marks = new ArrayList<Integer>(List.of(1,2,3,4,5));
        Student<Integer> student = new Student(name, marks, correct_marks);
        Student<Integer> student2 = student.mapAllStudentMarks((
                elem) -> elem + 1, (elem) -> elem + 1);

        String nameRes = "Liz";
        List<Integer> marksRes= new ArrayList<Integer>(List.of(3,4,5));
        List<Integer> correct_marksRes = new ArrayList<Integer>(List.of(1,2,3,4,5));
        Student<Integer> studentRes = new Student(nameRes,marksRes, correct_marksRes);
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
