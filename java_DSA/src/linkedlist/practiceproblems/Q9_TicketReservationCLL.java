package linkedlist.practiceproblems;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Q9: Circular Linked List - Online Ticket Reservation System */
public class Q9_TicketReservationCLL {

    static class Ticket {
        int id; String customer, movie, seat; LocalDateTime bookingTime;
        Ticket next;
        Ticket(int id, String customer, String movie, String seat) {
            this.id = id; this.customer = customer; this.movie = movie; this.seat = seat;
            this.bookingTime = LocalDateTime.now();
        }
        public String toString() {
            return "Ticket " + id + " | " + customer + " | " + movie + " | Seat " + seat
                    + " | " + bookingTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        }
    }

    private Ticket tail;    // tail.next = first ticket
    private int count;

    public void addTicket(int id, String customer, String movie, String seat) {
        Ticket t = new Ticket(id, customer, movie, seat);
        if (tail == null) { tail = t; t.next = t; }
        else { t.next = tail.next; tail.next = t; tail = t; }
        count++;
    }

    public boolean removeTicket(int id) {
        if (tail == null) { System.out.println("No tickets booked."); return false; }
        Ticket prev = tail, cur = tail.next;
        do {
            if (cur.id == id) {
                if (cur == prev) tail = null;
                else {
                    prev.next = cur.next;
                    if (cur == tail) tail = prev;
                }
                cur.next = null;
                count--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        } while (prev != tail);
        return false;
    }

    public void displayTickets() {
        if (tail == null) { System.out.println("No tickets booked."); return; }
        Ticket t = tail.next;
        do { System.out.println(t); t = t.next; } while (t != tail.next);
    }

    public void searchByCustomer(String name) {
        searchBy(name, true);
    }

    public void searchByMovie(String movie) {
        searchBy(movie, false);
    }

    private void searchBy(String key, boolean byCustomer) {
        if (tail == null) { System.out.println("No tickets booked."); return; }
        boolean found = false;
        Ticket t = tail.next;
        do {
            String field = byCustomer ? t.customer : t.movie;
            if (field.equalsIgnoreCase(key)) { System.out.println(t); found = true; }
            t = t.next;
        } while (t != tail.next);
        if (!found) System.out.println("No ticket found for: " + key);
    }

    public int totalTickets() { return count; }

    public static void main(String[] args) {
        Q9_TicketReservationCLL sys = new Q9_TicketReservationCLL();
        sys.addTicket(1001, "Ananya", "Inception", "A1");
        sys.addTicket(1002, "Vikram", "Interstellar", "B4");
        sys.addTicket(1003, "Sneha", "Inception", "A2");
        sys.addTicket(1004, "Ananya", "3 Idiots", "C7");
        System.out.println("--- All tickets ---"); sys.displayTickets();
        System.out.println("\nTotal booked: " + sys.totalTickets());
        System.out.println("\n--- Search customer 'Ananya' ---"); sys.searchByCustomer("Ananya");
        System.out.println("\n--- Search movie 'Inception' ---");  sys.searchByMovie("Inception");
        System.out.println("\nRemove 1002 -> " + sys.removeTicket(1002));
        System.out.println("Remove 9999 -> " + sys.removeTicket(9999));
        System.out.println("\n--- After removal ---"); sys.displayTickets();
        System.out.println("Total booked: " + sys.totalTickets());
    }
}
