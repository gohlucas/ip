import java.io.FileNotFoundException;

public class Dexter {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Dexter(String filePath) {
        storage = new Storage(filePath);

        try {
            ui = new Ui();
            tasks = new TaskList(storage.load());
        } catch (FileNotFoundException e) {
            ui.showingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.run(tasks);
    }

    public static void main(String[] args) {
        Dexter d = new Dexter("data.txt");
        d.run();
    }
}
