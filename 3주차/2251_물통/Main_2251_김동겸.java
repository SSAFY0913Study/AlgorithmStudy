import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

class Main {
	
	static int[] bottle = new int[3];
	static boolean[][][] visited = new boolean[201][201][201];
	static Set<Integer> result = new HashSet<Integer>();
	static StringBuilder sb = new StringBuilder(10);
	
	static int[] idx = {0, 1, 2};
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		bottle[0] = sc.nextInt();
		bottle[1] = sc.nextInt();
		bottle[2] = sc.nextInt();
		
		bfs(new int[] {0, 0, bottle[2]});
		
		result.stream().sorted().forEach(s -> {
			sb.append(s).append(" ");
		});
		System.out.println(sb);
	}

	private static void bfs(int[] water) {
		Queue<int[]> queue = new ArrayDeque<>();
		queue.offer(new int[] {water[0], water[1], water[2], 2, 2});	// c에서 시작. (water0~2, from, to)
		visited[water[0]][water[1]][water[2]] = true;
		result.add(water[2]);
		while ( !queue.isEmpty() ) {
			for (int i = 0, size = queue.size(); i < size; i++) {
				int[] cur = queue.poll();
				water[0] = cur[0];
				water[1] = cur[1];
				water[2] = cur[2];
				int from = cur[3];
				int to = cur[4];
				int amount = Math.min(bottle[to]-water[to], water[from]);
				
				water[to] += amount;
				water[from] -= amount;
				
				for (int j = 0; j < 3; j++) {
					for (int k = 0; k < 3; k++) {
						from = j;
						to = k;
						if (j == k) continue;
						
						amount = Math.min(bottle[to]-water[to], water[from]);
						if (amount <= 0) continue;
						
						water[to] += amount;
						water[from] -= amount;
//						System.out.println(Arrays.toString(water) + " " + amount);
						if (!visited[water[0]][water[1]][water[2]]) {
							queue.offer(new int[] {water[0], water[1], water[2], from, to});
							visited[water[0]][water[1]][water[2]] = true;
							if (water[0] == 0) result.add(water[2]);
						}
						water[to] -= amount;
						water[from] += amount;
					}
				}
			}
		}
		
	}
}

/**
 *	2251. 물통 
 */