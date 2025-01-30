package Dexter.Task;

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
    public String toString() {
        return "[E]" + super.toString() + " (from: " + super.getDate() + " " + from + ")" + " (to: " + to + ")";
    }
}