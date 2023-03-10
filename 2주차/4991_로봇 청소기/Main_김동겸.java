import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

class Main {
	
	/**
	 *  좌표 int : 31~16 x좌표, 15~0 y좌표
	 */
	
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static StringBuilder sb = new StringBuilder(10);
	private static int row, col;
	private static int n;			// 더러운 칸의 갯수.
	private static int[] dirties;	// 더러운 칸의 좌표. (최대 10개)
	private static int[] perm;		// 더러운 칸 순열 저장
	private static int startCoord;
	private static char[][] map;
	private static int[][] distance;
	private static int result;		// 최소 이동 거리
	private static boolean[][] visited;
	private static int MAX_STEP; 
	
	private static int[] dr = { -1,  0,  1,  0};
	private static int[] dc = {  0,  1,  0, -1};
	
	private static boolean init() throws IOException {
		String[] input = br.readLine().split(" ");
		row = Integer.parseInt(input[1]);	// h
		col = Integer.parseInt(input[0]);	// w
		result = Integer.MAX_VALUE;
		dirties = new int[10];
		n = 0;
		
		if ((row|col) == 0) return false;
		
		MAX_STEP = row*col;
		visited = new boolean[row][col];
		map = new char[row][col];
		
		for (int i = 0; i < row; i++) {
			map[i] = br.readLine().toCharArray();
			for (int j = 0; j < col; j++) {
				if (map[i][j] == 'o') {
					startCoord = codeCoord(i, j);
				}
				else if (map[i][j] == '*') {
					dirties[n++] = codeCoord(i, j);
				}
			}
		}
		distance = new int[n+1][n+1];
		perm = IntStream.range(1, n+1).toArray();
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		
		while (init()) {
			if (n == 0) {
				// 더러운 방이 없음
				sb.append(0).append("\n");
				continue;
			}
			else if (n == 1) {
				// 더러운 방이 1개
				sb.append(bfs(startCoord, dirties[0])).append("\n");
				continue;
			}
			
			// 더러운 방이 2개 이상
			// 노드에서 노드 거리 계산 (bfs)
			if (!calcDistance()) {
				// 도달 불가능할 때
				sb.append(-1).append("\n");
				continue;
			};
			
			// 순서에 따른 최소값 계산
			int[] orders = new int[n+1];	// 순회 순서 저장
			do {
				// 새로운 순열
				for (int i = 1; i <= n; i++) {
					orders[i] = perm[i-1];
				}
				// 순서에 따른 길이 검사
				// 이전 최소값을 넘어가면 더이상 검사하지않음
				int sumDistance = 0;	// 이동거리
				for (int i = 0; i < n; i++) {
					sumDistance += distance[orders[i]][orders[i+1]];
					
					if (result < sumDistance) break;
				}
				
				result = Math.min(result, sumDistance);
				
			} while (nextPermutation());
			
			// 구한 최솟값 출력
			sb.append(result).append("\n");
		}
		
		System.out.print(sb);
	}
	
	// 도달 불가능한 경우 -1 반환
	private static boolean calcDistance() {
		int start = startCoord;
		int startIdx = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				int dest = dirties[j];
				int destIdx = j+1;
				int d = bfs(start, dest);
				
				if (d == -1) return false;	// 도달 불가능 할 경우 -1 반환
				
				distance[startIdx][destIdx] = d;
				distance[destIdx][startIdx] = d;
			}
			
			start = dirties[i];
			startIdx = i+1;
		}
		return true;
	}

	private static void visitedClear() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				visited[i][j] = false;
			}
		}
	}

	private static boolean nextPermutation() {
		
		int lastIndex = n-1; 
		
		// 1. 꼭대기 탐색
		int i = lastIndex;
		while (i > 0 && perm[i-1] >= perm[i]) --i;
		if (i == 0) return false;	// 마지막 순열
		
		// 2. 꼭대기 이전 값보다 큰 값을 맨 뒤에서부터 탐색
		int j = lastIndex;
		while (perm[i-1] >= perm[j]) --j;
		
		// 3. 꼭대기 이전과 2번에서 찾은 값을 스왑
		swap(i-1, j);
		
		// 4. 꼭대기부터 맨 뒤까지 오름차순 정렬
		j = lastIndex;
		while (i < j) {
			swap(i++, j--);
		}
		
		return true;
	}
	
	private static void swap(int i, int j) {
		int temp = perm[i];
		perm[i] = perm[j];
		perm[j] = temp;
	}

	/**
	 * 
	 * @param src  시작 좌표.
	 * @param dest 목표 좌표.
	 * @return start에서 end까지 최소 거리. (도달 불가 -1 반환)
	 */
	private static int bfs(int src, int dest) {
		Queue<Integer> queue = new LinkedList<>();
		visitedClear();
		queue.offer(src);
		
		int step = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();
			
			for (; size > 0; size--) {
				int current = queue.poll();
				
				int r = decodeCoordR(current);
				int c = decodeCoordC(current);
				visited[r][c] = true;
				
				// 사방 순회
				for (int i = 0; i < 4; i++) {
					int nr = r + dr[i];
					int nc = c + dc[i];
					
					int nextCoord = codeCoord(nr, nc);
					if (nextCoord == dest) return step+1;
					
					if (isValidRC(nr, nc) && !visited[nr][nc]
							&& map[nr][nc] != 'x') {
						queue.offer(codeCoord(nr, nc));
						visited[nr][nc] = true;
					}
				}
			}
			
			if (++step == MAX_STEP) break;
		}
		
		// 위에서 최대 스텝을 넘어서면 도달하지 못하는 경우임.
		return -1;
	}

	private static boolean isValidRC(int nr, int nc) {
		return nr>=0 && nr < row && nc >= 0 && nc < col;
	}
	
	private static int codeCoord(int nr, int nc) {
		return nr << 16 | nc;
	}
	private static int decodeCoordR(int coord) {
		return coord >> 16;
	}
	private static int decodeCoordC(int coord) {
		return coord & (1<<16)-1;
	}
}
/**
 *  4991. 로봇 청소기
 */
