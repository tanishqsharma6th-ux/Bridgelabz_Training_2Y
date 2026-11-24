package linkedlist.practiceproblems;

import java.util.ArrayList;
import java.util.List;

/** Q6: Circular Linked List - Round Robin CPU Scheduling (all arrival times = 0) */
public class Q6_RoundRobinCLL {

    static class Process {
        int pid, burst, remaining, priority, completion;
        Process next;
        Process(int pid, int burst, int priority) {
            this.pid = pid; this.burst = burst; this.remaining = burst; this.priority = priority;
        }
    }

    private Process tail;   // tail.next is the head
    private int size;

    public void addProcess(int pid, int burst, int priority) {
        Process p = new Process(pid, burst, priority);
        if (tail == null) { tail = p; p.next = p; }
        else { p.next = tail.next; tail.next = p; tail = p; }
        size++;
    }

    public boolean removeProcess(int pid) {
        if (tail == null) return false;
        Process prev = tail, cur = tail.next;
        do {
            if (cur.pid == pid) {
                if (cur == prev) tail = null;           // only node
                else {
                    prev.next = cur.next;
                    if (cur == tail) tail = prev;
                }
                cur.next = null;
                size--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        } while (prev != tail);                          // one full cycle
        return false;
    }

    public void displayQueue() {
        if (tail == null) { System.out.println("  Queue: [empty]"); return; }
        StringBuilder sb = new StringBuilder("  Queue: ");
        Process p = tail.next;
        do { sb.append("P").append(p.pid).append("(rem=").append(p.remaining).append(") "); p = p.next; }
        while (p != tail.next);
        System.out.println(sb);
    }

    public void simulate(int quantum) {
        if (tail == null) { System.out.println("No processes."); return; }
        List<Process> finished = new ArrayList<>();
        int time = 0, round = 0;
        Process cur = tail.next;
        while (tail != null) {
            round++;
            System.out.println("Round " + round + ":");
            int n = size;
            for (int i = 0; i < n && tail != null; i++) {
                Process p = cur, nxt = p.next;
                int run = Math.min(quantum, p.remaining);
                System.out.println("  P" + p.pid + " runs " + run + " unit(s) [t=" + time + " -> " + (time + run) + "]");
                time += run;
                p.remaining -= run;
                if (p.remaining == 0) {
                    p.completion = time;
                    finished.add(p);
                    removeProcess(p.pid);
                    System.out.println("  P" + p.pid + " finished at t=" + time);
                }
                cur = nxt;
            }
            System.out.println("After round " + round + ":");
            displayQueue();
        }

        double totalWT = 0, totalTAT = 0;
        System.out.println("\nPID | Burst | Priority | Completion | Turn-Around | Waiting");
        for (Process p : finished) {
            int tat = p.completion;          // arrival = 0
            int wt = tat - p.burst;
            totalTAT += tat; totalWT += wt;
            System.out.printf("P%-2d | %5d | %8d | %10d | %11d | %7d%n", p.pid, p.burst, p.priority, p.completion, tat, wt);
        }
        System.out.printf("%nAverage Waiting Time    : %.2f%n", totalWT / finished.size());
        System.out.printf("Average Turn-Around Time: %.2f%n", totalTAT / finished.size());
    }

    public static void main(String[] args) {
        Q6_RoundRobinCLL rr = new Q6_RoundRobinCLL();
        rr.addProcess(1, 10, 2);
        rr.addProcess(2, 5, 1);
        rr.addProcess(3, 8, 3);
        System.out.println("Initial queue:");
        rr.displayQueue();
        System.out.println("\nTime quantum = 3\n");
        rr.simulate(3);
    }
}
