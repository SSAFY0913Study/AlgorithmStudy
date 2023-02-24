import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Main2 {
	
	static class Coord {
		int x, y;
		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public Coord(int[] p) {
			this.x = p[0];
			this.y = p[1];
		}
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M;
	static Coord knightPosition;
	static List<Coord> enemyList;
	
	private static void init() throws Exception {
		int[] input = inputToIntArray();
		N = input[0];
		M = input[1];
		knightPosition = new Coord(inputToIntArray());
		enemyList = new ArrayList<>();
		for (int i = 0; i < M; i++) {
			enemyList.add(new Coord(inputToIntArray()));
		}
	}

	public static void main(String[] args) throws Exception{
		init();

		
		
		br.close();
	}
	
	private static int[] inputToIntArray() throws IOException {
		String[] input = br.readLine().split(" ");
		int[] res = new int[input.length];
		for (int i = 0, length = input.length; i < length; i++) {
			res[i] = Integer.parseInt(input[i]);
		}
		return res;
	}
}

/**
 *  18404. 현명한 나이트
 */