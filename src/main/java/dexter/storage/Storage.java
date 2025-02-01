package dexter.storage;

import dexter.taskList.TaskList;
import dexter.ui.Ui;
import dexter.task.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private File f;
    private ArrayList<Task> myList;
    public Storage(String filePath) {
        this.f = new File(filePath);
        this.myList = new ArrayList<>();
    }
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
