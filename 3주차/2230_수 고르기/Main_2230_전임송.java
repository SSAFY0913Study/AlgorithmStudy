import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

//2230 수 고르기

public class Main_2230_수고르기 {
	static int N;
	static int M;
	static int[] numbers;
	static int minDifference = Integer.MAX_VALUE;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		M = Integer.parseInt(input[1]);
		numbers = new int[N];
		for (int i = 0; i < N; i++) {
			numbers[i] = Integer.parseInt(br.readLine());
		}
		Arrays.sort(numbers);
		search();
		System.out.println(minDifference);
	}
	
	public static void search() {
		for (int i = 0; i < N; i++) {
			int target = numbers[i];
			int index = Arrays.binarySearch(numbers, target + M);
			//System.out.println(index);
			if (index > 0) { //M 차이가 나는 수를 찾은 경우
				minDifference = M;
				return;
			}
			
			index = index * -1 - 1;
			
			if (index == N) {
				continue;
			}
			minDifference = Math.min(minDifference, numbers[index] - target);
		}
	}
}
