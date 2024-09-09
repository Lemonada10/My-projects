// Steven DY, 40283742
// Firas AL HADDAD, 40283180

public class Main {
	public static void main(String[] args) {
		
		PQ<Integer,Character> pQ = new PQ(1, true);
		pQ.insert(3,'C');
		pQ.insert(1, 'B');
		pQ.insert(4, 'W');
		pQ.insert(0, 'V');
		pQ.insert(6, 'B');
		pQ.insert(5, 'W');
		pQ.insert(8, 'V');
        System.out.println("Original heap:");
        pQ.print();
        System.out.println();
        System.out.println("After toggle:");
		pQ.toggle();
        pQ.print();
        System.out.println();

        System.out.println("Adding new entries");
		Entry<Integer, Character> entry = new Entry<>(3,'C');
        pQ.insert(12, 'Z');
        pQ.insert(13, 'L');
        pQ.insert(3, 'A');
        pQ.insert(7, 'P');
        pQ.print();
        System.out.println();

        System.out.println("Removing entry (3:C), replacing the key of (0:V) with 9, and replacing the value of (13:L) with Z");
		pQ.remove(entry);
		pQ.replaceKey( new Entry<>(0,'V'), 9);
		pQ.replaceValue(new Entry<>(13,'L'), 'Z');
        pQ.print();
        System.out.println();

        System.out.println("Printing the top element:");
		System.out.println("Top: "  + pQ.top());
        System.out.println("Printing heap size:");
		System.out.println(pQ.size());
        System.out.println("Checking if heap is empty:");
		System.out.println(pQ.isEmpty());
        System.out.println("Checking the state of heap, Min or Max?");
		System.out.println(pQ.state());

        System.out.println("");
        System.out.println("Removing top entry:");
        pQ.removeTop();
        pQ.print();
        System.out.println();

        System.out.println("Inserting entry (11:X):");
        pQ.insert(11, 'X');
        pQ.print();
        System.out.println("");

        System.out.println("Toggle the heap:");
        pQ.toggle();
        pQ.print();
        System.out.println();

        System.out.println("Removing top entry:");
        pQ.removeTop();
        pQ.print();
        System.out.println();

        System.out.println("Checking heap state, Min or Max?");
        System.out.println(pQ.state());

        System.out.println("Insert entry (2:F):");
        pQ.insert(2, 'F');
        pQ.print();
        System.out.println();

        System.out.println("Calling removing top twice:");
        pQ.removeTop();
        pQ.removeTop();
        pQ.print();
        System.out.println();

        System.out.println("Insert entry (7:M):");
        pQ.insert(7, 'M');
        pQ.print();
        System.out.println();

        System.out.println("Finally, toggling the heap again");
        pQ.toggle();
        pQ.print();
        
	}
}
