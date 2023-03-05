import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;


//16235 나무재테크
// 처음 양분  = 5
// 1. 나이만큼 땅의 양분을 먹고 나이 + 1, 한칸에 여러 개의 나무가 있다면 나이가 어린 나무부터 양분을 먹는다.
	//칸마다 나이순 pq?
	//만약 양분이 부족해 자신의 나이만큼 먹을 수 없으면 나무는 죽는다.
//2. 죽은 나무는 나이 / 2 만큼의 양분으로 변한다.
//3. 나이가 5의 배수인 나무가 번식하여 인접한 8개의 칸에 나이가 1인 나무가 추가된다.
//4. 땅에 A배열 값 대로 양분을 추가한다
//k년동안 반복
public class Main_16235_나무재태크_전임송 {	
	static final int INITIAL_RESOURCE = 5;
	static final int INITIAL_AGE = 1;
	static final int AGE_CONDITION = 5;
	
	static int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
	static int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
	
	static int N;
	static int M;
	static int K;
	static int[][] A;
	static int[][] resource;
	static int[][] additionalResource;
	
	static ArrayDeque<Integer>[][] land; //나무 정보 저장 **우선순위큐로 하면 시간초과, 정렬없이 덱을 사용해서 새로 생긴 나무들 왼쪽에 넣으면서 진행
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		M = Integer.parseInt(input[1]);
		K = Integer.parseInt(input[2]);
		A = new int[N][N];
		
		for (int r = 0; r < N; r++) {
			input = br.readLine().split(" ");
			for (int c = 0; c < N; c++) {
				A[r][c] = Integer.parseInt(input[c]);
			}
		}
		
		resource = new int[N][N];
		land = new ArrayDeque[N][N]; //초기화
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				land[r][c] = new ArrayDeque<>();
				resource[r][c] = INITIAL_RESOURCE;
			}
		}
		
		for (int i = 0; i < M; i++) {
			input = br.readLine().split(" ");
			int r = Integer.parseInt(input[0]) - 1;
			int c = Integer.parseInt(input[1]) - 1;
			int age = Integer.parseInt(input[2]);
			land[r][c].add(age); //나무 추가  **입력은 같은 자리에 나무 추가 없음 **
		}
		
		for (int y = 0; y < K; y++) {
			spring();
			summer();
			fall();
			winter();
			//print(land);
			//print(resource);
			
		}
		System.out.println(countTrees());
	}
	
	public static void spring() { 
		additionalResource = new int[N][N];
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (land[r][c].isEmpty()) {
					continue;
				}
				growTree(r, c);
			}
		}
	}
	
	public static void growTree(int r, int c) {
		int size = land[r][c].size();
		for (int i = 0; i < size; i++) { //모든 나무 탐색(나이 적은 순으로)
			int age = land[r][c].poll(); //덱 앞에서 뺀다
			if (resource[r][c] < age) { //자원이 충분하지않으면 죽는다.
				additionalResource[r][c] += age / 2; //여름에 추가할 자원 저장
				continue;
			}
			resource[r][c] -= age; //자원 소비
			land[r][c].add(age + 1); //나이 증가; 덱 뒤로 넣는다
		}
	}
	public static void summer() {
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				resource[r][c] += additionalResource[r][c]; //죽은 나무 자원 추가
			}
		}
	}
	
	public static void fall() {
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				int size = land[r][c].size();
				for (int i = 0; i < size; i++) { //모든 나무 탐색(나이 적은 순으로)
					int age = land[r][c].poll();
					if (age % AGE_CONDITION == 0) { //5의 배수이면
						spread(r, c);
					}
					land[r][c].add(age); 
				}
			}
		}
	}
	
	public static void winter() {
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				resource[r][c] += A[r][c]; //a배열 값 추가
			}
		}
	}
	public static void spread(int r, int c) { //8방으로 나이 1인 나무 추가
		for (int d = 0; d < 8; d++) {
			int newR = r + dr[d];
			int newC = c + dc[d];
			
			if (newR < 0 || newR >= N || newC < 0 || newC >= N) {
				continue;
			}
			land[newR][newC].push(INITIAL_AGE); //새로 추가하는 나무는 나이가 가장 어리기 때문에 덱의 앞쪽으로 추가
		}
	}
	
	public static int countTrees() {
		int count = 0;
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				count += land[r][c].size();
			}
		}
		return count;
	}
	public static void print(int[][] board) {
		for (int r = 0; r < board.length; r++) {
			System.out.println(Arrays.toString(board[r]));
		}
		System.out.println();
	}
	public static void print(ArrayDeque<Integer>[][] board) {
		for (int r = 0; r < board.length; r++) {
			System.out.println(Arrays.toString(board[r]));
		}
		System.out.println();
	}
}

/*5 2 9
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 1 3
3 2 3*/ //34