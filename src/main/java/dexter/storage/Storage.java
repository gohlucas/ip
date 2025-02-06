package dexter.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import dexter.task.Task;
import dexter.tasklist.TaskList;
import dexter.ui.Ui;



/**
 * Serves as an abstraction to store, process and save Tasks
 */
public class Storage {
    private File f;
    private ArrayList<Task> myList;

    /**
     * Constructs new Storage with reference to file input as parameter
     * @param filePath path to file in directory
     */
    public Storage(String filePath) {
        this.f = new File(filePath);
        this.myList = new ArrayList<>();
    }

    /**
     * Loads tasks in file and handles cases where it does not exist
     * @return Extracted tasks from file
     * @throws FileNotFoundException
     */
    public ArrayList<Task> load() throws FileNotFoundException {
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            String t = s.nextLine();
            Task p = Ui.createTask(t);
            myList.add(p);
        }
        s.close();
        return myList;
    }

    /**
     * Saves tasks in the current session into a txt file
     * @param lst TaskList containing tasks currently exisiting
     */
    public void save(TaskList lst) {
        String s = lst.toSave();
        try {
            FileWriter fw = new FileWriter(f, false);
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            System.out.println("error, IO Exception");
        }
    }
}
