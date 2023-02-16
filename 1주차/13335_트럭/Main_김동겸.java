import java.io.*;
class Main {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n, w, L;
		String[] input = br.readLine().split(" ");
		n = Integer.parseInt(input[0]);	// 트럭의 수
		w = Integer.parseInt(input[1]); // 다리의 길이
		L = Integer.parseInt(input[2]); // 최대 하중
		
		int[] a = new int[n];	// 트럭의 무게
		input = br.readLine().split(" ");
		for (int i = 0; i < n; i++) {
			a[i] = Integer.parseInt(input[i]);
		}
		
		int t = 0;				// 시간 변수
		int idx = 0;			// 현재 트럭 index
		int validWeight = L;	// 현재 다리 하중
		int[] removedWeight = new int[(n+2)*w];	// 트럭이 다리를 빠져나가는 시간에 감소될 하중 (트럭무게)
		while (true) {
			if (idx >= n && validWeight == L) break;	// 가리키는 트럭이 없고 다리에 트럭이 없을 때 탈출
			
			t++;
			validWeight += removedWeight[t];	// 트럭이 다리를 나갈 때 가용 하중을 트럭 무게만큼 증가
			
			if (idx < n) {
				if (validWeight < a[idx]) continue;	// 가용 하중보다 트럭의 무게가 크면, 가용 하중이 늘어날 때까지 시간 증가
				
				// 새로운 트럭 진입
				validWeight -= a[idx];
				removedWeight[t + w] = a[idx];	// 다리 길이 시간 만큼 뒤에 트럭 무게 저장
				idx++;
			}
		}
		
		System.out.println(t);	// 시간 출력
	}
}


/**
  * 백준 13335. 트럭
  * 
	입력
	세 개의 정수 n, w, L (1 ≤ n ≤ 1,000) ,(1 ≤ w ≤ 100), (10 ≤ L ≤ 1,000)
		: n - 다리를 건너는 트럭의 수, w - 다리의 길이, L 다리의 최대하중
	n개의 정수 a1, a2, ⋯ , an (1 ≤ ai ≤ 10) : ai는 i번째 트럭의 무게
	
	출력
	모든 트럭들이 다리를 건너는 최단시간
**/