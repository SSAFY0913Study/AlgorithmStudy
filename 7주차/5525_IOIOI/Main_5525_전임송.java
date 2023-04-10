import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//5525 IOIOI
/*
 * 문자열 읽으면서 연결된 IOI 수 기록
 * 각 연결된 IOI 마다 Pn의 개수는 연결된 수 - n + 1
 */

public class Main {	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());
		String line = br.readLine();
		List<Integer> record = new ArrayList<>(10);
		int conseq = 0;
		int started = 0;
		for (int i = 0; i < M; i++) {
			char character = line.charAt(i);
			//System.out.println(started);
			if (character == 'O') {
				if (started != 1) { //I다음에 오는 O가 아닌경우
					started = 0;
					record.add(conseq);
					conseq = 0;
					continue;
				}
				started++;
			}
			if (character == 'I') {
				if (started == 0) {
					started++;
					continue;
				}
				if (started == 1) { //II 로 IOI 패턴이 끝긴경우
					record.add(conseq);
					conseq = 0;
				}
				if (started == 2) {
					started = 1;
					conseq++;
				}
			}
			
		}
		record.add(conseq);
		int result = 0;
		for (int connected : record) {
			if (connected < N) {
				continue;
			}
			result += connected - N + 1;
		}
		
		System.out.println(result);
	}
}
