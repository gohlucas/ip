package Dexter.Storage;

import Dexter.Ui.Ui;
import Dexter.Task.Task;

import java.io.File;
import java.io.FileNotFoundException;
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
//       System.out.println("There is no existing database, will start with no data");
    }
}
