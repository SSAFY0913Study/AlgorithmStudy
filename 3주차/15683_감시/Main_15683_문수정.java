import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class 감시 {
    static final int UP = 0;
    static final int DOWN = 1;
    static final int LEFT = 2;
    static final int RIGHT = 3;

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[] dx = {-1, 1, 0, 0}; //위,아래,왼,오
    static int[] dy = {0, 0, -1, 1};
    static int[] directions;
    static int[][] map;
    static List<CCTV> cctvList = new ArrayList<>();
    static boolean[][] isVisited;
    static int N, M;
    static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        // 사무실의 세로 크기 N과 가로 크기 M
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];

        //N개의 줄에는 사무실 각 칸의 정보. 0은 빈 칸, 6은 벽, 1~5는 CCTV
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int info = Integer.parseInt(st.nextToken());
                if (info != 6 && info != 0) {
                    cctvList.add(new CCTV(i, j, info));
                }
                map[i][j] = info;
            }
        }

        //1-4가지, 2-2가지, 3-4가지, 4-4가지, 5-1가지-> 4가지 숫자로 중복 순열 만들기
        directions = new int[cctvList.size()];
        permu(0);
        System.out.println(answer);
    }

    private static void permu(int cnt) {
        if (cnt == cctvList.size()) {
            //최소값 구하기
            answer = Math.min(answer, countInVisible());
            return;
        }

        for (int i = 0; i < 4; i++) {
            directions[cnt] = i;
            permu(cnt + 1);
        }
    }

    //command 실행하기
    private static int countInVisible() {
        isVisited = new boolean[N][M];
        for (int i = 0; i < cctvList.size(); i++) {
            scan(cctvList.get(i), directions[i]);
        }
        return count();
    }

    //bfs로 감시
    private static void scan(CCTV cctv, int direction) {
        if (cctv.option == 1) {
            bfs(cctv, direction);
        }

        if (cctv.option == 2) {
            if (direction == UP || direction == DOWN) {
                bfs(cctv, UP);
                bfs(cctv, DOWN);
            }
            if (direction == RIGHT || direction == LEFT) {
                bfs(cctv, RIGHT);
                bfs(cctv, LEFT);
            }
        }

        if (cctv.option == 3) {
            if (direction == UP) {
                bfs(cctv, UP);
                bfs(cctv, RIGHT);
            }
            if (direction == RIGHT) {
                bfs(cctv, RIGHT);
                bfs(cctv, DOWN);
            }
            if (direction == DOWN) {
                bfs(cctv, DOWN);
                bfs(cctv, LEFT);
            }

            if (direction == LEFT) {
                bfs(cctv, LEFT);
                bfs(cctv, UP);
            }
        }

        if (cctv.option == 4) {
            if (direction == UP) {
                bfs(cctv, UP);
                bfs(cctv, LEFT);
                bfs(cctv, RIGHT);
            }
            if (direction == RIGHT) {
                bfs(cctv, RIGHT);
                bfs(cctv, UP);
                bfs(cctv, DOWN);
            }
            if (direction == DOWN) {
                bfs(cctv, DOWN);
                bfs(cctv, LEFT);
                bfs(cctv, RIGHT);
            }

            if (direction == LEFT) {
                bfs(cctv, LEFT);
                bfs(cctv, UP);
                bfs(cctv, DOWN);
            }
        }

        if (cctv.option == 5) {
            bfs(cctv, UP);
            bfs(cctv, DOWN);
            bfs(cctv, LEFT);
            bfs(cctv, RIGHT);
        }
    }

    //갈 수 있는곳 까지 감시 true (범위안, 6이아닐 경우)
    private static void bfs(CCTV cctv, int direction) {
        Queue<Integer> q = new LinkedList<>();
        q.add(cctv.x);
        q.add(cctv.y);
        isVisited[cctv.x][cctv.y] = true;

        while (!q.isEmpty()) {
            int nx = q.poll() + dx[direction];
            int ny = q.poll() + dy[direction];

            //벽만 아니면 끝까지 감
            if (nx < 0 || ny < 0 || nx >= N || ny >= M || map[nx][ny] == 6) {
                continue;
            }
            isVisited[nx][ny] = true;
            q.add(nx);
            q.add(ny);
        }
    }

    //사각지대 구하기: 0이면서 visited false인곳 카운트
    private static int count() {
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (isVisited[i][j] == false && map[i][j] == 0) {
                    cnt++;
                }
            }
        }
        return cnt;
    }
}

class CCTV {
    int x;
    int y;
    int option;

    public CCTV(int x, int y, int option) {
        this.x = x;
        this.y = y;
        this.option = option;
    }

    @Override
    public String toString() {
        return "CCTV{" +
                "x=" + x +
                ", y=" + y +
                ", option=" + option +
                '}';
    }
}
