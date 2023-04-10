import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

//1981 배열에서이동
/*
 * bfs 객체에 최소 최대 저장
 * 최대 최소조합마다 visited
 */

public class Main_1981_배열에서이동_전임송 {	
	static final int MAX_VALUE = 201;
	static final int MIN_VALUE = 0;
	static final int START_R = 0;
	static final int START_C = 0;
	static final int[] dr = {0, 0, -1 ,1};
	static final int[] dc = {-1, 1, 0 ,0};
	
	static class Position {
		int r;
		int c;
		public Position(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}
	}
	static int N;
	static int[][] board;
	static int min;
	static int max;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		board = new int[N][N];
		String[] line;
		min = MAX_VALUE;
		max = MIN_VALUE;
		for (int r = 0; r < N; r++) {
			line = br.readLine().split(" ");
			for (int c = 0; c < N; c++) {
				int number= Integer.parseInt(line[c]);
				board[r][c] = number;
				if (board[r][c] < min) {
					min = board[r][c];
				}
				if (board[r][c] > max) {
					max = board[r][c];
				}
			}
		}
		int result = MAX_VALUE;
		int mapMax = max;
		max = board[START_R][START_C];
		
		while (min <= board[START_R][START_C] && max <= mapMax) {
			if (bfs(START_R, START_C)) { //이번 ma
				if (max - min < result) {
					result = max - min;
				}
				min++;
				continue;
			}
			max++;
		}
		System.out.println(result);
	}
	
	public static boolean bfs(int r, int c) {
		boolean[][] visited = new boolean[N][N]; //가능한 차이 값 만큼 visited
		Queue<Position> queue = new ArrayDeque<>();
		queue.add(new Position(START_R, START_C));
		visited[START_R][START_C] = true;
		Position current;
		while (!queue.isEmpty()) {
			current = queue.poll();
			
			
			for (int d = 0; d < 4; d++) {
				int newR = current.r + dr[d];
				int newC = current.c + dc[d];
				if (newR < 0 || newR >= N || newC < 0 || newC >= N) {
					continue;
				}
				if (visited[newR][newC]) {
					continue;
				}
				if (board[newR][newC] > max || board[newR][newC] < min) { //이번 최솟값, 최댓값보다 최댓값이 큰 경우
					continue;
				}
				if (newR == N - 1 && newC == N - 1) {
					return true;
				}
				//System.out.println("r = " + newR + " c= " + newC + " " + newMin + " " + newMax);
				queue.add(new Position(newR, newC));
				visited[newR][newC] = true;
			}
		}
		return false;
	}
}
