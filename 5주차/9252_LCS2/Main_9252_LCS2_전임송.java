import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

//9252 LCS2
//2차원배열 
//모든 알파벳 비교
// 1. 같으면 그 전

public class Main_9252_LCS2_전임송 {	
	static long K;
	static int[][] dp;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String a = br.readLine();
		String b = br.readLine();
		int aLength = a.length();
		int bLength = b.length();
		dp = new int[aLength + 1][bLength + 1];
		for (int i = 0; i <= aLength; i++) {
			for (int j = 0; j <= bLength; j++) {
				if (i == 0 || j == 0) {
					dp[i][j] = 0;
					continue;
				}
				if (a.charAt(i - 1) == b.charAt(j - 1)) {
					dp[i][j] = dp[i - 1][j - 1] + 1;
					continue;
				}
				dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
			}
		}
		int row = aLength;
		int col = bLength;
		int length = dp[row][col];
		StringBuilder sb = new StringBuilder();
		while (length > 0) {
			if (dp[row - 1][col] == length) {
				row--;
				continue;
			}
			if (dp[row][col - 1] == length) {
				col--;
				continue;
			}
			length = dp[row - 1][col - 1];
			row--;
			col--;
			sb.append(a.charAt(row)); //공통으로 가지고 있는 문자 추가
		}
		System.out.printf("%d%n%s", dp[aLength][bLength], sb.reverse().toString());
	}
	
}