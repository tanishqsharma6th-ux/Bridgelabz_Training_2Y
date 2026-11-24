package linkedlist.practiceproblems;

/** Q2: Doubly Linked List - Movie Management System */
public class Q2_MovieManagementDLL {

    static class Movie {
        String title, director; int year; double rating;
        Movie prev, next;
        Movie(String title, String director, int year, double rating) {
            this.title = title; this.director = director; this.year = year; this.rating = rating;
        }
        public String toString() {
            return title + " | " + director + " | " + year + " | Rating: " + rating;
        }
    }

    private Movie head, tail;
    private int size;

    public void addAtBeginning(String t, String d, int y, double r) {
        Movie m = new Movie(t, d, y, r);
        if (head == null) head = tail = m;
        else { m.next = head; head.prev = m; head = m; }
        size++;
    }

    public void addAtEnd(String t, String d, int y, double r) {
        Movie m = new Movie(t, d, y, r);
        if (tail == null) head = tail = m;
        else { tail.next = m; m.prev = tail; tail = m; }
        size++;
    }

    /** position is 1-based */
    public void addAtPosition(int pos, String t, String d, int y, double r) {
        if (pos < 1 || pos > size + 1) { System.out.println("Invalid position: " + pos); return; }
        if (pos == 1) { addAtBeginning(t, d, y, r); return; }
        if (pos == size + 1) { addAtEnd(t, d, y, r); return; }
        Movie cur = head;
        for (int i = 1; i < pos; i++) cur = cur.next;   // node currently at 'pos'
        Movie m = new Movie(t, d, y, r);
        m.prev = cur.prev; m.next = cur;
        cur.prev.next = m; cur.prev = m;
        size++;
    }

    public boolean removeByTitle(String title) {
        Movie cur = head;
        while (cur != null && !cur.title.equalsIgnoreCase(title)) cur = cur.next;
        if (cur == null) return false;
        if (cur.prev != null) cur.prev.next = cur.next; else head = cur.next;
        if (cur.next != null) cur.next.prev = cur.prev; else tail = cur.prev;
        cur.prev = cur.next = null;
        size--;
        return true;
    }

    public void searchByDirector(String director) {
        boolean found = false;
        for (Movie m = head; m != null; m = m.next)
            if (m.director.equalsIgnoreCase(director)) { System.out.println(m); found = true; }
        if (!found) System.out.println("No movie found for director: " + director);
    }

    public void searchByRating(double rating) {
        boolean found = false;
        for (Movie m = head; m != null; m = m.next)
            if (m.rating == rating) { System.out.println(m); found = true; }
        if (!found) System.out.println("No movie found with rating: " + rating);
    }

    public void displayForward() {
        if (head == null) { System.out.println("No movies."); return; }
        for (Movie m = head; m != null; m = m.next) System.out.println(m);
    }

    public void displayReverse() {
        if (tail == null) { System.out.println("No movies."); return; }
        for (Movie m = tail; m != null; m = m.prev) System.out.println(m);
    }

    public boolean updateRating(String title, double newRating) {
        for (Movie m = head; m != null; m = m.next)
            if (m.title.equalsIgnoreCase(title)) { m.rating = newRating; return true; }
        return false;
    }

    public static void main(String[] args) {
        Q2_MovieManagementDLL list = new Q2_MovieManagementDLL();
        list.addAtEnd("Inception", "Christopher Nolan", 2010, 8.8);
        list.addAtBeginning("3 Idiots", "Rajkumar Hirani", 2009, 8.4);
        list.addAtEnd("Interstellar", "Christopher Nolan", 2014, 8.6);
        list.addAtPosition(2, "PK", "Rajkumar Hirani", 2014, 8.1);
        System.out.println("--- Forward ---");  list.displayForward();
        System.out.println("\n--- Reverse ---"); list.displayReverse();
        System.out.println("\n--- Search by Director: Christopher Nolan ---");
        list.searchByDirector("Christopher Nolan");
        System.out.println("\n--- Search by Rating: 8.1 ---");
        list.searchByRating(8.1);
        System.out.println("\nUpdate PK rating -> " + list.updateRating("PK", 8.3));
        System.out.println("Remove Inception -> " + list.removeByTitle("Inception"));
        System.out.println("\n--- After changes (forward) ---"); list.displayForward();
        System.out.println("\n--- After changes (reverse) ---"); list.displayReverse();
    }
}