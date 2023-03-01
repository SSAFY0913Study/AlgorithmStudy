import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    final static char DOT = '.';
    final static char BOMB = 'O';

    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};
    static int[][] isBomb;
    static int R;
    static int C;
    static int N;

    public static void main(String[] args) throws IOException {
        //입력
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        isBomb = new int[R][C];

        for (int i = 0; i < R; i++) {
            String line = br.readLine();
            for (int j = 0; j < C; j++) {
                //초기 세팅 'O' isBomb 1
                if (line.charAt(j) == BOMB) {
                    isBomb[i][j] = 1;
                }
            }
        }

        //입력에서 1초 써서 N-1
        for (int i = 0; i < N - 1; i++) {
            //짝수면 증가
            if (i % 2 == 0) {
                changeState();
                continue;
            }

            //홀수면 폭발
            bomb();
        }

        // 0이면 '.'이고 그외는 'O'임
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (isBomb[i][j] == 0) {
                    sb.append(DOT);
                    continue;
                }
                sb.append(BOMB);
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }

    private static void changeState() {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                //0,1 대기 상태
                if (isBomb[i][j] == 0 || isBomb[i][j] == 1) {
                    isBomb[i][j]++;
                }
            }
        }
    }

    private static void bomb() {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                //2초 지나면 isBomb==2인 애들 주변 isBomb 0
                //**근데 옆에 2이면 0으로 바꾸면 안됨 다음꺼 안터짐**
                if (isBomb[i][j] == 2) {
                    isBomb[i][j] = 0;
                    for (int k = 0; k < 4; k++) {
                        int nx = i + dx[k];
                        int ny = j + dy[k];
                        if (0 <= nx && nx < R && 0 <= ny && ny < C) {
                            if (isBomb[nx][ny] == 2) continue;
                            isBomb[nx][ny] = 0;
                        }
                    }
                }
            }
        }
    }
}
