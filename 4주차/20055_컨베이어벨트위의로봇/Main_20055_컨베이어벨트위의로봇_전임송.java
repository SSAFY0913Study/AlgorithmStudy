import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//20055 컨베이어 벨트 위의 로봇
//내구도 저장
//벨트 위 상황 저장 -> 1차원 배열 2N 크기, 벨트 위 배열 시작 인덱스, 끝 인덱스 저장, 이동 - 인덱스 수정

public class Main_20055_컨베이어벨트위의로봇_전임송 {	
	static int N;
	static int K;

	static int[] belt;
	static boolean[] robot;
	static int beltLength;
	static int start;
	static int end;
	static int count = 0;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		K = Integer.parseInt(input[1]);
		beltLength = 2 * N;
		belt = new int[beltLength];
		robot = new boolean[beltLength];
		input = br.readLine().split(" ");
		for (int r = 0; r < beltLength; r++) {
			belt[r] = Integer.parseInt(input[r]);
		}
		start = 0; //벨트 위 시작 인덱스
		end = N - 1; //벨트 위 끝 인덱스
		System.out.println(runSimulation());
	}
	
	
	private static int runSimulation() {
		int step = 0;
		while (count < K) {
			step++;
			moveBelt();
			moveRobot();
			addRobot();
		}
		return step;
	}
	
	public static void moveBelt() { //벨트이동
		start = reduceIndex(start);
		end = reduceIndex(end);
		checkEnd();
	}
	
	public static void moveRobot() {
		int index = end;
		index = reduceIndex(index);
		for (int i = 1; i < N; i++) { //n - 1만큼 역순으로 로봇 이동
			if (robot[index]) {
				int nextIndex = (index + 1) % beltLength;
				if (!robot[nextIndex] && belt[nextIndex] > 0) {
					robot[index] = false;
					robot[nextIndex] = true;
					useBelt(nextIndex);
				}
			}
			checkEnd();
			index = reduceIndex(index);
		}
	}
	
	public static void addRobot() {
		if (belt[start] > 0) {
			robot[start] = true;
			useBelt(start);
		}
	}
	
	public static int reduceIndex(int index) {
		index--;
 		if (index < 0) {
 			index = beltLength - 1;
 		}
 		return index;
	}
	
	public static void useBelt(int index) {
		belt[index]--;
		if (belt[index] == 0) {
			count++;
		}
	}
	public static void checkEnd() {
		if (robot[end]) {
			robot[end] = false;
		}
	}
}