import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Main {

	private static int R, C, N;
	
	private static char[][] map;
//	private static List<Integer> bombs;
	private static boolean[][] visited;
	
	private static int[] dx = { -1,  0,  1,  0};
	private static int[] dy = {  0,  1,  0, -1};
	
	private static void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		R = Integer.parseInt(input[0]);
		C = Integer.parseInt(input[1]);
		N = Integer.parseInt(input[2]);
		map = new char[R][C];
//		bombs = new ArrayList<>();
		visited = new boolean[R][C];
		
		for (int i = 0; i < R; i++) {
			map[i] = br.readLine().toCharArray();
			for (int j = 0; j < C; j++) {
				if (map[i][j] == 'O') map[i][j] = '1';
				else map[i][j] = '/';	// '/' -> '0' -> '1' -> '2'
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		// 입력 초기화
		init();
		
		reverse(1);
		
		// 결과 출력
		printMap();
	}

	private static void reverse(int time) {
		visitedInit();
		
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				
				if (visited[i][j]) continue;
				
				visited[i][j] = true;
				
				// map[i][j] == '2'
				if (map[i][j] != '2') {
					map[i][j]++;
					continue;
				}

				map[i][j] = '*';
				for (int k = 0; k < 4; k++) {
					int nx = i + dx[k];
					int ny = j + dy[k];
					
					if (isValidXY(nx, ny) && map[nx][ny] != '2') {
						map[nx][ny] = '*';
						visited[nx][ny] = true;
					}
				}
			}
		}

		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (map[i][j] == '*') map[i][j] = '/';
			}
		}

		if (++time != N) reverse(time);
	}

	private static void visitedInit() {
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				visited[i][j] = false;
			}
		}
	}

	private static boolean isValidXY(int nx, int ny) {
		return nx>=0&&nx<R&&ny>=0&&ny<C;
	}
	
	private static void printMap() {
		StringBuilder sb = new StringBuilder(10);
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (map[i][j] == '/')
					sb.append('.');
				else
					sb.append('O');
			}
			sb.append("\n");
		}
		System.out.print(sb);
	}
}

/**
 *  16918. 봄버맨
 */