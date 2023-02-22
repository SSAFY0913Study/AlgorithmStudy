import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class Main {
	
	/**
	 * x = idx / N, y = idx % N
	 */

	static final int UP		= 1;
	static final int RIGHT	= 2;
	static final int DOWN	= 3;
	static final int LEFT	= 4;
	
	static int SIZE, N, L, R, numOfDate;
	static int[] map;
	static boolean[] visited;
	
	
	private static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		SIZE = N*N;
		L = Integer.parseInt(input[1]);
		R = Integer.parseInt(input[2]);
		map = new int[SIZE];
		for (int i = 0; i < N; i++) {
			input = br.readLine().split(" ");
			for (int j = 0; j < N; j++) {
				map[i*N+j] = Integer.parseInt(input[j]);
			}
		}
		visited = new boolean[SIZE];
	}

	public static void main(String[] args) throws Exception {
		init();
		
		while((migration()));
		
		System.out.println(numOfDate);
	}

	// 이주 여부 반환
	private static boolean migration() {

		Arrays.fill(visited, false);	// 순회 여부 배열 초기화
		int isolationCount = 0;
		for (int i = 0; i < SIZE; i++) {
			if (visited[i]) continue;
			
			int size = dfs(i);
			if (size == 1) isolationCount++;
		};
		
		if (isolationCount == SIZE) return false;
		
		// 날 수 증가
		numOfDate++;
		return true;
	}

	// idx에서 시작해 순회한 나라 수 반환
	private static int dfs(int idx) {
		int cnt = 1;
		int sum = map[idx];
		Queue<Integer> idxList = new LinkedList<>();
		Stack<Integer> stk = new Stack<>();
		stk.push(idx);
		visited[idx] = true;
		idxList.offer(idx);
		while (!stk.isEmpty()) {
			int cur = stk.pop();

			for (int i = 1; i <= 4; i++) {
				// 사방순회
				int nextIndex = getNextIndex(cur, i);
				if (nextIndex != -1 && 
					!visited[nextIndex] &&
					canMove(cur, nextIndex)) // 이동 가능한지 확인
				{
					visited[nextIndex] = true;
					idxList.offer(nextIndex);
					stk.push(nextIndex);
					cnt++;					// 나라수 합산
					sum += map[nextIndex];	// 인구수 합산
				}
			}
		}
		if (cnt == 1) return 1;	// 인구이동 X
		
		// 평균 계산
		int avg = (int) ((double)sum / cnt);
		while(!idxList.isEmpty()) {
			map[idxList.poll()] = avg;
		}
		
		return cnt;
	}

	private static boolean canMove(int cur, int nextIndex) {
		int diff = Math.abs(map[cur] - map[nextIndex]);
		return diff>=L && diff<=R;
	}

	private static int getNextIndex(int idx, int direction) {
		int nIdx = -1;
		if (direction == UP) 	nIdx = idx >= N 		? idx-N : -1;
		if (direction == RIGHT) nIdx = idx % N != N-1	? idx+1 : -1;
		if (direction == DOWN) 	nIdx = idx+N < SIZE		? idx+N : -1;
		if (direction == LEFT)	nIdx = idx % N != 0		? idx-1 : -1;
		return nIdx;
	}
}

/**
 *  16234. 인구 이동
 */