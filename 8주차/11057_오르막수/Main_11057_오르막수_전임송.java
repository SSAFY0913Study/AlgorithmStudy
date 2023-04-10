import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
 * 11057 오르막수
 * 
 */

public class Main_11057_오르막수_전임송 {		
	static final int MOD = 10_007;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[][] dp = new int[N+1][10];
        
        // 1자리 수 초기화
        Arrays.fill(dp[1], 1);
        
        // 점화식을 이용해 dp 배열 채우기
        for (int i = 2; i <= N; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k <= j; k++) {
                    dp[i][j] += dp[i-1][k];
                    dp[i][j] %= MOD;
                }
            }
        }
        
        // n자리 수에서 가능한 모든 오르막 수의 개수 계산
        int answer = 0;
        for (int i = 0; i < 10; i++) {
            answer += dp[N][i];
            answer %= MOD;
        }
        
        System.out.println(answer);
	}
}
