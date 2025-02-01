package dexter.task;

import java.time.LocalDate;

public class Deadline extends Task {
    public Deadline(String description, LocalDate ld, String mark) {
        super(description, ld, mark);
//            super.changeDoneStatus(mark);
    }
    public Deadline(String description, LocalDate ld) {
        this(description, ld, "unmark");
    }
    @Override
    public String getAll() {
        return "D " + super.getAll() + "/by " + super.getPseudoDate();
    }
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + super.getDate() + ")";
    }
}