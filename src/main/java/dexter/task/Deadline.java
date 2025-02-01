package dexter.task;

import java.time.LocalDate;

public class Deadline extends Task {
    public Deadline(String description, LocalDate ld, String mark) {
        super(description, ld, mark);
    }
    public Deadline(String description, LocalDate ld) {
        this(description, ld, "unmark");
    }
    @Override
    public String getAll() {
//        String tt1 = super.toString().replaceAll("\\(", "/");
//        String tt2 = tt1.replaceAll(":", "");
        return "D " + super.getAll() + "/by " + super.getPseudoDate();
    }
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + super.getDate() + ")";
    }
}