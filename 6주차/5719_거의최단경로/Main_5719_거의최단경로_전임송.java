import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

//5719 거의 최단 경로
/*
 * 1. 다익스트라로 최단 길이를 가진 모든 경로를 찾으며 삭제한다
 * 		인접 리스트 사용
 * 		visited인 정점도 weight 가 같으면 부모 정점으로 저장, 더 작으면 리셋, 다시 큐에 넣을 필요는 없다
 * 2. 삭제한 그래프에서 다시 다익스트라로 두번째 최단경로를 찾는다
 */

public class Main_5719_거의최단경로_전임송 {	
	static class Pair implements Comparable<Pair>{
		int index;
		int weight;
		
		public Pair(int index, int weight) {
			super();
			this.index = index;
			this.weight = weight;
		}

		@Override
		public int compareTo(Pair o) {
			return Integer.compare(weight, o.weight);
		}
	}
	
	static List<Pair>[] graph;
	static List<Integer>[] parents;
	static boolean[][] deleted;
	static int N;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			String[] line = br.readLine().split(" ");
			N = Integer.parseInt(line[0]);
			int M = Integer.parseInt(line[1]);
			
			if (N == 0 && M == 0) { //테스트 케이스 끝
				break;
			}
			
			line = br.readLine().split(" ");
			int S = Integer.parseInt(line[0]);
			int D = Integer.parseInt(line[1]);
			graph = new List[N];
			
			for (int i = 0; i < N; i++) { //그래프 초기화
				graph[i] = new ArrayList<Pair>(10);
			}
			
			for (int i = 0; i < M; i++) {
				line = br.readLine().split(" ");
				int U = Integer.parseInt(line[0]);
				int V = Integer.parseInt(line[1]);
				int P = Integer.parseInt(line[2]);
				graph[U].add(new Pair(V, P));
			}
			
			deleted = new boolean[N][N];
			
			dijkstra(S, D); //첫번째 다익스트라
			deletePath(S, D); //최단 경로 제거
			System.out.println(dijkstra(S, D)); //두번째 다익스트라
		}
	}
	
	public static void deletePath(int start, int dest) { //다익스트라 이후 parents에 존제하는 모든 경로를 제거표시한다.
		Queue<Integer> queue = new ArrayDeque<>();
		boolean[] visited = new boolean[N];
		queue.add(dest); //from(parent)
		
		while (!queue.isEmpty()) {
			int current = queue.poll(); 

			if (current == start) {
				continue;
			}
			for (int parent : parents[current]) {
				deleted[parent][current] = true;
				//System.out.println(parent + ", " + from);
				if (visited[parent]) { //방문했던 정점이면 큐에 넣지 않는다
					continue;
				}
				queue.add(parent);
				visited[parent] = true;
			}
		}
	}
	
	public static int dijkstra(int start, int dest) {
		PriorityQueue<Pair> queue = new PriorityQueue<>();
		
		parents = new List[N];
		for (int i = 0; i < N; i++) { //초기화
			parents[i] = new ArrayList<Integer>(10);
		}
		int[] distance = new int[N];
		Arrays.fill(distance, Integer.MAX_VALUE);
		queue.add(new Pair(start, 0));
		distance[start] = 0;
		parents[start].add(-1);
		Pair current;
		Pair next;
		while (!queue.isEmpty()) {
			current = queue.poll();
			int curIndex = current.index;
			int curWeight = current.weight; //해당 정점 방문 전체 비용
			if (distance[curIndex] < curWeight) { //visited 역할
				continue;
			}
			int size = graph[curIndex].size();
			for (int i = 0; i < size; i++) {
				next = graph[curIndex].get(i);
				int nextIndex = next.index;
				int nextWeight = next.weight; //간선 비용
				if (deleted[curIndex][nextIndex]) { //삭제된 경로인경우
					continue;
				}
				if (distance[nextIndex] < nextWeight + curWeight) { //인접 정점의 최단거리가 더 작을 경우 
					continue;
				}
				if (distance[nextIndex] == nextWeight + curWeight) { //같은 경우
					parents[nextIndex].add(curIndex);
					continue;
				}
				
				distance[nextIndex] = nextWeight + curWeight; //작은 경우 
				queue.add(new Pair(nextIndex, distance[nextIndex]));
				parents[nextIndex].clear();
				parents[nextIndex].add(curIndex);
			}
		}
		//System.out.println(Arrays.toString(distance));
		if (distance[dest] == Integer.MAX_VALUE) {
			return -1;
		}
		return distance[dest];
	}
}
