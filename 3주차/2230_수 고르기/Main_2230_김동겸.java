import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N, M;
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		M = Integer.parseInt(input[1]);
		int[] numbers = new int[N + 1]; 
		for (int i = 0; i < N; i++) {
			numbers[i] = Integer.parseInt(br.readLine());
		}
		Arrays.sort(numbers);
		
		int min = 0;
		int i = 1, j = 1;
		// j를 증가시키며 M을 넘어가는 차이값 찾기
		while ((min = numbers[j] - numbers[i]) < M) {
			j++;
		}
		while (j <= N) {
			int diff = numbers[j] - numbers[i];
			if (i < N && diff >= M) {
				min = Math.min(numbers[j] - numbers[i], min);
				i++;
			}
			else j++;
		}
		
		System.out.println(min);
	}
}

/**
 *	2230. 수 고르기 
 */