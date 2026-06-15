package mfti.java.hw1;

import java.util.*;
import java.util.function.Function;

/**
 * Student can store name with marks and rules for correct marks.<br>
 * @param <T> - type of marks.
 */
public class Student<T> {
    private String name;
    private List<T> marks = new ArrayList<T>(0);
    private List<T> correct_marks = new ArrayList<T>(0);
    private final Stack<Runnable> undoStack = new Stack<>();

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, List<T> marks) {
        this.name = name;
        this.marks.addAll(marks);
    }

    public Student(String name, List<T> marks, List<T> correct_marks) {
        this.name = name;

        this.correct_marks.addAll(correct_marks);

        for(T mark: marks) {
            addLastMark(mark);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        undoStack.push(() -> this.name = oldName);
    }

    public void addLastMark(T mark) {
        if (correct_marks == null || correct_marks.contains(mark)) {
            marks.add(mark);
            undoStack.push(() -> marks.removeLast());
        } else {
            throw new IllegalArgumentException("incorrect mark: " + mark +";\tallowed marks: " + correct_marks);
        }
    }

    public T popLastMark() {
        if (!marks.isEmpty()) {
            T mark = marks.getLast();
            undoStack.push(() -> marks.add(mark));
            marks.removeLast();
            return mark;
        }
        return null;
    }

    public void addLastCorrectMark(T mark) {
        correct_marks.add(mark);
        undoStack.push(() -> correct_marks.removeLast());
    }

    public T popLastCorrectMark() {
        if (!correct_marks.isEmpty()) {
            T correct_mark = correct_marks.getLast();
            undoStack.push(() -> correct_marks.add(correct_mark));
            correct_marks.removeLast();
            return correct_mark;
        }
        return null;
    }

    /**
     * This function can transform lists of marks and correct marks with its own conditions.
     */
    public <R> Student<R> mapAllStudentMarks(Function<T, R> mapperMarks, Function<T, R> mapperCorrectMarks) {
        List<R> marks = this.marks.stream().map(mapperCorrectMarks).toList();
        List<R> correct_marks = this.correct_marks.stream().map(mapperCorrectMarks).toList();
        return new Student<R>(name, marks, correct_marks);
    }

    /**
     * This function undo previous action.<br>
     * Can be called multiple times, until reaching initial state of Student.
     */
    public void undo() {
        if (!undoStack.empty()) {
            Runnable undoAction = undoStack.pop();
            undoAction.run();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Student<T> other = (Student<T>) o;

        return name.equals(other.name) && marks.equals(other.marks);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("\t");
        sb.append(marks);
        return sb.toString();
    }

}
