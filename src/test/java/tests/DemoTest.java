package tests;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import dexter.parser.Parser;
import dexter.storage.Storage;
import dexter.task.Deadline;
import dexter.task.Task;
import dexter.tasklist.TaskList;


public class DemoTest {

    @Test
    /*
    check if the parser is working correctly
     */
    public void parserTest1() {
        LocalDate ld = LocalDate.parse("2025-01-02");
        String description = "buy a new yacht";
        String mark = "unmark";
        Task sample = new Deadline(description, ld, mark);
        String descript = "buy a new yacht /by 2025-01-02";
        Task actual = Parser.equalsDeadline(descript, mark);
        assert(actual.toString().equals(sample.toString()));
    }

    @Test
    public void saveTest() {
        TaskList myExtra;
        TaskList myCopy;
        TaskList myAfter;

        Storage storageE = new Storage("Extra.txt");
        Storage storageD = new Storage("data.txt");
        try {
            myExtra = new TaskList(storageE.load());
            myCopy = new TaskList(storageD.load());
            storageD.save(myExtra);

            Storage after = new Storage("data.txt");
            myAfter = new TaskList(after.load());
            assert(myAfter.toString().equals(myExtra.toString()));
            storageD.save(myCopy);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}
