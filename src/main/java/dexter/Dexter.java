package dexter;

import java.io.FileNotFoundException;

import dexter.storage.Storage;
import dexter.tasklist.TaskList;
import dexter.ui.Ui;

/**
 * Serves as root file for execution of code
 */
public class Dexter {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a Dexter object based on the pre-existing tasks in filePath (if any)
     * @param filePath string of path to the file to read
     */
    public Dexter(String filePath) {
        storage = new Storage(filePath);

        try {
            ui = new Ui();
            tasks = new TaskList(storage.load());
        } catch (FileNotFoundException e) {
            ui.showError();
            tasks = new TaskList();
        }
    }

    /**
     * Processes the task list and provides response to user
     */
    public void run() {
        tasks = ui.run(tasks);
        storage.save(tasks);
    }

    public static void main(String[] args) {
        Dexter d = new Dexter("data.txt");
        d.run();
    }
}
