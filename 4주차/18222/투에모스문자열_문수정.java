import java.io.IOException;
import java.util.Scanner;

public class Main {
	
	static long[] arr;
	
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		long n = scanner.nextLong();
		arr= new long[64];
		arr[0] = 1;
		for (int i = 1; i < 64; i++) {
			arr[i] = arr[i - 1] * 2;
		}
		System.out.println(recur(n));
	}

	private static long recur(long n) {
		if (n == 1) {
			return 0;
		}
		for (int i = 0; i < 64; i++) {
			if (arr[i] >= n) {
				return 1 - recur(n - arr[i - 1]);
			}
		}
		return 0;
	}
}
