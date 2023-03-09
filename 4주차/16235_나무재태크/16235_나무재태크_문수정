import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static Queue<Tree> trees = new LinkedList<>();
	static Queue<Tree> departedTrees = new LinkedList<>();
	static int[] dx = { -1, -1, -1, 0, 1, 1, 1, 0 };
	static int[] dy = { -1, 0, 1, 1, 1, 0, -1, -1 };

	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());

		// N, M, K
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		int[][] nutrient = new int[N][N];
		int[][] field = new int[N][N];
		int answer = 0;

		// N개의 줄에 A배열의 값
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				field[i][j] = 5;
				nutrient[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// M개의 줄에는 상도가 심은 나무의 정보를 나타내는 세 정수 x, y, z가 주어진다.
		// 처음 두 개의 정수는 나무의 위치 (x, y)를 의미하고, 마지막 정수는 그 나무의 나이
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int age = Integer.parseInt(st.nextToken());
			trees.add(new Tree(x, y, age));
		}

		for (int d = 0; d < K; d++) {
			if (trees.isEmpty()) {
				break;
			}

			// 봄에는 나무가 자신의 나이만큼 양분을 먹고, 나이가 1 증가한다.
			// 각각의 나무는 나무가 있는 1×1 크기의 칸에 있는 양분만 먹을 수 있다.
			// 하나의 칸에 여러 개의 나무가 있다면, 나이가 어린 나무부터 양분을 먹는다.
			// 만약, 땅에 양분이 부족해 자신의 나이만큼 양분을 먹을 수 없는 나무는 양분을 먹지 못하고 즉시 죽는다.
			Collections.sort((List<Tree>) trees);
			int size = trees.size();
			for (int i = 0; i < size; i++) {
				Tree tree = trees.poll();
				if (field[tree.x][tree.y] - tree.age >= 0) {
					field[tree.x][tree.y] -= tree.age;
					tree.age++;
					trees.add(tree);
				} else {
					departedTrees.add(tree);
				}
			}

			// 여름에는 봄에 죽은 나무가 양분으로 변하게 된다.
			// 각각의 죽은 나무마다 나이를 2로 나눈 값이 나무가 있던 칸에 양분으로 추가된다.
			// 소수점 아래는 버린다.
			while (!departedTrees.isEmpty()) {
				Tree tree = departedTrees.poll();
				field[tree.x][tree.y] += tree.age / 2;
			}

			// 가을에는 나무가 번식한다.
			// 번식하는 나무는 나이가 5의 배수이어야 하며,
			// 인접한 8개의 칸에 나이가 1인 나무가 생긴다.
			size = trees.size();
			for (int s = 0; s < size; s++) {
				Tree tree = trees.poll();
				trees.add(tree);
				if (tree.age % 5 != 0) {
					continue;
				}
				for (int i = 0; i < 8; i++) {
					int nx = tree.x + dx[i];
					int ny = tree.y + dy[i];

					if (nx < 0 || ny < 0 || nx >= N || ny >= N) {
						continue;
					}
					trees.add(new Tree(nx, ny, 1));
				}
			}

			// 겨울에는 S2D2가 땅을 돌아다니면서 땅에 양분을 추가한다.
			// 각 칸에 추가되는 양분의 양은 A[r][c]이고, 입력으로 주어진다.
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					field[i][j] += nutrient[i][j];
				}
			}
		}

		// K년이 지난 후 상도의 땅에 살아있는 나무의 개수
		System.out.println(trees.size());
	}

	static class Tree implements Comparable<Tree> {
		int x;
		int y;
		int age;

		public Tree(int x, int y, int age) {
			super();
			this.x = x;
			this.y = y;
			this.age = age;
		}

		@Override
		public int compareTo(Tree o) {
			return age - o.age;
		}

		@Override
		public String toString() {
			return "Tree [x=" + x + ", y=" + y + ", age=" + age + "]";
		}
	}
}
