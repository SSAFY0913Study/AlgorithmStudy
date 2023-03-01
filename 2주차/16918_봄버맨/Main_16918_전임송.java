import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//n이 많이 크지 않기때문에 시뮬레이션; timer 배열에 폭탄 시간 저장
public class Main_16918_봄버맨 {	
	static final char BOMB = 'O';
	static final char EMPTY = '.';
	
	static int row;
	static int col;
	static int[][] board;
	static int timer;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		row = Integer.parseInt(input[0]);
		col = Integer.parseInt(input[1]);
		int n = Integer.parseInt(input[2]);
		board = new int[row][col];
		timer = 2; 	
		String line; 							//처음 1초는 아무것도 하지 않기 때문에 2초로 초기화
		for (int r = 0; r < row; r++) {
			line = br.readLine();
			for (int c = 0; c < col; c++) {
				if (line.charAt(c) == BOMB) {
					board[r][c] = 3;
					continue;
				}
				board[r][c] = timer + 3; 		//비여있는칸은 2초에 3초 타이머를 가진 폭탄이 설치된다. 
			}
		}
		
		while (timer <= n) {
			trigger();
			timer++;
		}
		printBoard();
	}
	
	private static void printBoard() {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < row; r++) {
			for (int c = 0; c < col; c++) {
				if (board[r][c] - 3 == timer) { 	//아직 설치되지 않은 폭탄 위치
					sb.append(EMPTY);
					continue;
				}
				sb.append(BOMB);
			}
			sb.append('\n');
		}
		System.out.println(sb.toString());
	}
	
 	private static void trigger() {
		for (int r = 0; r < row; r++) {
			for (int c = 0; c < col; c++) {
				if (board[r][c] == timer) { 	//타이머가 다 된 폭탄들을 폭파시킨다.
					explode(r, c);
				}
			}
		}
		
	}	
	private static void explode(int r, int c) { //4방향 폭파; 같은 시간의 폭탄이면 아무것도 하지 않는다
		int[] dr = {0, 0, -1, 1};
		int[] dc = {-1, 1, 0, 0};
		int newTimer = timer + 4;
		board[r][c] = newTimer;																					
		for (int d = 0; d < 4; d++) {
			int newR = r + dr[d];
			int newC = c + dc[d];
			if (newR < 0 || newR >= row || newC < 0 || newC >= col 
					|| board[newR][newC] == timer) {
				continue;
			}
			board[newR][newC] = newTimer; //빈칸은 1초후에 3초 타이머를 가진 폭탄이 설치 되기때문에 현재 시간 + 4로 설정한다.
		}
	}
}