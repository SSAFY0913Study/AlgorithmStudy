import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

class Main {
	
	// 땅(양분)
	//  rand[N][N]
	
	// K번 반복
	// 나무 나이 별 리스트로 관리
	
	static class Tree {
		int v;  	// 버전(현재 년도보다 낮을 때만 작업)
		int x, y;	// 좌표
		int age;
		public Tree(int v, int x, int y, int age) {
			this.v = v;
			this.x = x;
			this.y = y;
			this.age = age;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Tree [v=").append(v).append(", x=").append(x).append(", y=").append(y).append(", age=")
					.append(age).append("]");
			return builder.toString();
		}
	}
	
	static int[] dx = { -1, -1, -1,  0,  1,  1,  1,  0};
	static int[] dy = { -1,  0,  1,  1,  1,  0, -1, -1};
	
	public static void main(String[] args) throws Exception {
		init();
		
		// K번 반복
		int year = 0;
		while(year++ < K) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					Deque<Tree> trees = treesMap[i][j];
					if (!trees.isEmpty()) {
					
						// 봄 : 나무 양분 흡수
						//  나이 어린 나무부터 흡수 : O(나무의 개수)
						//  땅의 양분이 나이보다 적을 경우 나무 사망
						//  나무가 사망하면 큐에 저장
						int idx = 0;
						int size = trees.size();
						for (int k = 0; k < size; k++) {
							Tree t = trees.pollFirst();
							
							if (t.v == year) {
								// 현재 단계에서 추가된 나무는 작업하지 않고 다시 뒤로 집어넣음
								trees.offerLast(t);
								continue;
							}
							
							if (t.age > rand[i][j]) {
								// 양분이 부족하므로 사망 처리.
								addition[i][j] += t.age/2;
								
								treeCnt--;
								continue;
							}
							
							// 나무 성장
							rand[i][j] -= t.age;
							t.v++;	// 버전, 나이 증가.
							t.age++;
							
							// 가을 : 나무 번식
							//  나이가 5의 배수인 나무 리스트를 순회하며 팔방 탐색하여 나무 추가 : O(나무의 개수)
							if (t.age % 5 == 0) {
								// 팔방순회
								for (int l = 0; l < 8; l++) {
									int nx = i + dx[l];
									int ny = j + dy[l];
									if (isValidCoord(nx, ny)) {
										treesMap[nx][ny].offerFirst(new Tree(year, nx, ny, 1));
										treeCnt++;
									}
								}
							}
	
							trees.offerLast(t);
						}
					}
					// 여름 : 죽은 나무 양분으로 변환
					//  사망한 나무의 양분을 땅의 양분으로 변환
					rand[i][j] += addition[i][j];
					addition[i][j] = 0;
					
					// 겨울 : 양분 추가  
					//  추가되는 양분 A[N][N]
					//  rand 배열에 A 배열을 더함
					rand[i][j] += A[i][j];
				}
			}
			
		}
		
		// 살아있는 나무의 개수 반환
		System.out.println(treeCnt);
	}
	
	private static boolean isValidCoord(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < N;
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int N, M, K, MAX_AGE, treeCnt;
	static int[][] rand, A, addition;
	static Deque<Tree>[][] treesMap;
	private static void init() throws Exception {
		input();
		N = readInt();	// 땅의 단위 크기
		M = readInt();	// 입력 받은 나무의 개수
		K = readInt();	// 반복 횟수
		rand = new int[N][N];	// 땅의 양분
		A = new int[N][N];		// 겨울에 추가할 양분
		addition = new int[N][N];		// 겨울에 추가할 양분
		treeCnt = M;
		MAX_AGE = K+11;	// 나무 초기 나이 10 + 0 인덱스 제외 = 11
		treesMap = new Deque[N][N];	// 나이 별 나무의 위치 리스트. 배열 인덱스 = 나이
		for (int i = 0; i < N; i++) {
			treesMap[i] = new Deque[N];
			for (int j = 0; j < N; j++) {
				treesMap[i][j] = new LinkedList<Tree>();
			}
		}
		
		// rand, A 배열 초기화
		for (int i = 0; i < N; i++) {
			input();
			for (int j = 0; j < N; j++) {
				rand[i][j] = 5;	// 초기 양분 5로 초기화
				A[i][j] = readInt();
			}
		}

		// 나무 초기화
		for (int i = 0; i < M; i++) {
			input();
			int x = readInt()-1;
			int y = readInt()-1;
			int age = readInt();
			treesMap[x][y].offer(new Tree(0, x, y, age));
		}
	}

	private static void input() throws Exception {
		st = new StringTokenizer(br.readLine(), " "); 
	}
	
	private static int readInt() {
		return Integer.parseInt(st.nextToken());
	}
}

/**
 *	16235. 나무 재테크 
 */