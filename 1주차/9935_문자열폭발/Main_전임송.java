package algorithm.boj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;

class Main_9935 {
	public static boolean explode(String target, Deque<Character> queue) {
		int length = target.length();
		Deque<Character> tmpQueue = new ArrayDeque<>(length);
		boolean isExplosive = true;
		for (int i = 1; i < length; i++) {
			if (queue.isEmpty()) {
				isExplosive = false;
				break;
			}
			char tmp = queue.pollLast();
			tmpQueue.add(tmp);
			
			if (target.charAt(length - 1 - i) != tmp) {
				isExplosive = false;
			}
		}
		if (!isExplosive) {
			while (!tmpQueue.isEmpty()) {
				queue.add(tmpQueue.pollLast());
			}
		}
		return isExplosive;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		String input = br.readLine();
		String target = br.readLine();
		
		Deque<Character> queue = new ArrayDeque<>();
		
		int length = input.length();
		char lastCharacter = target.charAt(target.length() - 1);
		for (int i = 0; i < length; i++) {
			char thisCharacter = input.charAt(i);
			if (thisCharacter == lastCharacter) { //타겟의 마지막 문자면 앞으로 이동하면서 poll 하고 타켓[i]와 비교한다
				if (explode(target, queue)) {
					continue;
				}
			}
			queue.add(thisCharacter); //ABBCABCC
		}
		if (queue.isEmpty()) {
			bw.append("FRULA");
		}
		while (!queue.isEmpty()) {
			bw.append(queue.poll());
		}
		
		bw.flush();
		br.close();
		bw.close();
	}	
}