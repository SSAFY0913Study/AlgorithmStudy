import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

class Main {
	
	private static int N;
	private static int[] powers;	
	private static List<Integer>[] dpArray;
	private static Map<Integer, Integer> idxMap;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		powers = new int[N + 1];
		dpArray = new List[N + 1];
		idxMap = new HashMap<>();
		for (int i = 0; i <= N; i++) {
			dpArray[i] = new ArrayList<>();
			dpArray[i].add(0);
		}
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			powers[i] = Integer.parseInt(st.nextToken());
		}
		// idxMap 초기화
		int[] orders = powers.clone();
		Arrays.sort(orders);
		for (int i = 0; i < N; i++) {
			idxMap.put(orders[N-i], i+1);	// idx 내림차순으로 설정
		}
		
		// dpArray 계산
		for (int i = 1; i <= N; i++) {
			int idx = idxMap.get(powers[i]);
			
			// 1 ~ idx 전까지 값 설정
			for (int j = 1; j <= N; j++) {
				List<Integer> prevList = dpArray[j-1];
				List<Integer> currentList = dpArray[j];
				
				currentList.add( Math.max(
						// 한단계 위 리스트에서 값을 가져옴
						// idx가 현재 병사 전투력일 경우에 1을 증가
						prevList.get(prevList.size()-1) + (j == idx ? 1 : 0),	
						currentList.get(currentList.size()-1)) );
			}
		}
		
		System.out.println(N - dpArray[N].get(dpArray[N].size()-1));
	}
}

/**
  * 백준 18353. 병사 배치하기
  * 
	문제
		전투력이 내림차순을 만족하도록 중간에서 병사를 삭제했을 때, 최소 삭제 수 구하기
		
	입력
		N			: 병사 수 (1 <= N <= 2000)
		power[N]	: 전투력  (1 <= power[] <= 10,000,000 
	
	출력
		res		: 삭제 병사 수
		
	아이디어
		탑 문제와 비슷한 방식
		dp와 리스트 사용
		(실패) => 1차원 dp를 2차원 dp 리스트로 바꾸기
		
		
		
		// (총 사이즈 - 내림차순 조합 중 최대 길이) = 삭제 수.   시간복잡도 O(2^N) 탈락
		
**/