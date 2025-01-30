import java.util.ArrayList;


public class TaskList {
    String LINE = "\t____________________________________________________________\n";
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
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(LINE);
        for (int i = 0; i < myList.size(); i++) {
            String s = "\t" + String.valueOf(i + 1) + ". " + myList.get(i);
            builder.append(s);
        }
        builder.append(LINE);
        return builder.toString();
    }
}
