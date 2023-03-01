import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class Main {
	
	static class CandidatePoint implements Comparable<CandidatePoint>{
		int x;
		int y;
		int friendCount;
		int spaciousCount;
		
		public CandidatePoint(int x, int y, int friendsCount, int spaciousCount) {
			super();
			this.x = x;
			this.y = y;
			this.friendCount = friendsCount;
			this.spaciousCount = spaciousCount;
		}

		@Override
		public int compareTo(CandidatePoint o) {
			int cmp = Integer.compare(o.friendCount, this.friendCount);
			if (cmp != 0) return cmp;
			
			cmp = Integer.compare(o.spaciousCount, this.spaciousCount);
			if (cmp != 0) return cmp;
			
			cmp = Integer.compare(this.x, o.x);
			if (cmp != 0) return cmp;
			
			return Integer.compare(this.y, o.y);
		}
	}
	
	static int N, SIZE;
	static int[][] classes;	// 자리 정보
	static int[][] friends;	// 친구 정보
	static Map<Integer, int[]> seatMap;
	
	// 사방순회 ( 상 우 하 좌 )
	static int[] dx = { -1,  0, +1,  0};
	static int[] dy = {  0, +1,  0, -1};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		SIZE = N*N;
		classes = new int[N+1][N+1];
		friends = new int[SIZE+1][4];
		seatMap = new HashMap<>();
		
		int[] inputOrder = new int[SIZE];
		for (int i = 0; i < SIZE; i++) {
			String[] input = br.readLine().split(" ");
			int stdNo = Integer.parseInt(input[0]);
			inputOrder[i] = stdNo;
			for (int j = 0; j < 4; j++) {
				friends[stdNo][j] = Integer.parseInt(input[j+1]);
			}
		}
		
		int[][] friendsCount = new int[N+1][N+1]; 		// 친구 수 카운트할 배열
		for (int i = 0; i < SIZE; i++) {
			int stdNo = inputOrder[i];	// 현재 학생 번호
			
			// 친구 수 배열 초기화
			for (int j = 1; j <= N; j++) {
				for (int k = 1; k <= N; k++) {
					friendsCount[j][k] = 0;
				}
			}
			// 옆자리가 친구인 자리 카운트 증가
			for (int fNum : friends[stdNo]) {
				int[] p = null;
				if ((p = seatMap.get(fNum)) != null) {
					for (int j = 0; j < 4; j++) {
						int nx = p[0] + dx[j];
						int ny = p[1] + dy[j];
						
						if (isValidPoint(nx, ny)) friendsCount[nx][ny]++;
					}
					
				}
			}
			
			PriorityQueue<CandidatePoint> pq = new PriorityQueue<>(100);
			for (int j = 1; j <= N; j++) {
				for (int k = 1; k <= N; k++) {
					if (classes[j][k] != 0) continue;	// 비어있는 자리가 아니면 다음 자리 확인
					
					// 주위 공백 수 확인
					int spaciousCnt = 0;
					// 자리 주변 사방탐색
					for (int l = 0; l < 4; l++) {
						int nx = j + dx[l];
						int ny = k + dy[l];
						if (isValidPoint(nx, ny) && classes[nx][ny] == 0) spaciousCnt++;
					}
					
					CandidatePoint seat = new CandidatePoint(j, k, friendsCount[j][k], spaciousCnt);
					
					pq.offer(seat);
				}
			}
			
			// 정해진 위치를 맵에 저장
			CandidatePoint dest = pq.poll();
			classes[dest.x][dest.y] = stdNo;
			seatMap.put(stdNo, new int[] {dest.x, dest.y});
			
//			// debug
//			System.out.println("========printMap===========");
//			for (int i1 = 1; i1 <= N; i1++) {
//				for (int j = 1; j <= N; j++) {
//					System.out.printf("%d ", classes[i1][j]);
//				}
//				System.out.println();
//			}
		}
		
		
		// 만족도 계산
		int sumSatisfaction = 0;
		for (int i = 1; i <= SIZE; i++) {
			sumSatisfaction += getSatisfaction(i);
		}
		System.out.println(sumSatisfaction);
	}
	
	private static int getSatisfaction(int stdNo) {
		int fCnt = -1;	// 친구 수
		int[] p = seatMap.get(stdNo);

		// 자리 주변 좌표 넣기
		Set<String> aroundPoint = new HashSet<>();
		for (int i = 0; i < 4; i++) {
			int nx = p[0] + dx[i];
			int ny = p[1] + dy[i];
			aroundPoint.add(String.format("%d %d", nx, ny));
		}
		
		// 친구 좌표 확인
		for (int i = 0; i < 4; i++) {
			int[] frdP = seatMap.get(friends[stdNo][i]);
			if (aroundPoint.contains(String.format("%d %d", frdP[0], frdP[1]))) fCnt++;
		}
		
		return (int) Math.pow(10, fCnt);
	}

	private static boolean isValidPoint(int nx, int ny) {
		return nx >= 1 && nx <= N && ny >= 1 && ny <= N;
	}

}

/**
  * 백준 21608. 상어 초등학교
  * 
	입력
	N : 반 크기 = N^2 (3 <= N <= 20)
	for (N^2):
		학생의 번호와 친구 4명의 번호
	
	
**/