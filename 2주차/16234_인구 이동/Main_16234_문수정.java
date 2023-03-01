import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static int[] dx = { 0, 0, -1, 1 };
    static int[] dy = { -1, 1, 0, 0 };

    static int[][] map;
    static boolean[][] isVisited;
    static boolean isDivided;
    static int count;
    static int N;
    static int L;
    static int R;

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        while (true) {
            //다시 돌때 isDivided isVisited 초기화
            isDivided = false;
            isVisited = new boolean[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (isVisited[i][j]) {
                        continue;
                    }
                    dfs(i, j);
                }
            }
            if (!isDivided) {
                break;
            }
            count++;
        }

        System.out.println(count);
    }

    private static void dfs(int x, int y) {
        // 큐에 들어있는게 없으면 break;
        // 가능한거(방문안하고, 차이가능) 방문체크
        // 위치 큐에 넣고 가능한거 리스트에 저장
        // sum/인구수 세팅
        // 언제 멈춤? 바꿀것이 없을 때

        Queue<Point> Q = new LinkedList<>();
        List<Point> countries = new ArrayList<Point>();
        Point point = new Point(x, y);
        countries.add(point);
        Q.add(point);
        isVisited[x][y] = true;
        int sum = map[x][y];
        int cnt = 1;

        while (!Q.isEmpty()) {
            Point poll = Q.poll();
            // 4방향으로 갈 수 있는지 체크(차이 계산), 방문 안한곳
            for (int k = 0; k < 4; k++) {
                int nx = poll.x + dx[k];
                int ny = poll.y + dy[k];

                if (0 > nx || 0 > ny || N <= nx || N <= ny || isVisited[nx][ny]) {
                    continue;
                }

                int interval = Math.abs(map[nx][ny] - map[poll.x][poll.y]);
                if (L <= interval && interval <= R) {
                    // 열리면 이동
                    isDivided = true;
                    sum += map[nx][ny];
                    cnt++;
                    isVisited[nx][ny] = true;
                    Point nPoint = new Point(nx, ny);
                    countries.add(nPoint);
                    Q.add(nPoint);
                }
            }
        }

        int dividedNumber = sum / cnt;
        for (Point country : countries) {
            map[country.x][country.y] = dividedNumber;
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