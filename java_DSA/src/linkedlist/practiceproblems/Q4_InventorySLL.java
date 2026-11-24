package linkedlist.practiceproblems;

import java.util.Comparator;

/** Q4: Singly Linked List - Inventory Management System */
public class Q4_InventorySLL {

    static class Item {
        String name; int id; int quantity; double price;
        Item next;
        Item(String name, int id, int quantity, double price) {
            this.name = name; this.id = id; this.quantity = quantity; this.price = price;
        }
        public String toString() {
            return "ID: " + id + " | " + name + " | Qty: " + quantity + " | Price: " + price;
        }
    }

    private Item head;
    private int size;

    public void addAtBeginning(String n, int id, int q, double p) {
        Item i = new Item(n, id, q, p);
        i.next = head; head = i; size++;
    }

    public void addAtEnd(String n, int id, int q, double p) {
        Item i = new Item(n, id, q, p);
        if (head == null) head = i;
        else { Item t = head; while (t.next != null) t = t.next; t.next = i; }
        size++;
    }

    /** position is 1-based */
    public void addAtPosition(int pos, String n, int id, int q, double p) {
        if (pos < 1 || pos > size + 1) { System.out.println("Invalid position: " + pos); return; }
        if (pos == 1) { addAtBeginning(n, id, q, p); return; }
        Item t = head;
        for (int k = 1; k < pos - 1; k++) t = t.next;
        Item i = new Item(n, id, q, p);
        i.next = t.next; t.next = i; size++;
    }

    public boolean removeById(int id) {
        if (head == null) return false;
        if (head.id == id) { head = head.next; size--; return true; }
        Item t = head;
        while (t.next != null && t.next.id != id) t = t.next;
        if (t.next == null) return false;
        t.next = t.next.next; size--;
        return true;
    }

    public boolean updateQuantity(int id, int newQty) {
        Item i = searchById(id);
        if (i == null) return false;
        i.quantity = newQty;
        return true;
    }

    public Item searchById(int id) {
        for (Item t = head; t != null; t = t.next) if (t.id == id) return t;
        return null;
    }

    public Item searchByName(String name) {
        for (Item t = head; t != null; t = t.next) if (t.name.equalsIgnoreCase(name)) return t;
        return null;
    }

    public double totalValue() {
        double total = 0;
        for (Item t = head; t != null; t = t.next) total += t.price * t.quantity;
        return total;
    }

    /** field: "name" or "price"; ascending true/false */
    public void sort(String field, boolean ascending) {
        Comparator<Item> c = field.equalsIgnoreCase("name")
                ? Comparator.comparing((Item i) -> i.name.toLowerCase())
                : Comparator.comparingDouble((Item i) -> i.price);
        if (!ascending) c = c.reversed();
        head = mergeSort(head, c);
    }

    private Item mergeSort(Item h, Comparator<Item> c) {
        if (h == null || h.next == null) return h;
        Item slow = h, fast = h.next;
        while (fast != null && fast.next != null) { slow = slow.next; fast = fast.next.next; }
        Item second = slow.next;
        slow.next = null;
        return merge(mergeSort(h, c), mergeSort(second, c), c);
    }

    private Item merge(Item a, Item b, Comparator<Item> c) {
        Item dummy = new Item("", 0, 0, 0), tail = dummy;
        while (a != null && b != null) {
            if (c.compare(a, b) <= 0) { tail.next = a; a = a.next; }
            else { tail.next = b; b = b.next; }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
    }

    public void display() {
        if (head == null) { System.out.println("Inventory empty."); return; }
        for (Item t = head; t != null; t = t.next) System.out.println(t);
    }

    public static void main(String[] args) {
        Q4_InventorySLL inv = new Q4_InventorySLL();
        inv.addAtEnd("Laptop", 101, 5, 55000);
        inv.addAtEnd("Mouse", 102, 50, 500);
        inv.addAtBeginning("Keyboard", 103, 30, 1200);
        inv.addAtPosition(2, "Monitor", 104, 10, 9000);
        System.out.println("--- Inventory ---"); inv.display();
        System.out.println("\nSearch ID 102: " + inv.searchById(102));
        System.out.println("Search name 'monitor': " + inv.searchByName("monitor"));
        inv.updateQuantity(102, 75);
        System.out.println("\nTotal value: " + inv.totalValue());
        System.out.println("\n--- Sorted by Name (asc) ---");   inv.sort("name", true);   inv.display();
        System.out.println("\n--- Sorted by Price (desc) ---"); inv.sort("price", false); inv.display();
        System.out.println("\nRemove 101 -> " + inv.removeById(101));
        System.out.println("\n--- After removal ---"); inv.display();
        System.out.println("Total value: " + inv.totalValue());
    }
}
