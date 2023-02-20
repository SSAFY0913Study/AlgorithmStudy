package algorithm.boj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

class Position {
	int r;
	int c;
	int time;
	
	public Position(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	public Position(int r, int c, int time) {
		this.r = r;
		this.c = c;
		this.time = time;
	}
	
}
public class Main_17142_연구소3 {
	static final int[] dr = {0, 0, -1, 1};
	static final int[] dc = {-1, 1, 0, 0};
	static final int MAX_TIME = 50000;
	
	static int M;
	static int numberOfVirus = 0;
	static int[] activeVirus;
	static List<int[]> possiblePositions;
	
	static int N;
	static Position[] virus;
	
	public static void combination(int start, int count) {
		if (count == M) {
			possiblePositions.add(Arrays.copyOf(activeVirus, M));
			return;
		}
		for (int i = start; i < numberOfVirus; i++) {
			activeVirus[count] = i;
			combination(i + 1, count + 1);
		}
	}
	
	public static int simulation(int[][] board, int[] virusPosition) {
		Deque<Position> queue = new ArrayDeque<>(10);
		int[][] visited = new int[N][N];
		int time;
		
		for (int index : virusPosition) {
			board[virus[index].r][virus[index].c] = 2;
			queue.add(virus[index]);
		}
		while (!queue.isEmpty()) {
			Position thisPos = queue.poll();
			time = visited[thisPos.r][thisPos.c] + 1;
			for (int d = 0; d < 4; d++) {
				int newR = thisPos.r + dr[d];
				int newC = thisPos.c + dc[d];
				if (newR >= 0 && newR < N && newC >= 0 && newC < N
						&& board[newR][newC] == 0 && visited[newR][newC] == 0) { 
					visited[newR][newC] = time;
					queue.add(new Position(newR, newC));
				}
			}
		}
		int result = MAX_TIME;
		//printBoard(board);
		for (int i = 0; i < numberOfVirus; i++) {
			board[virus[i].r][virus[i].c] = 2;
			visited[virus[i].r][virus[i].c] = 0;
		}
		if (isFull(board, visited)) { //use empty count
			result = getMax(visited);
		}
		for (int i = 0; i < numberOfVirus; i++) {
			board[virus[i].r][virus[i].c] = 0;
		}
		//printBoard(visited);
		//System.out.println(result);
		return result;
	}
	
	public static int getMax(int[][] visited) {
		int length = visited.length;
		int max = 0;
		for (int r = 0; r < length; r++) {
			for (int c = 0; c < length; c++) {
				max = Math.max(visited[r][c], max);
			}
		}
		return max;
	}
	
	public static boolean isFull(int[][] board, int[][] visited) {
		int length = board.length;
		for (int r = 0; r < length; r++) {
			for (int c = 0; c < length; c++) {
				if (board[r][c] == 0 && visited[r][c] == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static void printBoard(int[][] board) {
		for (int i = 0; i < board.length; i++) {
			System.out.println(Arrays.toString(board[i]));
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		M = Integer.parseInt(input[1]);
		
		int[][] board = new int[N][N];
		virus = new Position[10];
		
		for (int r = 0; r < N; r++) {
			input = br.readLine().split(" ");
			for (int c = 0; c < N; c++) {
				board[r][c] = Integer.parseInt(input[c]);
				if (board[r][c] == 2) {
					board[r][c] = 0;
					virus[numberOfVirus] = new Position(r, c, 0);
					numberOfVirus++;
				}
				
			}
		}
		
		possiblePositions = new ArrayList<>(10);
		activeVirus = new int[M];
		
		combination(0, 0);
		int minTime = MAX_TIME;
		for (int[] thisPosition : possiblePositions) {
			minTime = Math.min(simulation(board, thisPosition), minTime);
		}
		if (minTime == MAX_TIME) {
			bw.append("-1");
		}
		if (minTime < MAX_TIME) {
			if (minTime < 0) {
				minTime = 0;
			}
			bw.append(Integer.toString(minTime));
		}
		bw.flush();
		br.close();
		bw.close();
	}	
}