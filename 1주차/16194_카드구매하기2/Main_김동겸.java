import java.io.*;

class Main2 {
	
	private static int N;
	private static int[] P;
	private static int[] dp;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		P = new int[N+1];
		dp = new int[N+1];
		String[] input = br.readLine().split(" ");
		for (int i = 0; i < N; i++) {
			P[i+1] = Integer.parseInt(input[i]);
		}
		for (int i = 1; i <= N; i++) {
			dp[i] = P[i];
			for (int j = 1; j < i; j++) {
				dp[i] = Math.min(dp[i], dp[j] + dp[i - j]);
			}
		}
		
		System.out.println(dp[N]);
	}
}

/**
  * 16194. 카드 구매하기 2
**/