package dexter.task;

import java.time.LocalDate;

/**
 * Provides input handling for user input of ToDo Task
 */
public class ToDo extends Task {
    /**
     * Constructs ToDo task from saved file
     * @param description file record of task
     * @param mark mark if task is done
     */
    public ToDo(String description, String mark) {
        super(description, null, mark);
//            super.changeDoneStatus(mark);
    }

    /**
     * Constructs ToDo task from user input
     * @param description user input of task
     */
    public ToDo(String description) {
        this(description, "unmark");
    }

    /**
     * Returns details to write to txt file
     * @return details in specified format
     */
    @Override
    public String getAll() {
        return "T " + super.getAll();
    }

    /**
     * Returns false by default for program logic
     * @param t method friendly input
     * @return
     */
    @Override
    public boolean isDue(LocalDate t) {
        return false;
    }

    /**
     * Returns string representation of task for user viewing
     * @return string of details for user
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}