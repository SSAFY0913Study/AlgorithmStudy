import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//15683 감시
//모든 경우 탐색, 조합(각 cctv 마다 4방향 경우를 체크해야한다; 5번일때는 불필요), 사다리랑 비슷하다: 각 cctv 마다, 각 방향마다 조합
// 전체 빈 칸 - cctv감시 칸  = 사각지대
//void findVisable(int cctv type, int r, int c, int direction) 1: 
public class Main_15683_감시 {
	static final int EMPTY = 0;
	static final int WALL = 6;
	static final int CCTV_SINGLE = 1;
	static final int CCTV_DOUBLE_180 = 2;
	static final int CCTV_DOUBLE_90 = 3;
	static final int CCTV_TRIPLE = 4;
	static final int CCTV_FULL = 5;
	static final int[] CCTV_NUMBER_OF_CAMERA = {0, 1, 2, 2, 3, 4};
	static final int[] CCTV_POSSIBLE_DIRECTIONS = {0, 4, 2, 4, 4, 1}; //cctv 별로 탐색해야하는 방향 수

	static int[] dr = {0, 1, 0, -1}; //right, down, left, up
	static int[] dc = {1, 0, -1, 0};
	
	static int N;
	static int M;
	static int[][] office;
	
	static int blindCount = 0;
	static int cctvCount;
	static List<CCTV> cctvs;
	static int[] directions;
	static boolean[][] visited;
	static int minCount = Integer.MAX_VALUE;
	static class CCTV {
		int type;
		int possibleDirections;
		int r;
		int c;
		public CCTV(int type, int r, int c) {
			this.type = type;
			this.possibleDirections = CCTV_POSSIBLE_DIRECTIONS[type];
			this.r = r;
			this.c = c;
		}
		
		public int getCount(int direction) {
			int count = 0;
			for (int i = 0; i < CCTV_NUMBER_OF_CAMERA[type]; i++) {
				if (type == CCTV_SINGLE) {
					count += view(direction);
				}
				if (type == CCTV_DOUBLE_180) { //0(0, 2), 1(1,3)
					int d = direction + i * 2; 
					count += view(d);
				}
				if (type == CCTV_DOUBLE_90) {
					int d =(direction + i) % 4; 
					count += view(d);
				}
				if (type == CCTV_TRIPLE) {
					int d =(direction + i) % 4; 
					count += view(d);
				}
				if (type == CCTV_FULL) {
					int d =(direction + i) % 4; 
					count += view(d);
				}
			}
			return count;
		}
		
		private int view(int direction) {
			int count = 0;
			int newR = r;
			int newC = c;;
			while (true) { //주어진 방향으로 일직선 최대 칸 카운트
				newR = newR + dr[direction];
				newC = newC + dc[direction];
				
				if (newR < 0 || newR >= N || newC < 0 || newC >= M) {
					break;
				}
				if (office[newR][newC] == WALL) {
					break;
				}
				if (office[newR][newC] > 0 || visited[newR][newC]) { //다른 cctv, 이미 다른 cctv가 방문한 칸인 경우 카운트 하지 않는다
					continue;
				}
				count++;
				visited[newR][newC] = true;
			}
			return count;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		M = Integer.parseInt(input[1]);
		
		blindCount = N * M;
		office = new int[N][M];
		cctvCount = 0;
		cctvs = new ArrayList<>(8);
		for (int r = 0; r < N; r++) {
			input = br.readLine().split(" ");
			for (int c = 0; c < M; c++) {
				office[r][c] = Integer.parseInt(input[c]);
				if (office[r][c] == 6) {
					blindCount--;
					continue;
				}
				if (office[r][c] > 0) { //cctv 정보 저장
					int type = office[r][c];
					cctvCount++;
					cctvs.add(new CCTV(type, r, c));
					blindCount--;
				}
			}
		}
		directions = new int[cctvCount];
		getCombination(0, 0);
		System.out.println(minCount);
	}
	
	public static void getCombination(int count, int start) { //cctv마다 방향 설정
		if (count == cctvCount) {
			//사각지대 카운트
			minCount = Math.min(minCount, blindCount - countCovered());
			return;
		}
		for (int cctvNum = start; cctvNum < cctvCount; cctvNum++) {
			for (int d = 0; d < cctvs.get(cctvNum).possibleDirections; d++) {
				directions[cctvNum] = d;
				getCombination(count + 1, cctvNum + 1);
			}
		}
	}
	
	public static int countCovered() {
		//cctv마다 주어진 방향과 cctv 타입별로 보드 탐색; visited 배열 필요
		visited = new boolean[N][M];
		int totalCoveredCount = 0;
		for (int i = 0; i < cctvCount; i++) {
			totalCoveredCount += cctvs.get(i).getCount(directions[i]); 
		}
		return totalCoveredCount;
	}
}
