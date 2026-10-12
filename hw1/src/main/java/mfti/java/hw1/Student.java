package mfti.java.hw1;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Student can store name with marks and rules for correct marks.<br>
 * @param <T> - type of marks.
 */
public class Student<T> {
    private String name;
    private List<T> marks = new ArrayList<T>(0);
    private Predicate<T> isCorrectMark;
    private final Deque<Runnable> undoStack = new ArrayDeque<>(0);

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, List<T> marks) {
        this.name = name;
        this.marks.addAll(marks);
    }

    public Student(String name, List<T> marks, Predicate<T> isCorrectMark) {
        this.name = name;

        this.correctMarks.addAll(correctMarks);

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

    public List<T> getMarks() {
        return new ArrayList<T>(marks);
    }

    public List<T> getCorrectMarks() {
        return new ArrayList<T>(correctMarks);
    }

    public void addLastMark(T mark) {
        if (correctMarks.size() == 0 || correctMarks.contains(mark)) {
            marks.add(mark);
            undoStack.push(() -> marks.removeLast());
        } else {
            throw new IllegalArgumentException("incorrect mark: " + mark +";\tallowed marks: " + correctMarks);
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
        correctMarks.add(mark);
        undoStack.push(() -> correctMarks.removeLast());
    }

    public T popLastCorrectMark() {
        if (!correctMarks.isEmpty()) {
            T correct_mark = correctMarks.getLast();
            undoStack.push(() -> correctMarks.add(correct_mark));
            correctMarks.removeLast();
            return correct_mark;
        }
        return null;
    }

    /**
     * This function can transform lists of marks and correct marks with its own conditions.
     */
    public <R> Student<R> mapAllStudentMarks(Function<T, R> mapperMarks, Function<T, R> mapperCorrectMarks) {
        List<R> marks = this.marks.stream().map(mapperCorrectMarks).toList();
        List<R> correctMarks = this.correctMarks.stream().map(mapperCorrectMarks).toList();
        return new Student<R>(name, marks, correctMarks);
    }

    /**
     * This function undo previous action.<br>
     * Can be called multiple times, until reaching initial state of Student.
     */
    public void undo() {
        if (undoStack.size() != 0) {
            Runnable undoAction = undoStack.pop();
            undoAction.run();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Student<T> other = (Student<T>) o;

        if (!name.equals(other.name)) return false;

        // compare marks
        if (marks.size() != other.marks.size()) return false;

        Iterator<T> it = marks.iterator();
        Iterator<T> itO = other.marks.iterator();

        while(it.hasNext()) {
            if (!Objects.equals(it.next(), itO.next())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);

        int marksHash = 1;
        for (T mark : marks) {
            marksHash = 31 * marksHash + (mark != null ? mark.hashCode() : 0);
        }

        return 31 * result + marksHash;
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
