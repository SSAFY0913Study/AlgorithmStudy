import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//23288 주사위 굴리기 2
//좌표 1,1 부터 N,M
//시작 좌표 1,1 
//1. 이동방향으로 한칸 이동, 지도 밖으로 나가면 반대 방향으로 한칸 이동; 이동 가능 체크
//2. 도착한 칸에 대한 점수를 획득한다
	// 점수는 칸의 정수 B 에서 4방향으로 연속해서 이동할수있는 정수 B를 가진 칸의 수 C; B * C DFS
//3. 아랫면에 있는 정수 A와 주사위가 있는칸의 정수 B를 비교해
	// A > B, 이동 방향을 90도 시계방향으로 회전
	//A < B, 이동방향 반시계 방향으로 회전
	// 이동방향 변화 없음

public class Main_23288_주사위굴리기_전임송 {	
	static final int START_ROW = 1;
	static final int START_COL = 1;
	static final int EAST = 0;
	static final int NORTH = 1;
	static final int WEST = 2;
	static final int SOUTH = 3;
	static final int START_DIRECTION = EAST;
	
	static int[] dr = {0, 1, 0, -1}; //동 남 서 북
	static int[] dc = {1, 0, -1, 0};
	
	static class Dice {
		int row;
		int col;
		int direction;
		int top;
		int bottom;
		int left;
		int right;
		int up;
		int down;
		public Dice(int row, int col, int direction) {
			this.row = row;
			this.col = col;
			this.direction = direction;
			top = 1;
			bottom = 6;
			left = 4;
			right = 3;
			up = 2;
			down = 5;
			
		}
		public void roll() {
			if (!canRoll()) { //이동 못하는 경우 방향 변경
				flipDirection();
			}
			row = row + dr[direction]; //이동
			col = col + dc[direction];
			int tmpTop = top;
			int tmpBottom = bottom;
			if (direction == EAST) {
				top = left;
				bottom = right;
				left = tmpBottom;
				right = tmpTop;
			}
			if (direction == NORTH) {
				top = up;
				bottom = down;
				up = tmpBottom;
				down = tmpTop;
			}
			if (direction == WEST) {
				top = right;
				bottom = left;
				left = tmpTop;
				right = tmpBottom;
			}
			if (direction == SOUTH) {
				top = down;
				bottom = up;
				up = tmpTop;
				down = tmpBottom;
			}
		}
		public void turn(int mapNumber) {
			if (bottom > mapNumber) {
				turnClockwise();
			}
			if (bottom < mapNumber) {
				turnCounterClockwise();
			}
		}
		private void turnClockwise() {
			direction = (direction + 1) % 4;
		}
		private void turnCounterClockwise() {
			direction = (direction - 1);
			if (direction < 0) {
				direction = 3;
			}
		}
		private void flipDirection() {
			direction = (direction + 2) % 4;
		}
		private boolean canRoll() { //이동방향으로 이동 가능한지 체크
			int newR = row + dr[direction];
			int newC = col + dc[direction];
			return newR >= 0 && newR < N && newC >= 0 && newC < M;
		}
	}
	static int N;
	static int M;

	static int[][] map;
	static boolean[][] visited;
	static int score;
	static int totalScore = 0;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		M = Integer.parseInt(input[1]);
		int K = Integer.parseInt(input[2]);
		map = new int[N][M];
		
		for (int r = 0; r < N; r++) {
			input = br.readLine().split(" ");
			for (int c = 0; c < M; c++) {
				map[r][c] = Integer.parseInt(input[c]);
			}
		}
		Dice dice = new Dice(START_ROW - 1, START_COL - 1, START_DIRECTION);
		for (int i = 0; i < K; i++) {
			dice.roll();
			totalScore += getScore(dice.row, dice.col);
			dice.turn(map[dice.row][dice.col]);
		}
		System.out.println(totalScore);
	}
	
 	public static int getScore(int row, int col) {
 		int number = map[row][col]; //해당 칸 정수
 		visited = new boolean[N][M];
 		score = 0;
 		dfs(number, row, col);
 		return score * number;
 	}
 	
 	public static void dfs(int number, int row, int col) { 
 		score++;
 		visited[row][col] = true;
 		for (int d = 0; d < 4; d++) {
 			int newR = row + dr[d];
 			int newC = col + dc[d];
 			if (newR < 0 || newR >= N || newC < 0 || newC >= M) {
 				continue;
 			}
 			if (visited[newR][newC] || map[newR][newC] != number) {
 				continue;
 			}
 			dfs(number, newR, newC);
 		}
 	}
}