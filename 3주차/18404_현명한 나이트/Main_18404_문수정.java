import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static List<Point> list = new ArrayList<>();
    static int[] dx = { -2, -2, -1, -1, 1, 1, 2, 2 };
    static int[] dy = { -1, 1, -2, 2, -2, 2, -1, 1 };
    static int[][] isVisited;
    static int N, M, X, Y;// 내위치

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        X = Integer.parseInt(st.nextToken());
        Y = Integer.parseInt(st.nextToken());

        for (int j = 0; j < M; j++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            list.add(new Point(x,y));
        }

        //8가지 위치 탐색해서 거기까지 갈 수 있는 최소 이동 수→ bfs
        //최소 이동수 담는 배열 만들어서 적위치 for문 돌면서 이동수 출력
        bfs();
        for (Point point : list) {
            sb.append(isVisited[point.x][point.y]-1).append(" ");
        }
        System.out.println(sb);
    }

    private static void bfs() {
        isVisited = new int[N+1][N+1];
        isVisited[X][Y] = 1;
        Queue<Integer> Q = new LinkedList<Integer>();
        Q.add(X);
        Q.add(Y);

        while (!Q.isEmpty()) {
            int x = Q.poll();
            int y = Q.poll();
            for (int i = 0; i < 8; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx < 1 || ny < 1 || nx > N || ny > N || isVisited[nx][ny]!=0) {
                    continue;
                }

                isVisited[nx][ny] = isVisited[x][y]+1;
                Q.add(nx);
                Q.add(ny);
            }
        }
    }
}

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}