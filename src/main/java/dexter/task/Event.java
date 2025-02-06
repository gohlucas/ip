package dexter.task;

import java.time.LocalDate;

/**
 * Provides input handling for user input of Event Task
 */
public class Event extends Task {
    private String from;
    private String to;

    /**
     * Constructs event task from saved file
     * @param description file record of task
     * @param ld method friendly date
     * @param from starting time of event
     * @param to ending time of event
     * @param mark mark if task is done
     */
    public Event(String description, LocalDate ld, String from, String to, String mark) {
        super(description, ld, mark);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs event task from user input
     * @param description user input of task
     * @param ld method friendly version of date
     * @param from starting time of event
     * @param to ending time of event
     */
    public Event(String description, LocalDate ld, String from, String to) {
        this(description, ld, from, to, "unmark");
    }

    /**
     * Returns details to write to txt file
     * @return details in specified format
     */
    @Override
    public String getAll() {
        return "E " + super.getAll() + "/from " + super.getPseudoDate() + " " + this.from + " /to " + this.to;
    }

    /**
     * Returns string representation of task for user viewing
     * @return string of details for user
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + super.getDate() + " " + from + ")" + " (to: " + to + ")";
    }
}
