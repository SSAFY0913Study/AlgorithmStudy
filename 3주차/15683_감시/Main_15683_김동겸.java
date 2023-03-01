import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class Main {
	
	static final int UP = 0;
	static final int LEFT = 1;
	static final int DOWN = 2;
	static final int RIGHT = 3;
	
	static int[] dx = { -1,  0,  1,  0 };
	static int[] dy = {  0,  1,  0, -1 };
	
	static int N, M, K;	// K : cctv 개수
	static int[][] map;
	static int[][] cctvCoords;
	static int result = Integer.MAX_VALUE;
	
	private static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		M = Integer.parseInt(input[1]);
		map = new int[N][M];
		cctvCoords = new int[8][2];
		for (int i = 0; i < N; i++) {
			input = br.readLine().split(" ");
			for (int j = 0; j < M; j++) {
				int num = Integer.parseInt(input[j]);
				map[i][j] = num;
				if (num > 0 && num < 6) {
					cctvCoords[K++] = new int[] {i, j};
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		setCCTVs(0);
		
		System.out.println(result);
	}
	
	private static void setCCTVs(int cnt) {
		if (cnt == K) {
			// 결과 출력
			int numOfNotVisibled = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					if (map[i][j] == 0) numOfNotVisibled++;
				}
			}
			result = Math.min(result, numOfNotVisibled);
			return;
		}
		
		int cx = cctvCoords[cnt][0];
		int cy = cctvCoords[cnt][1];
		int type = map[cx][cy];
		
		switch(type) {
		case 1:
			for (int i = 0; i < 4; i++) {
				int[][] rollback = new int[N][M]; 
				rollback = setVisibility(cx, cy, i, false, rollback);
				setCCTVs(cnt+1);
				setVisibility(cx, cy, i, true, rollback);
			}
			break;
		case 2:
			for (int i = 0; i < 2; i++) {
				int[][] rollback = new int[N][M];
				// 반대 방향으로 두 방향
				rollback = setVisibility(cx, cy, i, false, rollback);
				rollback = setVisibility(cx, cy, i+2, false, rollback);
				setCCTVs(cnt+1);
				setVisibility(cx, cy, i, true, rollback);
				setVisibility(cx, cy, i+2, true, rollback);
			}
			break;
		case 3:
			for (int i = 0; i < 4; i++) {
				int[][] rollback = new int[N][M];
				// 시계방향으로 연속된 방향 두 방향
				rollback = setVisibility(cx, cy, i, false, rollback);
				rollback = setVisibility(cx, cy, (i+1)%4, false, rollback);
				setCCTVs(cnt+1);
				setVisibility(cx, cy, i, true, rollback);
				setVisibility(cx, cy, (i+1)%4, true, rollback);
			}
			break;
		case 4:
			for (int i = 0; i < 4; i++) {
				int[][] rollback = new int[N][M];
				// 시계방향으로 연속된 방향 세 방향
				rollback = setVisibility(cx, cy, i, false, rollback);
				rollback = setVisibility(cx, cy, (i+1)%4, false, rollback);
				rollback = setVisibility(cx, cy, (i+2)%4, false, rollback);
				setCCTVs(cnt+1);
				setVisibility(cx, cy, i, true, rollback);
				setVisibility(cx, cy, (i+1)%4, true, rollback);
				setVisibility(cx, cy, (i+2)%4, true, rollback);
			}
			break;
		case 5:
			// 네 방향
			int[][] rollback = new int[N][M];
			rollback = setVisibility(cx, cy, 0, false, rollback);
			rollback = setVisibility(cx, cy, 1, false, rollback);
			rollback = setVisibility(cx, cy, 2, false, rollback);
			rollback = setVisibility(cx, cy, 3, false, rollback);
			setCCTVs(cnt+1);
			setVisibility(cx, cy, 0, true, rollback);
			setVisibility(cx, cy, 1, true, rollback);
			setVisibility(cx, cy, 2, true, rollback);
			setVisibility(cx, cy, 3, true, rollback);
			break;
		}
	}

	private static int[][] setVisibility(int x, int y, int d, boolean rollbacked, int[][] rollback) {
		
		int dx_ = dx[d];
		int dy_ = dy[d];
		int nx = x + dx_;
		int ny = y + dy_;
		
		if (!rollbacked) {
			if (rollback == null)
				rollback = new int[N][M];
			
			while (isValidCoord(nx, ny) && map[nx][ny] != 6) {
				rollback[nx][ny] = map[nx][ny];
				if (map[nx][ny] == 0) map[nx][ny] = '#';
				nx += dx_;
				ny += dy_;
			}
		}
		else {
			// rollback
			while (isValidCoord(nx, ny) && map[nx][ny] != 6) {
				map[nx][ny] = rollback[nx][ny];
				nx += dx_;
				ny += dy_;
			}
		}
		return rollback;
	}

	private static boolean isValidCoord(int nx, int ny) {
		return nx >= 0 && nx < N && ny >= 0 && ny < M;
	}
}

/**
  * 백준 15683. 감시
  * 
**/