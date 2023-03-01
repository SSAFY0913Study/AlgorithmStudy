import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

//18404 현명한 나이트

//최소 이동 수 -> 나이트의 위치에서 bfs 8방향 탐색; visited 배열 필요; 
//모든 적 나이트 탐색 시 탐색 종료

class Position {
	int r;
	int c;
	public Position(int r, int c) {
		super();
		this.r = r;
		this.c = c;
	}
}

public class Main_18404_현명한나이트 {
	static int[] dr = {-2, -2, -1, -1, 1, 1, 2, 2};
	static int[] dc = {-1, 1, -2, 2, -2, 2, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		int n = Integer.parseInt(input[0]);
		int m = Integer.parseInt(input[1]);
		
		input = br.readLine().split(" ");
		int x = Integer.parseInt(input[0]);
		int y = Integer.parseInt(input[1]);
		
		int[][] enemyMap = new int[n + 1][n + 1];
		int[] minimumMoves = new int[m + 1]; //인덱스 1부터
		for (int i = 1; i <= m; i++) {
			input = br.readLine().split(" ");
			int enemyR = Integer.parseInt(input[0]);
			int enemyC = Integer.parseInt(input[1]);
			enemyMap[enemyR][enemyC] = i;
		}
		
		Queue<Position> queue = new ArrayDeque<>();
		int[][] visited = new int[n + 1][n + 1];
		int enemyCount = 0;
		queue.add(new Position(x,y));
		visited[x][y] = 1;
		
		Position current;
		while (!queue.isEmpty()) { //나이트 위치부터 8가지 이동 방법으로 bfs
			current = queue.poll();
			int turn = visited[current.r][current.c] + 1;
			for (int d = 0; d < 8; d++) {
				int newR = current.r + dr[d];
				int newC = current.c + dc[d];
				
				if (newR <= 0 || newR > n || newC <= 0 || newC > n) { //바운더리 체크
					continue;
				}
				if (visited[newR][newC] > 0) { //방문 체크
					continue;
				}
				queue.add(new Position(newR, newC));
				visited[newR][newC] = turn;
				if (enemyMap[newR][newC] > 0) { //적이 존재하는 칸인 경우
					int enemyNum = enemyMap[newR][newC];
					minimumMoves[enemyNum] = turn - 1; //시작점에서 턴이 1인상태로 시작해서 1을 빼야한다
					enemyCount++;
				}
			}
			if (enemyCount == m) {
				break;
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= m; i++) {
			sb.append(minimumMoves[i]).append(' ');
		}
		System.out.println(sb.toString());
	}
}
