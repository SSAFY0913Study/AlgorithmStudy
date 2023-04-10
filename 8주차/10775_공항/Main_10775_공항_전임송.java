import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/*
 * 10775 공항
 * 1~g
 * 그리디, 가장 뒷쪽 게이트부터 도킹
 * 각 P값마다 최대 값 갱신, 
 * 갱신 된 값의 최대값을 가져오기 위해 UNION FIND 활용
 * 
 */

public class Main {		
	static int[] maxValue;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int G = Integer.parseInt(br.readLine());
		int P = Integer.parseInt(br.readLine());
		int[] planes = new int[P];
		maxValue = new int[G + 1];
		for (int i = 0; i < P; i++) {
			planes[i] = Integer.parseInt(br.readLine());
		}
		for (int i = 1; i <= G; i++) {
			maxValue[i] = i;
		}
		for (int i = 0; i < P; i++) {
			int maxGate = findMax(planes[i]);
			if (maxGate == 0) {
				System.out.println(i);
				return;
			}
			maxValue[maxGate] = findMax(maxGate - 1);
		}
		System.out.println(P);
	}
	public static int findMax(int n) {
		if (maxValue[n] == n) {
			return n;
		}
		return maxValue[n] = findMax(maxValue[n]);
	}
}
