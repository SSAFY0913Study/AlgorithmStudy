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
	private static Queue<Integer> bombs;
	
	private static int[] dx = { -1,  0,  1,  0};
	private static int[] dy = {  0,  1,  0, -1};
	
	private static void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		R = Integer.parseInt(input[0]);
		C = Integer.parseInt(input[1]);
		N = Integer.parseInt(input[2]);
		
		if (N > 2) N = (N-3) % 4 + 3;
		
		map = new char[R][C];
		bombs = new LinkedList<>();
		
		for (int i = 0; i < R; i++) {
			map[i] = br.readLine().toCharArray();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		// 변수 초기화
		init();
		
		// N = 3, 5, 7, 9, 11, ...
		// 3 == 7 == 4k + 3 : 처음 폭탄이 터진 장면
		// 5 == 9 == 4k + 1 : 폭탄이 두 번째로 터진 장면
		if (N % 4 == 3) boom();
		else if (N % 2 == 0) changeBombAll();
		else if (N != 1 && N % 4 == 1) {
			boom(); 
			boom();
		}
		
		printMap();
	}

	private static void boom() {
		// 폭탄 위치 기억
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (map[i][j] == 'O') { 
					bombs.offer(i);
					bombs.offer(j);
				}
			}
		}
		
		// 모두 폭탄으로 바꿈
		changeBombAll();

		// 폭탄과 그 주변을 공백으로 바꿈 
		while(!bombs.isEmpty()) {
			int x = bombs.poll();
			int y = bombs.poll();
			map[x][y] = '.';
			for (int i = 0; i < 4; i++) {
				int nx = x+dx[i];
				int ny = y+dy[i];
				if (isValidXY(nx, ny))
					map[nx][ny] = '.';
			}
		}
	}

	private static boolean isValidXY(int nx, int ny) {
		return nx>=0&&nx<R&&ny>=0&&ny<C;
	}
	
	private static void changeBombAll() {
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				map[i][j] = 'O';
			}
		}
	}
	
	private static void printMap() {
		StringBuilder sb = new StringBuilder(10);
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
					sb.append(map[i][j]);
			}
			sb.append("\n");
		}
		System.out.print(sb);
	}
}

/**
 *  16918. 봄버맨
 */