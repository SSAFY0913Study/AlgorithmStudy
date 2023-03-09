import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());

		// N, K가 주어진다. 둘째 줄에는 A1, A2, ..., A2
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		int size = N * 2;
		int[] arr = new int[size];
		boolean[] robot = new boolean[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < size; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		int step = 1;
		while (true) {
			int zero = 0;
			// 벨트가 각 칸 위에 있는 로봇과 함께 한 칸 회전한다.
			// 벨트 회전
			int last = arr[size - 1];
			for (int i = size - 1; i >= 1; i--) {
				arr[i] = arr[i - 1];
			}
			arr[0] = last;

			// 로봇 회전
			for (int i = N - 1; i >= 1; i--) {
				robot[i] = robot[i - 1];
			}
			robot[N - 1] = false;
			robot[0] = false;

			// 로봇 이동
			// 로봇이 이동하기 위해서는 이동하려는 칸에 로봇이 없으며, 그 칸의 내구도가 1 이상 남아 있어야 한다.
			for (int i = N - 1; i >= 1; i--) {
				if (robot[i - 1] && !robot[i] && arr[i] >= 1) {
					robot[i - 1] = false;
					robot[i] = true;
					arr[i]--;
				}
			}
			
			// 올리는 위치에 있는 칸의 내구도가 0이 아니면 올리는 위치에 로봇을 올린다.
			if (arr[0] != 0) {
				robot[0] = true;
				arr[0]--;
			}
			
			// 내구도가 0인 칸의 개수가 K개 이상이라면 과정을 종료한다.
			for(int i=0;i<size;i++) {
				if(arr[i]==0) {
					zero++;
				}
			}
			
//			System.out.println("제로" + zero);
			
			if (zero >= K) {
				break;
			}
            
			// 그렇지 않다면 1번으로 돌아간다.
//			System.out.println(Arrays.toString(arr));
//			System.out.println(Arrays.toString(robot));
//			System.out.println("스텝" + step);
			step++;
		}
		// 종료되었을 때 몇 번째 단계가 진행 중이었는지 구해보자
		System.out.println(step);
	}
}
