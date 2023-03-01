import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

//1. 하나의 세로선에 인접한 가로선 개수가 짝수여야 한다
//2. 선에서 나갔다 들어올수 있어야한다
//백트래킹; 모든 조합 시도; 재귀
//세로선마다 배열, 가로선 카운터, 
//그래프 체크 함수, 가로 1부터 h 까지 내려가면서 자기 자신이 나오는지 체크
//재귀로 제일 왼쪽 가로선 빈자리 부터 subset 
public class Main_15684_사다리조작 {	
	static int N;
	static int M;
	static int H;
	static int[][] ladder;
	static int minCount = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		M = Integer.parseInt(input[1]);
		H = Integer.parseInt(input[2]);
		ladder = new int[H + 1][N + 1]; //둘 다 인덱스 1에서 시작
		for (int i = 0; i < M; i++) {
			input = br.readLine().split(" ");
			int h = Integer.parseInt(input[0]);
			int position = Integer.parseInt(input[1]);
			connect(h, position);
		}
		setLadder(1, 1, 0);
		if (minCount == Integer.MAX_VALUE) {
			minCount = -1;
		}
		System.out.println(minCount);
	}
	//이미 존재하는 가로선은 아에 배제해야 한다
	public static void setLadder(int h, int position, int count) { //모든 가능한 가로줄 subset 완전 탐색
		if (position == N) { //N까지 가로줄 추가했을 경우
			if (checkLadder()) { //정답체크
				minCount = Math.min(minCount, count);
			}
			return;
		} 
		
		if (h == H + 1) { //position의 이번 subset의 가로줄들이 다 추가된 후
			setLadder(1, position + 1, count); //인접한 가로줄 개수가 홀수일때만 진행했더니 틀림
			return;
		} 
		if (count < 3 && ladder[h][position] == 0 && ladder[h][position + 1] == 0) {//3개 이하 추가했고, 지금 위치에 가로선이 없고 양 옆 세로선에 인접한 가로선이 없을 때 	
			connect(h, position); //해당 위치 가로선 추가
			setLadder(h + 1, position, count + 1); //다음 가로선을 추가하는 subset
			disconnect(h, position);
		}
		setLadder(h + 1, position, count); //해당 가로선 미추가
	}
	
	public static boolean checkLadder() { //사다리 i에서 시작하고 i에서 끝나는지 확인
		for (int start = 1; start <= N; start++) {
			int position = start;
			for (int h = 1; h <= H; h++) {
				if (ladder[h][position] > 0) {
					position = ladder[h][position];
				}
			}
			if (position != start) {
				return false;
			}
		}
		return true;
	}
	
	public static void connect(int h, int position) { //pos 하고 pos + 1사이에 가로선을 놓는다
		ladder[h][position] = position + 1;
		ladder[h][position + 1] = position;
	}
	
	public static void disconnect(int h, int position) {
		ladder[h][position] = 0;
		ladder[h][position + 1] = 0;
	}
	//15684 사다리조작
}