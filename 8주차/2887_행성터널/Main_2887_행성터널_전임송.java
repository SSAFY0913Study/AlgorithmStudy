import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//2887 행성터널
/*
 * 3차원 mst
 *  x y z에 대해 정렬 후 각 정렬리스트에서 i와 i + 1 사이에 간선 추가
 *  한 좌표에 대한 비표값을 비용으로 사용하기 때문에 정렬 후 자신보다 작거나 큰 가장 가까운 행성이랑 연결되야 MST가 완성된다.
 *  kruskal
 *  
 */
public class Main {
	static class Edge implements Comparable<Edge> {
		int id;
		int to;
		long value;
		public Edge(int id, long price) {
			super();
			this.id = id;
			this.value = price;
		}
		public Edge(int id, int to, long value) {
			super();
			this.id = id;
			this.to = to;
			this.value = value;
		}

		@Override
		public int compareTo(Edge o) {
			return Long.compare(value, o.value);
		}
		@Override
		public String toString() {
			return "Edge [id=" + id + ", price=" + value + "]";
		}
	}
	static int N;
	static boolean[] visited;
	static List<Edge> planetsX;
	static List<Edge> planetsY;
	static List<Edge> planetsZ;
	static List<Edge> tunnels;
	static int[] parents;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		visited = new boolean[N];
		planetsX = new ArrayList<>(N);
		planetsY = new ArrayList<>(N);
		planetsZ = new ArrayList<>(N);
		String[] input;
		for (int i = 0; i < N; i++) {
			input = br.readLine().split(" ");
			int x = Integer.parseInt(input[0]);
			int y = Integer.parseInt(input[1]);
			int z = Integer.parseInt(input[2]);
			planetsX.add(new Edge(i, x));
			planetsY.add(new Edge(i, y));
			planetsZ.add(new Edge(i, z));
		}
		parents = new int[N];
		tunnels = new ArrayList<>(10);
		Collections.sort(planetsX);
		Collections.sort(planetsZ);
		Collections.sort(planetsY);
		for (int i = 0; i < N - 1; i++) { //간선 추가
			tunnels.add(new Edge(planetsX.get(i).id, planetsX.get(i + 1).id, 
					Math.abs(planetsX.get(i).value - planetsX.get(i + 1).value)));
			tunnels.add(new Edge(planetsY.get(i).id, planetsY.get(i + 1).id, 
					Math.abs(planetsY.get(i).value - planetsY.get(i + 1).value)));
			tunnels.add(new Edge(planetsZ.get(i).id, planetsZ.get(i + 1).id, 
					Math.abs(planetsZ.get(i).value - planetsZ.get(i + 1).value)));
		}
		Collections.sort(tunnels);
		makeSets();
		
		long totalCost = 0;
		int count = 0;
		for (int i = 0, size = tunnels.size(); i < size; i++) {
			Edge current = tunnels.get(i);
			if (union(current.id, current.to)) {
				totalCost += current.value;
				count++;
				if (count == N) {
					break;
				}
			}
		}
		System.out.println(totalCost);
	}
	
	public static void makeSets() {
		for (int i = 0; i < N; i++) {
			parents[i] = i;
		}
	}
	
	public static int findSet(int u) {
		if (parents[u] == u) {
			return u;
		}
		return parents[u] = findSet(parents[u]);
	}
	
	public static boolean union(int u, int v) {
		int parentU = findSet(u);
		int parentV = findSet(v);
		if (parentU == parentV) {
			return false;
		}
		parents[parentV] = parentU;
		return true;
	}
}
