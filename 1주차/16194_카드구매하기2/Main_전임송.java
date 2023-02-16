package algorithm.boj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

class Main_16194 {
	static final int MAX_PRICE = 10_000_000;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int n = Integer.parseInt(br.readLine());
		String[] input = br.readLine().split(" ");
		
		int[] price = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			price[i] = Integer.parseInt(input[i - 1]);
		}
		
		int[][] dp = new int[n + 1][n + 1];
		for (int col = 1; col <= n; col++) {
			dp[0][col] = MAX_PRICE;
		}
		for (int row = 1; row <= n; row++) {
			for (int col = 1; col <= n; col++) {
				if (col < row) {
					dp[row][col] = dp[row - 1][col];
					continue;
				}
				dp[row][col] = Math.min(dp[row][col - row] + price[row], dp[row - 1][col]);
			}
		}
		
		bw.append(Integer.toString(dp[n][n]));
		bw.flush();
		br.close();
		bw.close();
	}	
}