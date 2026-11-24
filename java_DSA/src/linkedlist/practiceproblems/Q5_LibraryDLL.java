package linkedlist.practiceproblems;

/** Q5: Doubly Linked List - Library Management System */
public class Q5_LibraryDLL {

    static class Book {
        String title, author, genre; int id; boolean available;
        Book prev, next;
        Book(String title, String author, String genre, int id, boolean available) {
            this.title = title; this.author = author; this.genre = genre;
            this.id = id; this.available = available;
        }
        public String toString() {
            return "ID: " + id + " | " + title + " | " + author + " | " + genre
                    + " | " + (available ? "Available" : "Issued");
        }
    }

    private Book head, tail;
    private int count;

    public void addAtBeginning(String t, String a, String g, int id, boolean av) {
        Book b = new Book(t, a, g, id, av);
        if (head == null) head = tail = b;
        else { b.next = head; head.prev = b; head = b; }
        count++;
    }

    public void addAtEnd(String t, String a, String g, int id, boolean av) {
        Book b = new Book(t, a, g, id, av);
        if (tail == null) head = tail = b;
        else { tail.next = b; b.prev = tail; tail = b; }
        count++;
    }

    /** position is 1-based */
    public void addAtPosition(int pos, String t, String a, String g, int id, boolean av) {
        if (pos < 1 || pos > count + 1) { System.out.println("Invalid position: " + pos); return; }
        if (pos == 1) { addAtBeginning(t, a, g, id, av); return; }
        if (pos == count + 1) { addAtEnd(t, a, g, id, av); return; }
        Book cur = head;
        for (int i = 1; i < pos; i++) cur = cur.next;
        Book b = new Book(t, a, g, id, av);
        b.prev = cur.prev; b.next = cur;
        cur.prev.next = b; cur.prev = b;
        count++;
    }

    public boolean removeById(int id) {
        Book cur = head;
        while (cur != null && cur.id != id) cur = cur.next;
        if (cur == null) return false;
        if (cur.prev != null) cur.prev.next = cur.next; else head = cur.next;
        if (cur.next != null) cur.next.prev = cur.prev; else tail = cur.prev;
        cur.prev = cur.next = null;
        count--;
        return true;
    }

    public void searchByTitle(String title) {
        boolean found = false;
        for (Book b = head; b != null; b = b.next)
            if (b.title.equalsIgnoreCase(title)) { System.out.println(b); found = true; }
        if (!found) System.out.println("No book titled: " + title);
    }

    public void searchByAuthor(String author) {
        boolean found = false;
        for (Book b = head; b != null; b = b.next)
            if (b.author.equalsIgnoreCase(author)) { System.out.println(b); found = true; }
        if (!found) System.out.println("No book by author: " + author);
    }

    public boolean updateAvailability(int id, boolean available) {
        for (Book b = head; b != null; b = b.next)
            if (b.id == id) { b.available = available; return true; }
        return false;
    }

    public void displayForward() {
        if (head == null) { System.out.println("Library is empty."); return; }
        for (Book b = head; b != null; b = b.next) System.out.println(b);
    }

    public void displayReverse() {
        if (tail == null) { System.out.println("Library is empty."); return; }
        for (Book b = tail; b != null; b = b.prev) System.out.println(b);
    }

    public int countBooks() { return count; }

    public static void main(String[] args) {
        Q5_LibraryDLL lib = new Q5_LibraryDLL();
        lib.addAtEnd("The Alchemist", "Paulo Coelho", "Fiction", 1, true);
        lib.addAtEnd("Clean Code", "Robert Martin", "Technology", 2, true);
        lib.addAtBeginning("Wings of Fire", "A.P.J. Abdul Kalam", "Biography", 3, false);
        lib.addAtPosition(2, "Brida", "Paulo Coelho", "Fiction", 4, true);
        System.out.println("--- Forward ---");  lib.displayForward();
        System.out.println("\n--- Reverse ---"); lib.displayReverse();
        System.out.println("\nTotal books: " + lib.countBooks());
        System.out.println("\n--- Search title 'Clean Code' ---"); lib.searchByTitle("Clean Code");
        System.out.println("\n--- Search author 'Paulo Coelho' ---"); lib.searchByAuthor("Paulo Coelho");
        System.out.println("\nIssue book 2 -> " + lib.updateAvailability(2, false));
        System.out.println("Remove book 3 -> " + lib.removeById(3));
        System.out.println("\n--- After changes ---"); lib.displayForward();
        System.out.println("Total books: " + lib.countBooks());
    }
}
