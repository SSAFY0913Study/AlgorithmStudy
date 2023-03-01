import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static int N, M, I, W;
    static List<Integer>[] employees;
    static int[] scores;

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 회사의 직원 수 n명, 최초의 칭찬의 횟수 m
        // 직원 n명의 직속 상사의 번호

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        employees = new ArrayList[N + 1];
        scores = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            employees[i] = new ArrayList<>();
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            int superiorId = Integer.parseInt(st.nextToken());
            if (superiorId != -1) {
                employees[superiorId].add(i);
            }
        }

        // 직속 상사로부터 칭찬을 받은 직원 번호 i, 칭찬의 수치 w
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            I = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            scores[I] += W;
        }

        dfs(1);

        for(int i=1;i<=N;i++) {
            sb.append(scores[i]).append(" ");
        }
        System.out.println(sb);
    }

    private static void dfs(int superiorId) {
        List<Integer> list = employees[superiorId];
        for (Integer employeeId : list) {
            scores[employeeId] += scores[superiorId];
            dfs(employeeId);
        }
    }
}