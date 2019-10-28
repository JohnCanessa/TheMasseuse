import java.util.Scanner;

/*
 * 
 */
public class Solution {

	/*
	 * Index of longest appointment.
	 */
	static int longestIndex(int[] massages, int i) {

		// **** ****
		if (massages[i] > massages[i + 1]) {
			if (massages[i] > massages[i + 2])
				return i;
			else
				return i + 2;
		} else {
			if (massages[i + 1] > massages[i + 2])
				return i + 1;
			else
				return i + 2;
		}
	}

	/*
	 * Compute the maximum number of minutes. Recursive method. With memoization.
	 */
	static int maxMinutes(int[] massages, int i, int[] memo) {

		// **** end condition ****
		if (i >= massages.length)
			return 0;

		// // **** best with this reservation ****
		// int bestWith = massages[i] + maxMinutes(massages, i + 2);
		//
		// // **** best without this reservation ****
		// int bestWithOut = maxMinutes(massages, i + 1);
		//
		// // ???? ????
		// System.out.println("maxMinutes <<< i: " + i + " bestWith: " + bestWith + "
		// bestWithOut: " + bestWithOut);
		//
		// // **** best so far ****
		// return Math.max(bestWith, bestWithOut);

		// **** ****
		if (memo[i] == 0) {
			// {
			int bestIndex = massages[i] + maxMinutes(massages, i + 2, memo);
			int bestWithout = maxMinutes(massages, i + 1, memo);
			memo[i] = Math.max(bestIndex, bestWithout);

			// ???? ????
			System.out.println("maxMinutes <<< memo[" + i + "]: " + memo[i]);
		}

		// **** this value is computed once ****
		return memo[i];
	}

	/*
	 * Entry point to compute the maximum number of minutes. Non-recursive method.
	 */
	static int maxMinutes(int[] massages) {

		// **** allocate array for memoization ****
		int[] memo = new int[massages.length];

		// **** ****
		return maxMinutes(massages, 0, memo);
	}

	/*
	 * Same as with memoization but iterative.
	 */
	static int maxMinutesIterative(int[] massages) {

		// **** allocate array for memoization (need two extra slots) ****
		int[] memo = new int[massages.length + 2];

		// **** build memoization backwards ****
		for (int i = massages.length - 1; i >= 0; i--) {
			int bestWith = massages[i] + memo[i + 2];
			int bestWithout = memo[i + 1];
			memo[i] = Math.max(bestWith, bestWithout);

			// ???? ????
			System.out.printf("maxMinutesIterative <<< bestWithout: %3d bestWith: %3d\n", bestWithout, bestWith);
			System.out.printf("maxMinutesIterative <<< memo[%d]: %3d\n", i, memo[i]);
		}

		// **** optimum time ****
		return memo[0];
	}

	/*
	 * Same as with memoization and iterative but optimal O(n) and O(1).
	 */
	static int maxMinutesOptimum(int[] massages) {

		// **** ****
		int oneWay = 0;
		int twoWay = 0;

		// **** ****
		for (int i = massages.length - 1; i >= 0; i--) {
			int bestWith = massages[i] + twoWay;
			int bestWithout = oneWay;
			int current = Math.max(bestWith, bestWithout);
			twoWay = oneWay;
			oneWay = current;

			// ???? ????
			// System.out.println("maxMinutesOptimum <<< twoWay: " + twoWay + " oneWay: " +
			// oneWay);
			System.out.printf("maxMinutesOptimum <<< twoWay: %3d oneWay: %3d\n", twoWay, oneWay);
		}

		// **** ****
		return oneWay;
	}

	/*
	 * Test scaffolding.
	 */
	public static void main(String[] args) {

		// **** ****
		Scanner sc = new Scanner(System.in);

		// **** ****
		int n = sc.nextInt();

		// ???? ????
		System.out.println("main <<<    n: " + n);

		// **** ****
		int[] massages = new int[n];

		// **** ****
		for (int i = 0; i < n; i++) {
			massages[i] = sc.nextInt();
		}

		// ???? ????
		System.out.print("main <<< apps: [");
		for (int i = 0; i < massages.length; i++) {
			if (i + 1 < massages.length)
				System.out.print(massages[i] + ", ");
			else
				System.out.print(massages[i]);
		}
		System.out.println("]\n");

		// **** ****
		sc.close();

		// **** select apointmets that generate the maximum number of minutes ****
		int sum = maxMinutes(massages);
		System.out.println("main <<<  sum: " + sum + "\n");

		sum = maxMinutesIterative(massages);
		System.out.println("main <<<  sum: " + sum + "\n");

		sum = maxMinutesOptimum(massages);
		System.out.println("main <<<  sum: " + sum + "\n");
	}

}
