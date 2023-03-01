import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//2251 물통

//dfs나 bfs 사용, 현재 물통 번호 사용
//물통이 꽉 차있지 않으면 물 이동
//세번째 물통이 다시 가득 찰때까지 탐색

public class Main_2251_물통 {
	static final int NUMBER_OF_BOTTLES = 3;
	static int[] size;
	
	static boolean[][][] state; //200 * 200 * 200 bytes = 8MB
	static boolean[] possibleC;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] input = br.readLine().split(" ");
		size = new int[NUMBER_OF_BOTTLES];
		size[0] = Integer.parseInt(input[0]); //A
		size[1] = Integer.parseInt(input[1]); //B
		size[2] = Integer.parseInt(input[2]); //C
		
		state = new boolean[size[0] + 1][size[1] + 1][size[2] + 1];
		possibleC = new boolean[size[2] + 1]; //가능한 모든 C의물의 양
		state[0][0][size[2]] = true; //첫 상태 
		possibleC[size[2]] = true;
		dfs(0, 0, size[2]); //초기 물 상태
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= size[2]; i++) {
			if (possibleC[i]) {
				sb.append(i).append(' ');
			}
		}
		System.out.println(sb.toString());
	}
	
	public static void dfs(int a, int b, int c) {
		//A->B, B->A, B->C, B->C, A->C, C->A 모든 물 이동 경우의 수 탐색
		state[a][b][c] = true;
		if (a == 0) {
			possibleC[c] = true; //a가 비어있을때 c 물 양 체크
		}
		
		int amount;
		
		amount = moveAmount(0, a, b); //B->A
		if (!state[a + amount][b - amount][c]) {
			dfs(a + amount, b - amount, c);
		}
		amount = moveAmount(1, b, a); //A->B
		if (!state[a - amount][b + amount][c]) {
			dfs(a - amount, b + amount, c);
		}
		amount = moveAmount(0, a, c); //C->A
		if (!state[a + amount][b][c - amount]) {
			dfs(a + amount, b, c - amount);
		}
		amount = moveAmount(2, c, a); //A->C
		if (!state[a - amount][b][c + amount]) {
			dfs(a - amount, b, c + amount);
		}
		amount = moveAmount(1, b, c); //C->B
		if (!state[a][b + amount][c - amount]) {
			dfs(a, b + amount, c - amount);
		}
		amount = moveAmount(2, c, b); //B->C
		if (!state[a][b - amount][c + amount]) {
			dfs(a, b - amount, c + amount);
		}
	}
	
	//한물통에서 다른 물통으로 물을 이동할수 있는 양 계산
	public static int moveAmount(int destination, int destinationAmount, int possibleAmount) {
		return Math.min(size[destination] - destinationAmount, possibleAmount);
	}
}
