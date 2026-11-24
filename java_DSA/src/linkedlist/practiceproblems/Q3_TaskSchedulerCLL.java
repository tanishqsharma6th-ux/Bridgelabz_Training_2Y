package linkedlist.practiceproblems;

/** Q3: Circular Linked List - Task Scheduler */
public class Q3_TaskSchedulerCLL {

    static class Task {
        int id; String name; int priority; String dueDate;
        Task next;
        Task(int id, String name, int priority, String dueDate) {
            this.id = id; this.name = name; this.priority = priority; this.dueDate = dueDate;
        }
        public String toString() {
            return "ID: " + id + " | " + name + " | Priority: " + priority + " | Due: " + dueDate;
        }
    }

    private Task head, tail, current;
    private int size;

    public void addAtBeginning(int id, String name, int p, String due) {
        Task t = new Task(id, name, p, due);
        if (head == null) { head = tail = current = t; t.next = t; }
        else { t.next = head; head = t; tail.next = head; }
        size++;
    }

    public void addAtEnd(int id, String name, int p, String due) {
        Task t = new Task(id, name, p, due);
        if (head == null) { head = tail = current = t; t.next = t; }
        else { tail.next = t; t.next = head; tail = t; }
        size++;
    }

    /** position is 1-based */
    public void addAtPosition(int pos, int id, String name, int p, String due) {
        if (pos < 1 || pos > size + 1) { System.out.println("Invalid position: " + pos); return; }
        if (pos == 1) { addAtBeginning(id, name, p, due); return; }
        if (pos == size + 1) { addAtEnd(id, name, p, due); return; }
        Task prev = head;
        for (int i = 1; i < pos - 1; i++) prev = prev.next;
        Task t = new Task(id, name, p, due);
        t.next = prev.next;
        prev.next = t;
        size++;
    }

    public boolean removeById(int id) {
        if (head == null) { System.out.println("Scheduler is empty."); return false; }
        Task prev = tail, cur = head;
        do {
            if (cur.id == id) {
                if (cur == head && cur == tail) { head = tail = current = null; }
                else {
                    prev.next = cur.next;
                    if (cur == head) head = cur.next;
                    if (cur == tail) tail = prev;
                    if (cur == current) current = cur.next;
                }
                cur.next = null;
                size--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        } while (cur != head);
        return false;
    }

    public void viewCurrent() {
        if (current == null) System.out.println("No tasks.");
        else System.out.println("Current task: " + current);
    }

    public void moveToNext() {
        if (current == null) { System.out.println("No tasks."); return; }
        current = current.next;
        System.out.println("Moved to next -> " + current);
    }

    public void displayAll() {
        if (head == null) { System.out.println("No tasks."); return; }
        Task t = head;
        do { System.out.println(t); t = t.next; } while (t != head);   // stop after one cycle
    }

    public void searchByPriority(int priority) {
        if (head == null) { System.out.println("No tasks."); return; }
        boolean found = false;
        Task t = head;
        do {
            if (t.priority == priority) { System.out.println(t); found = true; }
            t = t.next;
        } while (t != head);
        if (!found) System.out.println("No task with priority " + priority);
    }

    public static void main(String[] args) {
        Q3_TaskSchedulerCLL s = new Q3_TaskSchedulerCLL();
        s.addAtEnd(1, "Write report", 2, "2025-01-10");
        s.addAtEnd(2, "Fix bug #42", 1, "2025-01-08");
        s.addAtBeginning(3, "Team meeting", 3, "2025-01-07");
        s.addAtPosition(2, 4, "Code review", 1, "2025-01-09");
        System.out.println("--- All tasks ---"); s.displayAll();
        System.out.println();
        s.viewCurrent();
        for (int i = 0; i < 5; i++) s.moveToNext();     // wraps around the circle
        System.out.println("\n--- Priority 1 ---"); s.searchByPriority(1);
        System.out.println("\nRemove task 2 -> " + s.removeById(2));
        System.out.println("Remove task 99 -> " + s.removeById(99));
        System.out.println("\n--- After removal ---"); s.displayAll();
    }
}
