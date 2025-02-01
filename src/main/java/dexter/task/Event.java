package dexter.task;

import java.time.LocalDate;

public class Event extends Task {
    private String from;
    private String to;

    public Event(String description, LocalDate ld, String from, String to, String mark) {
        super(description, ld, mark);
        this.from = from;
        this.to = to;
    }

    public Event(String description, LocalDate ld, String from, String to) {
        this(description, ld, from, to, "unmark");
    }
    @Override
    public String getAll() {
//        String tt1 = super.toString().replaceAll("\\(", "/");
//        String tt2 = tt1.replaceAll(":", "");
        return "E " + super.getAll() + "/from " + super.getPseudoDate() + " " + this.from + " /to " + this.to;
    }
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + super.getDate() + " " + from + ")" + " (to: " + to + ")";
    }
}