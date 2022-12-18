package aoc2022.day17;

public class ManualCalc {

	public static void main(String[] args) {
		int turn = 999460;
		int cycleT = 999460 - 997755;
		int cycleH = 1534637 - 1532019;
		
		long l = 1_000_000_000_000l;
		long m = l - turn; 
		long d = m / cycleT;
		long end = l - (d * cycleT);
		long bonus = d * cycleH;
		
		System.out.println("End: " + end + " Bonus: " + bonus);
	}

}
