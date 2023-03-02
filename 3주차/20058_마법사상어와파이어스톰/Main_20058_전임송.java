import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

//20058 마법사상어와파이어스톰
// 1. 격자 90도 돌리기
// 2. 얼음 처리
		//모든칸탐색, 사방에 얼음이 없는 칸이 3개 미만이면 얼음 수 - 1;
public class Main_20058_마법사상어와파이어스톰 {	
	static final int REQUIRED_ICE = 3;
	
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
	static int NN;
	static int[][] ice;
	
	static int[] dr = {0, 0, -1, 1};
	static int[] dc = {-1, 1, 0, 0};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		int Q = Integer.parseInt(input[1]);
		NN = (int) Math.pow(2, N);
		ice = new int[NN][NN];
		for (int r = 0; r < NN; r++) {
			input = br.readLine().split(" ");
			for (int c = 0; c < NN; c++) {
				ice[r][c] = Integer.parseInt(input[c]);
			}
		}
		input = br.readLine().split(" ");
		for (int i = 0; i < Q; i++) {
			int level = Integer.parseInt(input[i]);
			castSpell(level);
			reduceIce();
		}

		System.out.printf("%d%n%d%n", countIce(), findCluster());
	}
	
	public static void castSpell(int level) { //level에 따라 파이어스톰을 시전한다, 
		int size = (int) Math.pow(2, level);
		int numberOfSplits = NN / size;
		int[][] copyIce = new int[NN][NN];
		for (int r = 0; r < numberOfSplits; r++) {
			for (int c = 0; c < numberOfSplits; c++) { //각 격자별로 회전
				rotate(copyIce, r * size, c * size, size);
			}
		}
		ice = copyIce;
	}
	
	public static void rotate(int[][] copyIce, int startR, int startC, int size) { //90도 회전
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				copyIce[r + startR][c + startC] = ice[(size - c - 1) + startR][r + startC];
			}
		}
	}
	public static void reduceIce() {
		int[][] reduced = new int[NN][NN];
		
		for (int r = 0; r < NN; r++) {
			for (int c = 0; c < NN; c++) {
				int iceCount = 0;
				for (int d = 0; d < 4; d++) { //사방탐색
					int newR = r + dr[d];
					int newC = c + dc[d];
					if (newR < 0 || newR >= NN || newC < 0 || newC >= NN) {
						continue;
					}
					if (ice[newR][newC] == 0) {
						continue;
					}
					iceCount++;
				}
				if (iceCount < REQUIRED_ICE) {
					reduced[r][c] = -1; //줄어들 얼음 위치 저장
				}
			}
		}
		for (int r = 0; r < NN; r++) {
			for (int c = 0; c < NN; c++) {
				if (ice[r][c] > 0) {
					ice[r][c] += reduced[r][c]; //얼음 양 변화 반영
				}
			}
		}
	}
	
	public static int countIce() { //전체 얼음 수 카운트
		int sum = 0;
		for (int r = 0; r < NN; r++) {
			for (int c = 0; c < NN; c++) {
				sum += ice[r][c];
			}
		}
		return sum;
	} 
	
	public static int findCluster() {
		boolean[][] visited = new boolean[NN][NN];
		int clusterSize = 0;
		for (int r = 0; r < NN; r++) {
			for (int c = 0; c < NN; c++) { //모든 칸에서 bfs 탐색 시작
				clusterSize = Math.max(clusterSize, bfs(visited, r, c));
			}
		}
		return clusterSize;
	}
	
	public static int bfs(boolean[][] visited, int r, int c) { 
		int size = 0;
		if (visited[r][c] || ice[r][c] == 0) { //0이 아니거나 방문 안한 칸일때만 탐색
			return size;
		}
		
		Queue<Position> queue = new ArrayDeque<>();
		size++;
		visited[r][c] = true;
		queue.add(new Position(r, c));
		
		while (!queue.isEmpty()) {
			Position current = queue.poll();
			
			for (int d = 0; d < 4; d++) { //사방탐색
				int newR = current.r + dr[d];
				int newC = current.c + dc[d];
				if (newR < 0 || newR >= NN || newC < 0 || newC >= NN) {
					continue;
				}
				if (visited[newR][newC] || ice[newR][newC] == 0) {
					continue;
				}
				size++;
				visited[newR][newC] = true;
				queue.add(new Position(newR, newC));
			}
		}
		return size;
	}
	
	static public void printIce() {
		for (int r = 0; r < NN; r++) {
			System.out.println(Arrays.toString(ice[r]));
		}
		System.out.println();
	}
}