package dexter.tasklist;

import java.util.ArrayList;

import dexter.task.Task;

/**
 * Contains collection of Tasks objects for add, read, update and delete operations
 */
public class TaskList {
    private static final String LINE_BREAK = "\t____________________________________________________________\n";
    private ArrayList<Task> myList;

    public TaskList() {
        this.myList = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> lst) {
        this.myList = new ArrayList<>(lst);
    }

    public void add(Task t) {
        this.myList.add(t);
    }
    public void remove(int i) {
        this.myList.remove(i);
    }

    public Task get(int i) {
        return this.myList.get(i);
    }
    public int size() {
        return this.myList.size();
    }

    /**
     * Calls on methods from TaskList for the purpose of writing to txt file
     * @return string of data preserved in storage format
     */
    public String toSave() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < myList.size(); i++) {
            String s = myList.get(i).getAll() + "\n";
            builder.append(s);
        }
        return builder.toString();
    }

    /**
     * Iterates through TaskList to find relevant Tasks
     * @param keyword Word to be found
     * @return List of Tasks containing word
     */
    public TaskList findKeyword(String keyword) {
        ArrayList<Task> keywordList = new ArrayList<>();
        for (int i = 0; i < myList.size(); i++) {
            Task a = this.myList.get(i);
            String b = a.getAll();
            if (b.contains(keyword)) {
                keywordList.add(a);
            }
        }
        return new TaskList(keywordList);
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(LINE_BREAK);
        for (int i = 0; i < myList.size(); i++) {
            String s = "\t" + String.valueOf(i + 1) + ". " + myList.get(i) + "\n";
            builder.append(s);
        }
        builder.append(LINE_BREAK);
        return builder.toString();
    }
}
