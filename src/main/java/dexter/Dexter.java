package dexter;

import java.io.FileNotFoundException;

import dexter.storage.Storage;
import dexter.tasklist.TaskList;
import dexter.ui.Ui;

/**
 * Serves as root file for execution of code
 */
public class Dexter {
    private static final String FILE_PATH = "data.txt";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a Dexter object based on the pre-existing tasks in filePath (if any)
     */
    public Dexter() {
        storage = new Storage(FILE_PATH);
        assert storage != null : "Storage should be initialised";
        try {
            ui = new Ui();
            tasks = new TaskList(storage.load());
        } catch (FileNotFoundException e) {
            ui.showError();
            tasks = new TaskList();
        }
        assert tasks != null : "TaskList should be initialised";
    }

    /**
     * Processes the task list and provides response to user
     */
    public String run(String input) {
        String res = ui.run(tasks, input);
        storage.save(tasks);
        return res;
    }

    public String getResponse(String input) {
        assert input != null : "Input should not be null";
        return this.run(input);
    }
    public static void main(String[] args) {
    }
}

