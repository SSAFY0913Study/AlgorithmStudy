import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

//4991 로봇 청소기

//모든 더러운 칸 방문 순서를 순열로
	//더러운칸 3개고 0 1 2 순열이면 더러운칸[0] 먼저 찾아 시작점부터 bfs, 
	//도착하면 더러운칸[1] 찾아 현재 위치 부터 bfs...
//시작부터 더러운 칸 하나 방문할때마다 bfs로 최소 이동 거리 계산 ---> 시간초과

//시작점 + 모든 더러운 칸부터 bfs해서 다른 더러운 칸까지 최단 거리 2차원 배열에 저장 후 순열마다 단순 비교

class Position {
	int r;
	int c;
	int distance;
	
	public Position(int r, int c, int distance) {
		super();
		this.r = r;
		this.c = c;
		this.distance = distance;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		return r == ((Position)obj).r && c == ((Position)obj).c;
	}
}

public class Main_4991_로봇청소기 {	
	static final char CLEAN = '.';
	static final char DIRT = '*';
	static final char FURNITURE = 'x';
	static final char START = 'o';
	static final int STOP = 0;
	
	static int col;
	static int row;
	static char[][] room;
	static int[][] distance;
	static int dirtCount;
	static int minDistance;
	static int[] visitOrder;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String[] input;

		while (true) {
			input = br.readLine().split(" ");
			
			col = Integer.parseInt(input[0]);
			row = Integer.parseInt(input[1]);
			if (row == STOP && col == STOP) { //0 0 입력 받으면 while 종료
				break;
			}
			
			room = new char[row][col];
			dirtCount = 0;
			minDistance = Integer.MAX_VALUE;
			
			List<Position> positions = new ArrayList<>(10); //시작 점 + 더러운칸 위치 저장; 인덱스로 더러운칸 넘버링
			String line; 
			for (int r = 0; r < row; r++) {
				line = br.readLine();
				for (int c = 0; c < col; c++) {
					room[r][c] = line.charAt(c);
					if (room[r][c] == DIRT) {
						dirtCount++;
						positions.add(new Position(r, c, 0));
					}
					if (room[r][c] == START) {
						positions.add(0, new Position(r, c, 0));
					}
				}
			}
			
			if (!getDistance(positions)) { //도달할 수 없는 더러운 칸이 존재하면
				sb.append(-1).append('\n');
				continue;
			}
			
			visitOrder = new int[dirtCount + 1]; //더러운칸이 아에 없는 경우를 위해 +1
			findPermutation(0, 0); //시작점에서 시작해서 모든 더러운 칸을 최소 이동으로 방문하는 순열 탐색
			
			sb.append(minDistance).append('\n');
		}
		System.out.println(sb.toString());
	}
	
	public static boolean getDistance(List<Position> positions) { //distance 배열 업데이트
		int[] dr = {0, 0, -1, 1};
		int[] dc = {-1, 1, 0, 0};
		distance = new int[dirtCount + 1][dirtCount + 1]; //더러운칸 개수 + 시작점; distance[from][to] = 거리
		for (int startIndex = 0; startIndex < dirtCount + 1; startIndex++) { // 시작점 + 모든 더러운 칸에서부터 bfs를 통해 각 지점까지 최단거리 계산 
			Queue<Position> queue = new ArrayDeque<>();
			boolean[][] visited = new boolean[row][col];
			int positionCount = dirtCount; //방문해야하는 지점 수; 더러운칸 + 시작점 - 자기자신
			
			Position initial = positions.get(startIndex);
			queue.add(initial);
			visited[initial.r][initial.c] = true;
			while (!queue.isEmpty()) {
				Position thisPos = queue.poll();
				for (int d = 0; d < 4; d++) {
					int newR = thisPos.r + dr[d];
					int newC = thisPos.c + dc[d];
					int newDistance = thisPos.distance + 1;
					if (newR < 0 || newR >= row || newC < 0|| newC >= col
							|| visited[newR][newC] || room[newR][newC] == FURNITURE) { //바운더리 체크, 가구 체크
						continue;
					}
					if (room[newR][newC] == START || room[newR][newC] == DIRT) { // 찾던 칸인 경우 배열에 거리 추가
						int arrivedIndex = positions.indexOf(new Position(newR, newC, 0)); //도착한 칸이 몇번째 더러운 칸인지 탐색
						distance[startIndex][arrivedIndex] = newDistance;
						positionCount--;
					}
					visited[newR][newC] = true;
					queue.add(new Position(newR, newC, newDistance));
				}
				if (positionCount == 0) { //더 이상 방문해야할 지점이 없을 때  
					break;
				}
			}
			if (positionCount > 0) { // 방문할 수 없는 더러운 칸이 존재할 때 
				return false;
			}
		}
		return true;
	}
	
	public static void findPermutation(int count, int flag) { //시작점 뺀 더러운 칸 방문 순서 순열 탐색
		if (count == dirtCount) {
			minDistance = Math.min(minDistance, getTotalDistance());
			return;
		}
		for (int i = 1; i <= dirtCount; i++) {
			if ((flag & 1 << i) != 0) {
				continue;
			}
			visitOrder[count] = i;
			findPermutation(count + 1, flag | 1 << i);
		}
	}
	
	public static int getTotalDistance() { //현재 방문 순서의 이동 거리를 계산한다.
		int prev = visitOrder[0];
		int totalDistance = distance[0][prev]; //시작점부터 첫 방문 더러운 칸까지 거리 추가
		for (int i = 0; i < dirtCount; i++) { 
			int next = visitOrder[i];
			totalDistance += distance[prev][next];
			prev = next;
		}
		return totalDistance;
	}
}