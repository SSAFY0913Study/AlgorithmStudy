import java.io.*;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
class Main {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		char[] S = br.readLine().toCharArray();
		int length = S.length;
		char[] T = br.readLine().toCharArray();
		int tLength = T.length;
		
		Deque<Integer> deque = new LinkedList<>();
		StringBuilder sb = new StringBuilder();
		int idx = 0;
		int count = 0;
		
		while (idx <length) {
			if (S[idx] == T[count]) {
				if (count == (tLength-1)) {
					count = deque.size() > 0 ? deque.pollLast() : 0;
				}
				else {
					count++;
				}
			}
			else if (S[idx] == T[0]) {
				if (count > 0) {
					deque.addLast(count);
				}
				
				count = 1;
			}
			else {
				if (count > 0) {
					deque.add(count);
					count = 0;
				}
				while (!deque.isEmpty()) {
					int l = deque.pollFirst();
					for (int i = 0; i < l; i++) {
						sb.append(T[i]);
					}
				}
				sb.append(S[idx]);
			}
			
			idx++;
		}
		
		// count 0인지 확인
		if (count > 0) {
			deque.addLast(count);
		}
		// 스택에 남은 문자 처리
		while (!deque.isEmpty()) {
			int l = deque.pollFirst();
			for (int i = 0; i < l; i++) {
				sb.append(T[i]);
			}
		}
		
		if (sb.length() != 0)
			System.out.println(sb);
		else
			System.out.println("FRULA");
	}
}


/**
  * 백준 9935. 문자열 폭발
  * 
	문제
	상근이는 문자열에 폭발 문자열을 심어 놓았다. 폭발 문자열이 폭발하면 그 문자는 문자열에서 사라지며, 남은 문자열은 합쳐지게 된다.
	
	폭발은 다음과 같은 과정으로 진행된다.
	
	문자열이 폭발 문자열을 포함하고 있는 경우에, 모든 폭발 문자열이 폭발하게 된다. 남은 문자열을 순서대로 이어 붙여 새로운 문자열을 만든다.
	새로 생긴 문자열에 폭발 문자열이 포함되어 있을 수도 있다.
	폭발은 폭발 문자열이 문자열에 없을 때까지 계속된다.
	상근이는 모든 폭발이 끝난 후에 어떤 문자열이 남는지 구해보려고 한다. 남아있는 문자가 없는 경우가 있다. 이때는 "FRULA"를 출력한다.
	
	폭발 문자열은 같은 문자를 두 개 이상 포함하지 않는다.

	입력
	문자열 (1 <= S <= 1,000,000)
	폭발 문자열 (1 <= T <= 36)
	
	두 문자열은 모두 알파벳 소문자와 대문자, 숫자 0, 1, ..., 9로만 이루어져 있다.
	
	출력
	모든 폭발이 끝난 후 남은 문자열
**/