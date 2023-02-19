import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

class Main {
	
	private static int N, result;
	private static List<Integer> dp;
	private static List<Integer> list;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		list = new ArrayList<>();
		dp = new ArrayList<>();
		list.add(Integer.MAX_VALUE);
		dp.add(0);
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			int curPower = Integer.parseInt(st.nextToken());
			int lastIndex = list.size()-1;
			
			// 현재 원소가 마지막 원소보다 작을 경우 비교하기 않고 리스트에 삽입
			if (list.get(lastIndex) > curPower) {
				list.add(curPower);
				dp.add(dp.get(lastIndex) + 1);	// 현재 까지 개수 저장
				continue;
			}
			
			int nowCnt = dp.get(lastIndex);	// 이전 요소 까지의 개수
			// 사용하는 경우와 사용하지 않는 경우 길이 비교
			while (list.get(lastIndex) < curPower) lastIndex--;	// curPower보다 큰 값으로 index 이동
			if (dp.get(lastIndex) + 1 >= nowCnt) {
				int idx = list.size() - 1;
				do {
					list.remove(idx);	// lastIndex 이후 요소 삭제
					dp.remove(idx);
					result++;
				} while (--idx > lastIndex);
				
				list.add(curPower);
				dp.add(dp.get(idx)+1);
			}
		}
		
		System.out.println(result);
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
**/