package dexter.task;

import java.time.LocalDate;

/**
 * Provides input handling for user input of Deadline Task
 */
public class Deadline extends Task {
    /**
     * Constructs deadline task from saved file
     * @param description file record of task
     * @param ld method friendly date
     * @param mark mark if task is done
     */
    public Deadline(String description, LocalDate ld, String mark) {
        super(description, ld, mark);
    }

    /**
     * Constructs deadline task from user input
     * @param description user input of task
     * @param ld method friendly date
     */
    public Deadline(String description, LocalDate ld) {
        this(description, ld, "unmark");
    }

    /**
     * Returns details to write to txt file
     * @return details in specified format
     */
    @Override
    public String getAll() {
        return "D " + super.getAll() + "/by " + super.getPseudoDate();
    }

    /**
     * Returns string representation of task for user viewing
     * @return string of details for user
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + super.getDate() + ")";
    }
}
