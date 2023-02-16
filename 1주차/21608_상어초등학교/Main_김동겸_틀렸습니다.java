import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

class Main {
	
	static int N, SIZE;
	static int[][] classes;
	static int[][] spaceCounts;
	static int[][] friends;
	static Map<Integer, int[]> studentPoint;
	
	// 사방순회 ( 상 우 하 좌 )
	static int[] dx = { -1,  0, +1,  0};
	static int[] dy = {  0, +1,  0, -1};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		SIZE = N*N;
		classes = new int[N+1][N+1];		 // 자리 정보
		spaceCounts = new int[N+2][N+2];	 // 주변에 빈자리 개수를 카운트하는 배열
		friends = new int[SIZE+1][4];		 // 친구 정보
		studentPoint = new HashMap<>();		 // 학생 위치 저장
		
		for (int i = 1; i <= N; i++) {
			Arrays.fill(spaceCounts[i], 4);
		}
		
		for (int i = 1; i <= N; i++) {
			spaceCounts[i][1]--;
			spaceCounts[1][i]--;
			spaceCounts[i][N]--;
			spaceCounts[N][i]--;
		}
		
		int[] inputOrder = new int[SIZE];
		for (int i = 0; i < SIZE; i++) {
			String[] input = br.readLine().split(" ");
			int stdNo = Integer.parseInt(input[0]);
			inputOrder[i] = stdNo;
			for (int j = 0; j < 4; j++) {
				friends[stdNo][j] = Integer.parseInt(input[j+1]);
			}
		}
		
		for (int i = 0; i < SIZE; i++) {
			int stdNo = inputOrder[i];
			
			// 1. 친구 위치를 바탕으로 가장 친구가 많은 위치를 받아옴
			int[] candidatePoints = getMostFriendsPoints(stdNo);
			
			// 2. 빈칸이 많은 위치를 받아옴 (행, 열 조건 만족을 위한 오름차순)
			int[] destPoints = getSpaciousPoint(candidatePoints);
			
			// 정해진 위치를 맵에 저장
			studentPoint.put(stdNo, destPoints);
			classes[destPoints[0]][destPoints[1]] = stdNo;
			spaceCounts[destPoints[0]][destPoints[1]] = 0;
			for (int j = 0; j < 4; j++) {
				spaceCounts[destPoints[0] + dx[j]][destPoints[1] + dy[j]]--;
			}
		}
		
//		// debug
//		System.out.println("========printMap===========");
//		for (int i = 1; i <= N; i++) {
//			for (int j = 1; j <= N; j++) {
//				System.out.printf("%d ", classes[i][j]);
//			}
//			System.out.println();
//		}
		
		// 만족도 계산
		int sumSatisfaction = 0;
		for (int i = 1; i <= SIZE; i++) {
			sumSatisfaction += getSatisfaction(i);
		}
		System.out.println(sumSatisfaction);
	}
	
	// 주변에 친구들이 많은 위치(들) 반환. 길이는 2의 배수(x1,y1,x2,y2, ...)
	private static int[] getMostFriendsPoints(int stdNo) {
		// 친구 정보
		int[] friendsNum = friends[stdNo];
		int max = 0;
		Map<String, Integer> pointMap = new HashMap<>();
		
		// 친구의 위치를 가져옴
		for (int i = 0; i < 4; i++) {
			int[] friendPoint = studentPoint.get(friendsNum[i]);
			if (friendPoint == null) continue;
			
			// 사방순회하며 해당 위치의 카운트를 증가
			for (int j = 0; j < 4; j++) {
				int nx = friendPoint[0] + dx[j];
				int ny = friendPoint[1] + dy[j];
				
				if (!isValidPoint(nx, ny) ||
						classes[nx][ny] != 0) continue;
				
				String pointString = String.format("%d %d", nx, ny);
				if (!pointMap.containsKey(pointString)) {
					pointMap.put(pointString, 0);
				}
				int count = pointMap.get(pointString)+1;
				pointMap.put(pointString, count);
				max = Math.max(max, count);
			}
		}
		int[] points = new int[40]; // 4명 * 사방 * 2(좌표정보) = 32
		int idx = 0;
		for (Entry<String, Integer> entry : pointMap.entrySet()) {
			String key = entry.getKey();
			int value = entry.getValue().intValue();
			if (value == max) {
				String[] point = key.split(" ");
				points[idx++] = Integer.parseInt(point[0]);
				points[idx++] = Integer.parseInt(point[1]);
			}
		}
		
		return Arrays.copyOf(points, idx);
	}

	private static boolean isValidPoint(int nx, int ny) {
		return nx >= 1 && nx <= N && ny >= 1 && ny <= N;
	}

	// 빈칸수 확인
	private static int[] getSpaciousPoint(int[] candidatePoints) {
		int size = candidatePoints.length;
		if (size/2 == 1) return candidatePoints;
		
		int[] spaceCount = new int[size/2];
		
		// 사방순회하며 빈칸수 확인
		int max = 0;
		for (int i = 0; i < size; i += 2) {
			int x = candidatePoints[i];
			int y = candidatePoints[i+1];
			
			for (int j = 0; j < 4; j++) {
				int nx = x + dx[j];
				int ny = y + dy[j];
				
				if (!isValidPoint(nx, ny)) continue;
				
				if (classes[nx][ny] == 0) {
					spaceCount[i/2]++;
					max = Math.max(max, spaceCount[i/2]);
				}
			}
		}
		
		int[] result = new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE};
		for (int i = 0; i < size; i += 2) {
			if (spaceCount[i/2] == max &&
					result[0] > candidatePoints[i] &&
					result[1] > candidatePoints[i+1]) {
				result[0] = candidatePoints[i];
				result[1] = candidatePoints[i+1];
			}
		}
		
		if (result[0] == Integer.MAX_VALUE) {
			int x = N, y = N, s = 0;
			for (int j = N; j > 0; j--) {
				for (int i = N; i > 0; i--) {
					if (spaceCounts[i][j] >= s &&
							x >= i && y >= j) {
						x = i;
						y = j;
						s = spaceCounts[i][j];
					}
				}
			}
			result[0] = x;
			result[1] = y;
		}
		
		return result;
	}

	// 해당 학생의 만족도를 반환
	private static int getSatisfaction(int no) {
		Set<String> friendsSet = new HashSet<String>();
		for (int i = 0; i < 4; i++) {
			int[] p = studentPoint.get(friends[no][i]);
			friendsSet.add(String.format("%d %d", p[0], p[1]));
		}
		
		int[] p = studentPoint.get(no);
		for (int i = 0; i < 4; i++) {
			int nx = p[0] + dx[i];
			int ny = p[1] + dy[i];
			friendsSet.remove(String.format("%d %d", nx, ny));
		}
		int count = 4-friendsSet.size();
		if (count == 0) return 0;
		if (count == 1) return 1;
		if (count == 2) return 10;
		if (count == 3) return 100;
		if (count == 4) return 1000;
		
		return 0;
		
	}
}

class CandidatePoint implements Comparable<CandidatePoint>{
	int x;
	int y;
	int friendCount;
	
	@Override
	public int compareTo(CandidatePoint o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private boolean isSamePoint(int x, int y) {
		return this.x == x && this.y == y;
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