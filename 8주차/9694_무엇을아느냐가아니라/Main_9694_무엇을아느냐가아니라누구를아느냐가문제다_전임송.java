import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/*
 * 9694 무엇을아느냐가아니라누구를아느냐가문제다
 * dfs
 * 
 */

public class Main_9694_무엇을아느냐가아니라누구를아느냐가문제다_전임송 {		
	static final int INF = 100;
	static final int START = 0;
	static class Edge implements Comparable<Edge> {
		int to;
		int weight;
		public Edge(int to, int weight) {
			super();
			this.to = to;
			this.weight = weight;
		}
		@Override
		public int compareTo(Edge o) {
			// TODO Auto-generated method stub
			return weight - o.weight;
		}
		
	}
	static int N;
	static int M;
	static int[][] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int testCase = Integer.parseInt(st.nextToken());
		for (int t = 1; t <= testCase; t++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			adj = new int[M][M];
			for (int r = 0; r < M; r++) {
				Arrays.fill(adj[r], INF);
			}
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				int weight = Integer.parseInt(st.nextToken());
				adj[from][to] = weight;
				adj[to][from] = weight;
			}
			sb.append("Case #").append(t).append(": ").append(dijkstra()).append('\n');
		}
		System.out.println(sb);
		
	}
	
	public static String dijkstra() {
		boolean[] visited = new boolean[M];
		int[] distance = new int[M];
		Arrays.fill(distance, INF);
		int[] parents = new int[M];
		PriorityQueue<Edge> queue = new PriorityQueue<>();
		queue.add(new Edge(0, 0));
		distance[0] = 0;
		while (!queue.isEmpty()) {
			Edge current = queue.poll();
			visited[current.to] = true;
			for (int i = 0; i < M; i++) {
				if (adj[current.to][i] == INF) {
					continue;
				}
				if (visited[i]) {
					continue;
				}
				if (distance[i] > current.weight + adj[current.to][i]) {
					distance[i] = current.weight + adj[current.to][i];
					parents[i] = current.to;
					queue.add(new Edge(i, distance[i]));
				}
			}
		}
		if (distance[M - 1] == INF) {
			return "-1";
		}
		int current = M - 1;
		Stack<Integer> stack = new Stack<>();
		while (true) {
			stack.add(current);
			if (current == START) {
				break;
			}
			current = parents[current];
		}
		StringJoiner sj = new StringJoiner(" ");
		while (!stack.isEmpty()) {
			sj.add(Integer.toString(stack.pop()));
		}
		return sj.toString();
	}
}
