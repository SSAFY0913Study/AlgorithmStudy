import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Main {

	private static int R, C, N;
	
	private static char[][] map;
	private static List<int[]> bombs;
	
	private static int[] dx = { -1,  0,  1,  0};
	private static int[] dy = {  0,  1,  0, -1};
	
	public static void main(String[] args) throws Exception {
		init();
		
		int flag = N%4;
		if (flag == 3) {
			reverse();
		}
		else if (flag == 0 || flag == 2){
			fillZero();
		}
		
		StringBuilder sb = new StringBuilder(10);
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				sb.append(map[i][j]);
			}
			sb.append("\n");
		}
		System.out.print(sb);
	}
	
	private static void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		R = Integer.parseInt(input[0]);
		C = Integer.parseInt(input[1]);
		N = Integer.parseInt(input[2]);
		map = new char[R][C];
		bombs = new ArrayList<>();
		
		for (int i = 0; i < R; i++) {
			map[i] = br.readLine().toCharArray();
		}
	}
	
	private static void fillZero() {
		for (int i = 0; i < R; i++) {
			Arrays.fill(map[i], 0, C, 'O');
		}
	}

	private static void reverse() {
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				// map[i][j] == 'O'
				if (map[i][j] != 'O') continue;
			
				map[i][j] = '*';
				for (int k = 0; k < 4; k++) {
					int nx = i + dx[k];
					int ny = j + dy[k];
					
					if (isValidXY(nx, ny) && map[nx][ny] != 'O')
						map[nx][ny] = '*';
				}
			}
		}

		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (map[i][j] == '*') map[i][j] = '.';
				else map[i][j] = 'O';
			}
		}
	}

	private static boolean isValidXY(int nx, int ny) {
		return nx>=0&&nx<R&&ny>=0&&ny<C;
	}

}

/**
 *  16918. 봄버맨
 */