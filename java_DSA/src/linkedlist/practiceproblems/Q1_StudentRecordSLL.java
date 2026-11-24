package linkedlist.practiceproblems;

/** Q1: Singly Linked List - Student Record Management */
public class Q1_StudentRecordSLL {

    static class Student {
        int rollNo; String name; int age; String grade;
        Student next;
        Student(int rollNo, String name, int age, String grade) {
            this.rollNo = rollNo; this.name = name; this.age = age; this.grade = grade;
        }
        public String toString() {
            return "Roll: " + rollNo + " | Name: " + name + " | Age: " + age + " | Grade: " + grade;
        }
    }

    private Student head;
    private int size;

    public void addAtBeginning(int roll, String name, int age, String grade) {
        Student s = new Student(roll, name, age, grade);
        s.next = head;
        head = s;
        size++;
    }

    public void addAtEnd(int roll, String name, int age, String grade) {
        Student s = new Student(roll, name, age, grade);
        if (head == null) head = s;
        else {
            Student t = head;
            while (t.next != null) t = t.next;
            t.next = s;
        }
        size++;
    }

    /** position is 1-based */
    public void addAtPosition(int pos, int roll, String name, int age, String grade) {
        if (pos < 1 || pos > size + 1) { System.out.println("Invalid position: " + pos); return; }
        if (pos == 1) { addAtBeginning(roll, name, age, grade); return; }
        Student t = head;
        for (int i = 1; i < pos - 1; i++) t = t.next;
        Student s = new Student(roll, name, age, grade);
        s.next = t.next;
        t.next = s;
        size++;
    }

    public boolean deleteByRoll(int roll) {
        if (head == null) { System.out.println("List is empty."); return false; }
        if (head.rollNo == roll) { Student d = head; head = head.next; d.next = null; size--; return true; }
        Student t = head;
        while (t.next != null && t.next.rollNo != roll) t = t.next;
        if (t.next == null) return false;
        Student d = t.next;
        t.next = d.next;
        d.next = null;
        size--;
        return true;
    }

    public Student search(int roll) {
        for (Student t = head; t != null; t = t.next) if (t.rollNo == roll) return t;
        return null;
    }

    public void display() {
        if (head == null) { System.out.println("No records."); return; }
        for (Student t = head; t != null; t = t.next) System.out.println(t);
    }

    public boolean updateGrade(int roll, String newGrade) {
        Student s = search(roll);
        if (s == null) return false;
        s.grade = newGrade;
        return true;
    }

    public static void main(String[] args) {
        Q1_StudentRecordSLL list = new Q1_StudentRecordSLL();
        list.addAtEnd(102, "Amit", 20, "B");
        list.addAtBeginning(101, "Riya", 19, "A");
        list.addAtEnd(104, "Karan", 21, "C");
        list.addAtPosition(3, 103, "Neha", 20, "A");
        System.out.println("--- All records ---");
        list.display();
        System.out.println("\nSearch 103: " + list.search(103));
        System.out.println("Search 999: " + list.search(999));
        System.out.println("\nUpdate grade of 104 -> " + list.updateGrade(104, "B+"));
        System.out.println("Delete 102 -> " + list.deleteByRoll(102));
        System.out.println("Delete 999 -> " + list.deleteByRoll(999));
        System.out.println("\n--- After changes ---");
        list.display();
    }
}
