package Dexter.Task;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Task {
    private String description;
    private boolean isDone;
    private LocalDate t;

    public Task(String description, LocalDate t, String bool) {
        this.description = description;
        this.isDone = bool.equals("mark");
        this.t = t;
    }
    public void changeDoneStatus(String bool) {
        this.isDone = bool.equals("mark");
    }
    public boolean isDue(LocalDate t) {
        if (this.t == null) {
            return false;
        }
        return this.t.equals(t);
    }
    public String getDate() {
        if (this.t == null) { return null; }
        return this.t.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }
    @Override
    public String toString() {
        String mark = (this.isDone) ? "X" : " ";
        return "[" + mark + "] " + this.description;
    }
}