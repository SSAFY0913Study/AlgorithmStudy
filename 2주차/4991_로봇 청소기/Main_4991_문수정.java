import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static final char DUST = '*';
    public static final char CLEANER = 'o';
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static Point[] permu;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    //가로 크기 w와 세로 크기 h
    static int W, H;
    static List<Point> dusts;
    static Point cleaner;
    static char[][] room;
    static int[][] distance;
    static boolean[] isVisited;
    static int answer;

    //먼지 위치 리스트로 갖자
    //청소기와 먼지사이의 거리를 구한다
    //먼지들끼리 거리 구하고 순열로 가본다
    //bfs
    public static void main(String[] args) throws IOException {

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());
            room = new char[H][W];
            distance = new int[H][W];
            dusts = new ArrayList();
            answer = Integer.MAX_VALUE;

            if (W == 0 && H == 0) {
                break;
            }

            for (int i = 0; i < H; i++) {
                String str = br.readLine();
                for (int j = 0; j < W; j++) {
                    room[i][j] = str.charAt(j);

                    if (room[i][j] == CLEANER) {
                        cleaner = new Point(i, j);
                    }

                    if (room[i][j] == DUST) {
                        dusts.add(new Point(i, j));
                    }
                }
            }
            bfs(cleaner);
            boolean flag = false;
            for (Point dust : dusts) {
                if (distance[dust.x][dust.y] == 0) {
                    System.out.println(-1);
                    flag = true;
                    break;
                }
            }
            if (flag) {
                continue;
            }

            for (Point dust : dusts) {
                bfs(dust);
            }

            permu = new Point[dusts.size()];
            isVisited = new boolean[dusts.size()];

            permutation(0, cleaner.distance);
            System.out.println(answer);
        }

    }

    private static void permutation(int cnt, int[][] distance) {
        if (cnt == dusts.size()) {
            int sum = 0;
            for (Point dust : permu) {
                sum += distance[dust.x][dust.y] - 1;
                distance = dust.distance;
            }
            answer = Math.min(sum, answer);
            return;
        }

        for (int i = 0; i < dusts.size(); i++) {
            if (isVisited[i]) {
                continue;
            }
            isVisited[i] = true;
            permu[cnt] = dusts.get(i);
            permutation(cnt + 1, distance);
            isVisited[i] = false;
        }
    }

    private static void bfs(Point point) {

        Queue<Integer> Q = new LinkedList<>();

        distance = new int[H][W];
        distance[point.x][point.y] = 1;
        Q.add(point.x);
        Q.add(point.y);

        while (!Q.isEmpty()) {
            int cx = Q.poll();
            int cy = Q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if (nx < 0 || ny < 0 | nx >= H || ny >= W) {
                    continue;
                }

                if (distance[nx][ny] != 0 || room[nx][ny] == 'x') {
                    continue;
                }

                distance[nx][ny] = distance[cx][cy] + 1;
                Q.add(nx);
                Q.add(ny);
            }
        }

        point.distance = distance;
    }
}

class Point {
    int x;
    int y;
    int[][] distance;


    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}