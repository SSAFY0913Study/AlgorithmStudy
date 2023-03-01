import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static List<Point> possiblePoints = new ArrayList<>();
    static boolean[][] bridge;
    static Point[] combination;
    static boolean isPossible;
    static int count = -1;
    static int N;
    static int M;
    static int H;

    // 세로선의 개수 N, 가로선의 개수 M, 세로선마다 가로선을 놓을 수 있는 위치의 개수 H
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); //열
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken()); //행

        //(1,1)부터 시작
        bridge = new boolean[H + 1][N + 1];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            bridge[x][y] = true;
        }

        //애초에 입력받을때 다리놓을 수 있는 부분만 따로 저장 (맨 마지막도 true안됨)
        for (int i = 1; i <= H; i++) {
            for (int j = 1; j < N; j++) { //맨끝은 다리 있으면 안됨
                if (bridge[i][j]) continue; //이미 다리 있는 경우
                if (bridge[i][j - 1]) continue; //왼쪽 true면 새로운 다리 못둠
                possiblePoints.add(new Point(i, j));
            }
        }

        //조합0~3까지 구하고 가능할 때 리턴 불가능 -1
        //판단 로직 false나오면 계속 진행 true면 바로 정답
        //왼쪽으로 갈 수 있는지도 체크
        for (int i = 0; i <= 3; i++) {
            combination = new Point[i];
            combination(i, 0, 0);
            if (isPossible) {
                count = i;
                break;
            }
        }

        System.out.println(count);

    }

    private static void combination(int bonus, int cnt, int start) {
        if (cnt == bonus) {
            boolean[][] newBridge = new boolean[H + 1][N + 1];
            for (int i = 1; i <= H; i++) {
                for (int j = 1; j <= N; j++) {
                    newBridge[i][j] = bridge[i][j];
                }
            }

            for (Point point : combination) {
                if (newBridge[point.x][point.y - 1]) {
                    return;
                }
                newBridge[point.x][point.y] = true;
            }

            if (isPossible(newBridge)) {
                isPossible = true;
            }
            return;
        }

        for (int i = start; i < possiblePoints.size(); i++) {
            combination[cnt] = possiblePoints.get(i);
            combination(bonus, cnt + 1, i + 1);
            if (isPossible) {
                return;
            }
        }
    }

    private static boolean isPossible(boolean[][] newBridge) {
        for (int i = 1; i <= N; i++) {
            int x = 1;
            int y = i;
            while (x <= H) {
                //오른쪽
                if (newBridge[x][y]) {
                    y += 1;
                    x += 1; //아래는 무조건
                    continue;
                }
                //왼쪽
                if (newBridge[x][y - 1]) {
                    y -= 1;
                    x += 1; //아래는 무조건
                    continue;
                }
                //둘다 아니면 아래
                x += 1;
            }
            if (i != y) {
                return false;
            }
        }
        return true;
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