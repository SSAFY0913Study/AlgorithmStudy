import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class Main {
	
	private static int N, M, K, spaceCount, minTime;
	private static int[][] srcMap, map;
	private static ArrayList<int[]> startPoints;
	private static boolean[] selected;
	
	private static int[] dx = { -1,  0, +1,  0};	// 상, 우, 하, 좌
	private static int[] dy = {  0, +1,  0, -1};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		M = Integer.parseInt(input[1]);
		srcMap = new int[N][N];
		map = new int[N][N];
		startPoints = new ArrayList<int[]>();
		minTime = Integer.MAX_VALUE;
		
		for (int i = 0; i < N; i++) {
			input = br.readLine().split(" ");
			for (int j = 0; j < N; j++) {
				int num = Integer.parseInt(input[j]);
				srcMap[i][j] = num;
				
				if (num == 0) {
					spaceCount++;
				}
				else if (num == 2) {
					startPoints.add(new int[] {i, j});
				}
			}
		}
		
		K = startPoints.size();
		selected = new boolean[K];
		combination(0, 0);
		
		if (minTime == Integer.MAX_VALUE)
			minTime = -1;
		System.out.println(minTime);
		br.close();
	}
	
	public static void bfs() {
		// 초기값 세팅
		int leftSpaceCount = spaceCount;
//		for (int i = 0; i < N; i++) {	// 맵 객체를 새로 할당하는 것보다 복사하는게 더 빠르지 않을까?
//			for (int j = 0; j < N; j++) {
//				map[i][j] = srcMap[i][j];
//			}
//		}
		map = new int[N][N];
		for (int i = 0; i < N; i++) {	// 맵 객체를 새로 할당하는 것보다 복사하는게 더 빠르지 않을까?
			map[i] = srcMap[i].clone();
		}
		Queue<int[]> queue = new LinkedList<>();
		for (int i = 0; i < K; i++) {
			if (selected[i]) {
				int[] cur = startPoints.get(i);
				queue.offer(cur);
				map[cur[0]][cur[1]] = 11;	// 0은 빈 칸을 나타내므로 순회했단 표시로 11 저장
			}
		}
		
		int time = 0;
		Queue<int[]> next = new LinkedList<>();
		while (!queue.isEmpty()) {

			// 남은 공간이 없는 경우 종료
			if (leftSpaceCount == 0) break;
			
			// 시간 증가
			time++;
			
			// 최소 시간보다 오래걸리는 경우 더이상 순회 x
			if (minTime < time) break;
			
			// 한 타임 동안 순회
			while (!queue.isEmpty()) {
				int[] cur = queue.poll();
				int x = cur[0];
				int y = cur[1];
				
				for(int j = 0; j < 4; j++) {
					int nx = x + dx[j];
					int ny = y + dy[j];
					
					if (isValidPosition(nx, ny)) 
					{
						if (map[nx][ny] == 0) {	// 빈 공간일 때만, 다음 큐에 추가
							next.offer(new int[] {nx, ny});
							map[nx][ny] = 11;
							leftSpaceCount--;
						}
						else if (map[nx][ny] == 2) {	// 비활성일 경우, 지금 큐에 추가
							next.offer(new int[] {nx, ny});
							map[nx][ny] = 11;
						}
					}
				}
			}
			
			// printMap(time, leftSpaceCount);
			queue.addAll(next);
			next.clear();
		}

		if (leftSpaceCount == 0) {
			minTime = Math.min(minTime, time);
		}
	}
	
	private static void printMap(int time, int leftSpaceCount) {
		System.out.printf("time : %d %d\n", time, leftSpaceCount);
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.printf("%02d ", map[i][j]);
			}
			System.out.println();
		}
		System.out.println("==========================================");
	}

	public static void combination(int r, int s) {
		if (r == M) {
			bfs();
			return;
		}
		
		for (int i = s; i < K; i++) {
			if (selected[i]) continue;
			
			selected[i] = true;
			combination(r+1, i+1);
			selected[i] = false;
		}
	}
	
	private static boolean isValidPosition(int x, int y) {
		if (x >= 0 && x < N &&
				y >= 0 && y < N) 
			return true;
		else 
			return false;
	}
}

/**
  * 백준 17142. 연구소 3
  * 
	입력
	N, M						// N : 연구소의 크기 (4 ≤ N ≤ 50), M : 놓을 수 있는 바이러스의 개수(1 ≤ M ≤ 10)
	for (N, N) ->	[0, 1, 2]	// 0은 빈 칸, 1은 벽, 2는 바이러스를 놓을 수 있는 위치. (M <= 2의 개수 K <= 10)
	
	출력
	연구소의 모든 빈 칸에 바이러스가 있게 되는 최소 시간.
	모든 빈 칸에 퍼뜨릴 수 없는 경우에는 -1 출력.
	
	아이디어
	kCM의 경우 중 최소 시간 (조합 + BFS + 백트래킹)
	
	
**/