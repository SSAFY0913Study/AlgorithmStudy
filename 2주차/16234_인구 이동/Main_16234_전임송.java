import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

//16234 인구이동

//BFS로 각 칸에서 시작하며 연합 확인, 탐색 진행하면서 인구수 더해주고 탐색 완료 하면 인구값 저장; 만약 이미 연합 포함된 칸이면 시작 하지 않는다. 
//모든 연합 인구 수 구한 후 모든칸 인구 수 업데이트

class Position {
	int r;
	int c;
	public Position(int r, int c) {
		super();
		this.r = r;
		this.c = c;
	} 
}
public class Main_16234_인구이동 {
	static int N;
	static int L;
	static int R;
	static int[][] land;
	
	static int[] dr = {0, 0, -1, 1};
	static int[] dc = {-1, 1, 0, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		L = Integer.parseInt(input[1]);
		R = Integer.parseInt(input[2]);
		
		land = new int[N][N];
		for (int r = 0; r < N; r++) {
			input = br.readLine().split(" ");
			for (int c = 0; c < N; c++) {
				land[r][c] = Integer.parseInt(input[c]);
			}
		}
		
		int updateCount = 0;
		while (true) {
			if (!updatePopulation()) {
				break;
			}
			updateCount++;
		}
		System.out.println(updateCount);
	}
	
	public static boolean updatePopulation () {
		int[][] union = new int[N][N];
		int[] population = new int[(N * N) + 1]; // 최대 연합 수; 연합 번호 1부터 시작
		int unionCount = 1;
		Queue<Position> queue = new ArrayDeque<>();
		Position current;
		int populationSum;
		int landSize;
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (union[r][c] > 0) {
					continue;
				}
				
				queue.add(new Position(r, c));
				union[r][c] = unionCount;
				populationSum = land[r][c];
				landSize = 1;
				
				while (!queue.isEmpty()) {
					current = queue.poll();
					for (int d = 0; d < 4; d++) {
						int newR = current.r + dr[d];
						int newC = current.c + dc[d];
						
						if (newR < 0 || newR >= N || newC < 0 || newC >= N 
								|| union[newR][newC] > 0) { //바운더리 체크
							continue;
						}
						int difference = Math.abs(land[newR][newC] - land[current.r][current.c]);
						if (difference < L || difference > R) { //조건 체크
							continue;
						}
						queue.add(new Position(newR, newC));
						union[newR][newC] = unionCount;
						populationSum += land[newR][newC];
						landSize++;
					}
				}
				population[unionCount] = populationSum / landSize; //새로운 인구수 계산
				unionCount++;
			}
		}
		
		if (unionCount - 1 == N * N) { //인구 이동이 더 이상 없는 경우
			return false;
		}
		
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				land[r][c] = population[union[r][c]]; //새로운 인구수로 업데이트
			}
		}
		return true;
	}
}
