package linkedlist.practiceproblems;

/** Q7: Singly Linked List - Social Media Friend Connections (friend list = nested linked list) */
public class Q7_SocialFriendsSLL {

    static class FriendNode {
        int id; FriendNode next;
        FriendNode(int id) { this.id = id; }
    }

    static class User {
        int id; String name; int age;
        FriendNode friends;          // head of nested friend-ID list
        User next;
        User(int id, String name, int age) { this.id = id; this.name = name; this.age = age; }

        boolean hasFriend(int fid) {
            for (FriendNode f = friends; f != null; f = f.next) if (f.id == fid) return true;
            return false;
        }
        void addFriend(int fid) {
            FriendNode n = new FriendNode(fid);
            n.next = friends; friends = n;
        }
        boolean removeFriend(int fid) {
            if (friends == null) return false;
            if (friends.id == fid) { friends = friends.next; return true; }
            FriendNode f = friends;
            while (f.next != null && f.next.id != fid) f = f.next;
            if (f.next == null) return false;
            f.next = f.next.next;
            return true;
        }
        int friendCount() {
            int c = 0;
            for (FriendNode f = friends; f != null; f = f.next) c++;
            return c;
        }
    }

    private User head;

    public void addUser(int id, String name, int age) {
        User u = new User(id, name, age);
        if (head == null) head = u;
        else { User t = head; while (t.next != null) t = t.next; t.next = u; }
    }

    public User searchById(int id) {
        for (User u = head; u != null; u = u.next) if (u.id == id) return u;
        return null;
    }

    public User searchByName(String name) {
        for (User u = head; u != null; u = u.next) if (u.name.equalsIgnoreCase(name)) return u;
        return null;
    }

    public boolean addFriendConnection(int a, int b) {
        User ua = searchById(a), ub = searchById(b);
        if (ua == null || ub == null || a == b) { System.out.println("Invalid users."); return false; }
        if (ua.hasFriend(b)) { System.out.println("Already friends."); return false; }
        ua.addFriend(b);
        ub.addFriend(a);
        return true;
    }

    public boolean removeFriendConnection(int a, int b) {
        User ua = searchById(a), ub = searchById(b);
        if (ua == null || ub == null) return false;
        boolean r1 = ua.removeFriend(b), r2 = ub.removeFriend(a);
        return r1 && r2;
    }

    public void mutualFriends(int a, int b) {
        User ua = searchById(a), ub = searchById(b);
        if (ua == null || ub == null) { System.out.println("Invalid users."); return; }
        System.out.print("Mutual friends of " + ua.name + " and " + ub.name + ": ");
        boolean found = false;
        for (FriendNode f = ua.friends; f != null; f = f.next) {
            if (ub.hasFriend(f.id)) {
                System.out.print(searchById(f.id).name + " ");
                found = true;
            }
        }
        System.out.println(found ? "" : "None");
    }

    public void displayFriends(int id) {
        User u = searchById(id);
        if (u == null) { System.out.println("User not found."); return; }
        System.out.print(u.name + "'s friends: ");
        if (u.friends == null) { System.out.println("None"); return; }
        for (FriendNode f = u.friends; f != null; f = f.next) {
            User fr = searchById(f.id);
            System.out.print((fr != null ? fr.name : "?") + "(" + f.id + ") ");
        }
        System.out.println();
    }

    public void countFriendsForAll() {
        for (User u = head; u != null; u = u.next)
            System.out.println(u.name + " (ID " + u.id + ") has " + u.friendCount() + " friend(s)");
    }

    public static void main(String[] args) {
        Q7_SocialFriendsSLL net = new Q7_SocialFriendsSLL();
        net.addUser(1, "Aarav", 22);
        net.addUser(2, "Diya", 21);
        net.addUser(3, "Ishaan", 23);
        net.addUser(4, "Meera", 20);
        net.addUser(5, "Rohan", 24);

        net.addFriendConnection(1, 2);
        net.addFriendConnection(1, 3);
        net.addFriendConnection(2, 3);
        net.addFriendConnection(2, 4);
        net.addFriendConnection(3, 5);

        net.displayFriends(1);
        net.displayFriends(2);
        System.out.println();
        net.mutualFriends(1, 2);
        net.mutualFriends(1, 4);
        System.out.println("\nSearch by name 'Meera': ID " + net.searchByName("Meera").id);
        System.out.println("Search by ID 5: " + net.searchById(5).name);
        System.out.println("\n--- Friend counts ---");
        net.countFriendsForAll();
        System.out.println("\nRemove 1-2 -> " + net.removeFriendConnection(1, 2));
        net.displayFriends(1);
        net.displayFriends(2);
        net.mutualFriends(1, 2);
    }
}
