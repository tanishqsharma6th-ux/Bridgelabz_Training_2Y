package linkedlist.practiceproblems;

/** Q8: Doubly Linked List - Undo/Redo for a Text Editor (history limited to 10 states) */
public class Q8_UndoRedoDLL {

    static class State {
        String text;
        State prev, next;
        State(String text) { this.text = text; }
    }

    private static final int LIMIT = 10;
    private State head, current;
    private int size;

    public Q8_UndoRedoDLL() {
        head = current = new State("");     // initial empty document
        size = 1;
    }

    /** Called on every type/action: adds a new state after the current one. */
    public void addState(String newText) {
        // a new action invalidates the redo history
        for (State s = current.next; s != null; s = s.next) size--;
        current.next = null;

        State s = new State(newText);
        s.prev = current;
        current.next = s;
        current = s;
        size++;

        if (size > LIMIT) {                 // drop the oldest state
            State old = head;
            head = head.next;
            head.prev = null;
            old.next = null;
            size--;
        }
    }

    public void type(String word) { addState(current.text + word); }

    public void undo() {
        if (current.prev == null) { System.out.println("Nothing to undo."); return; }
        current = current.prev;
    }

    public void redo() {
        if (current.next == null) { System.out.println("Nothing to redo."); return; }
        current = current.next;
    }

    public void displayCurrent() { System.out.println("Text: \"" + current.text + "\""); }

    public int historySize() { return size; }

    public static void main(String[] args) {
        Q8_UndoRedoDLL ed = new Q8_UndoRedoDLL();
        ed.type("Hello");        ed.displayCurrent();
        ed.type(" World");       ed.displayCurrent();
        ed.type("!");            ed.displayCurrent();

        System.out.println("\n-- undo x2 --");
        ed.undo(); ed.displayCurrent();
        ed.undo(); ed.displayCurrent();

        System.out.println("\n-- redo x1 --");
        ed.redo(); ed.displayCurrent();

        System.out.println("\n-- new action after undo clears redo history --");
        ed.type(" Java");        ed.displayCurrent();
        ed.redo();               // nothing to redo

        System.out.println("\n-- history limit test (12 more edits) --");
        for (int i = 1; i <= 12; i++) ed.type(" " + i);
        System.out.println("History size: " + ed.historySize() + " (limit " + LIMIT + ")");
        for (int i = 0; i < 15; i++) ed.undo();     // can go back at most 9 steps
        ed.displayCurrent();
    }
}
