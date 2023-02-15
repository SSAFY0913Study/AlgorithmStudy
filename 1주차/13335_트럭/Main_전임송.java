package algorithm.boj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;

class Main_13335 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
			
		String[] input = br.readLine().split(" ");
		int n = Integer.parseInt(input[0]);
		int w = Integer.parseInt(input[1]);
		int l = Integer.parseInt(input[2]);
		int[] weights = new int[n + 1];
		input = br.readLine().split(" ");
		for (int i = 0; i < n; i++) {
			weights[i] = Integer.parseInt(input[i]);
		}
		int totalTime = 0; 
		int weightSum = 0; 	
		int inCount = 0;
		int outCount = 0; 
		
		Deque<Integer> queue = new ArrayDeque<>(w);
		for (int i = 0; i < w; i++) { //ť�� �ٸ� ���̸�ŭ 0 �߰�
			queue.add(0);
		}
		while (true) {
			totalTime++;
			
			int outWeight = queue.poll(); 
			if (outWeight > 0) { //Ʈ���� �ٸ��� �� �ǳ��� ��
				outCount++;
			}
			if (outCount == n) { //��� Ʈ���� �ٸ��� �ǳ��� ��
				break;
			}
			weightSum -= outWeight; 
			
			if (inCount < n && weightSum + weights[inCount] <= l) { //�̹� Ʈ���� �ٸ��� �÷��� �ִ������� �� ���� ��
				queue.add(weights[inCount]);
				weightSum += weights[inCount];
				inCount++;
				continue;
			}
			queue.add(0); 
		}
		bw.append(Integer.toString(totalTime));
		bw.flush();
		br.close();
		bw.close();
	}	
}