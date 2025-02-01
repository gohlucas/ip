package dexter.task;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Provides subclasses with blueprint of methods required
 */
public abstract class Task {
    private String description;
    private boolean isDone;
    private LocalDate t;

    /**
     * Constructs a task from user input
     * @param description user input of task
     * @param t method friendly input
     * @param bool mark if task is done
     */
    public Task(String description, LocalDate t, String bool) {
        this.description = description;
        this.isDone = bool.equals("mark");
        this.t = t;
    }

    /**
     * Negates the current state of isDone
     * @param bool modify this.isDone
     */
    public void changeDoneStatus(String bool) {
        this.isDone = bool.equals("mark");
    }

    /**
     * Returns whether the task is due
     * @param t method friendly input
     * @return whether task is due
     */
    public boolean isDue(LocalDate t) {
        if (this.t == null) {
            return false;
        }
        return this.t.equals(t);
    }

    /**
     * Returns the date in user targeted format
     * @return date in specified format
     */
    public String getDate() {
        if (this.t == null) { return null; }
        return this.t.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }

    /**
     * Returns the date in storage targeted format
     * @return date in specified format
     */
    public LocalDate getPseudoDate() {
        return this.t;
    }

    /**
     * Returns details of task for writing to txt file
     * @return details in specified format
     */
    public String getAll() {
        String i = this.isDone ? "1" : "0";
        return i + " " + this.description;
    }

    /**
     * Returns string representation of task for user viewing
     * @return string of details for user
     */
    @Override
    public String toString() {
        String mark = (this.isDone) ? "X" : " ";
        return "[" + mark + "] " + this.description;
    }
}