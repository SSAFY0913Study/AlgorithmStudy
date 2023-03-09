import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[] dx = {-1, 0, 1, 0};//시계방향
    static int[] dy = {0, 1, 0, -1};
    static int N, M, K;
    static int[][] arr;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        // 세로 크기 N, 가로 크기 M (2 ≤ N, M ≤ 20), 그리고 이동하는 횟수 K
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        arr = new int[N][M];
        int answer = 0;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        Dice dice = new Dice();
        for (int i = 0; i < K; i++) {
            //주사위가 이동 방향으로 한 칸 굴러간다.
            dice.roll();

            //주사위가 도착한 칸 (x, y)에 대한 점수를 획득한다.
            answer += getScore(dice.x, dice.y);

            //주사위의 아랫면에 있는 정수 A와 주사위가 있는 칸 (x, y)에 있는 정수 B를 비교해 이동 방향을 결정한다.
            dice.changeDirection(arr[dice.x][dice.y]);
        }
        System.out.println(answer);
    }

    //(x, y)에 있는 정수를 B라고 했을때,
    // (x, y)에서 동서남북 방향으로 연속해서 이동할 수 있는 칸의 수 C를 모두 구한다.
    // 이때 이동할 수 있는 칸에는 모두 정수 B가 있어야 한다. 여기서 점수는 B와 C를 곱한 값이다.
    //각 이동에서 획득하는 점수의 합을 구해보자.
    private static int getScore(int x, int y) {
        Queue<Integer> q = new LinkedList<>();
        boolean[][] isVisited = new boolean[N][M];
        isVisited[x][y] = true;
        q.add(x);
        q.add(y);
        int cnt = 0;
        while (!q.isEmpty()) {
            cnt++;
            int size = q.size() / 2;
            int cx = q.poll();
            int cy = q.poll();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < 4; j++) {
                    int nx = cx + dx[j];
                    int ny = cy + dy[j];

                    if (nx < 0 || ny < 0 || nx >= N || ny >= M || isVisited[nx][ny]) {
                        continue;
                    }
                    if (isVisited[nx][ny] || arr[nx][ny] != arr[x][y]) {
                        continue;
                    }

                    isVisited[nx][ny] = true;
                    q.add(nx);
                    q.add(ny);
                }
            }
        }
//        System.out.println(cnt);
        return arr[x][y] * cnt;
    }

    static class Dice {
        static final int NORTH = 0;
        static final int EAST = 1;
        static final int SOUTH = 2;
        static final int WEST = 3;

        int x;
        int y;
        int up;
        int bottom;
        int east;
        int west;
        int south;
        int north;
        int direction;

        public Dice() {
            this.x = 0;
            this.y = 0;
            this.up = 1;
            this.bottom = 6;
            this.east = 3;
            this.west = 4;
            this.south = 5;
            this.north = 2;
            this.direction = EAST;
        }

        public void roll() {
            move();
            changeState();
        }

        private void move() {
            //만약, 이동 방향에 칸이 없다면, 이동 방향을 반대로 한 다음 한 칸 굴러간다.
            int nx = x + dx[direction];
            int ny = y + dy[direction];
            if (nx < 0 || ny < 0 || nx >= N || ny >= M) {
                direction = (direction + 2) % 4;
            }
            x += dx[direction];
            y += dy[direction];
        }

        private void changeState() {
            if (direction == NORTH) {
                int tmp = up;
                this.up = south;
                this.south = bottom;
                this.bottom = north;
                this.north = tmp;
                return;
            }

            if (direction == EAST) {
                int tmp = up;
                this.up = west;
                this.west = bottom;
                this.bottom = east;
                this.east = tmp;
                return;
            }

            if (direction == SOUTH) {
                int tmp = up;
                this.up = north;
                this.north = bottom;
                this.bottom = south;
                this.south = tmp;
                return;
            }

            if (direction == WEST) {
                int tmp = up;
                this.up = east;
                this.east = bottom;
                this.bottom = west;
                this.west = tmp;
            }
        }

        public void changeDirection(int score) {
            //주사위의 아랫면에 있는 정수 A와 주사위가 있는 칸 (x, y)에 있는 정수 B
            //A > B인 경우 이동 방향을 90도 시계 방향으로 회전시킨다.
            if (bottom > score) {
                direction = (direction + 1) % 4;
                return;
            }

            //A < B인 경우 이동 방향을 90도 반시계 방향으로 회전시킨다.
            if (bottom < score) {
                direction = (direction + 3) % 4;
            }

            //A = B인 경우 이동 방향에 변화는 없다.
        }

        @Override
        public String toString() {
            return "Dice{" +
                    "x=" + x +
                    ", y=" + y +
                    ", up=" + up +
                    ", bottom=" + bottom +
                    ", east=" + east +
                    ", west=" + west +
                    ", south=" + south +
                    ", north=" + north +
                    ", direction=" + direction +
                    '}';
        }
    }
}
