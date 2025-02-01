package dexter.task;

import java.time.LocalDate;

public class ToDo extends Task {
    public ToDo(String description, String mark) {
        super(description, null, mark);
//            super.changeDoneStatus(mark);
    }
    public ToDo(String description) {
        this(description, "unmark");
    }
    @Override
    public String getAll() {
//        String tt1 = super.toString().replaceAll("\\(", "/");
//        String tt2 = tt1.replaceAll(":", "");
        return "T " + super.getAll();
    }
    @Override
    public boolean isDue(LocalDate t) {
        return false;
    }
    @Override
    public String getDate() {
        return null;
    }
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}