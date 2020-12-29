package assign09;

/**
 * This class demonstrates how to use a hash table to store key-value pairs.
 * 
 * @author Erin Parker
 * @version March 19, 2020
 */
public class StudentHashDemo {

	public static void main(String[] args) {
		
		StudentBadHash alan = new StudentBadHash(1019999, "Alan", "Turing");
		StudentBadHash ada = new StudentBadHash(1004203, "Ada", "Lovelace");
		StudentBadHash edsgar = new StudentBadHash(1010661, "Edsgar", "Dijkstra");
		StudentBadHash grace = new StudentBadHash(1019941, "Grace", "Hopper");

		HashTable<StudentBadHash, Double> studentGpaTable = new HashTable<StudentBadHash, Double>();
		studentGpaTable.put(alan, 3.2);
		studentGpaTable.put(ada, 3.5);
		studentGpaTable.put(edsgar, 3.8);
		studentGpaTable.put(grace, 4.0);
		
		for(MapEntry<StudentBadHash, Double> e : studentGpaTable.entries())
			System.out.println("Student " + e.getKey() + " has GPA " + e.getValue() + ".");
	}
}