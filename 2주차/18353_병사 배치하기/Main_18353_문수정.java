import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 최대 부분 증가 수열 이용
public class Main {
    static int N;
    static int[] power;
    static int[] count; //앞에 올 수 있는 병사의 개수를 저장하는 배열

    public static void main(String[] args) throws IOException {
        //입력
        input();
        count = new int[N];
        int total = 0;

        //나보다 크면서 병사개수의 최대값을 갖고있는 배열 값에 + 1
        for (int i = 0; i < N; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                if (power[j] > power[i]) {
                    max = Math.max(max, count[j]);
                }
            }

            count[i] = max + 1;
            total = Math.max(total, count[i]); //전체 배열에 대한 최대값을 갱신하면 선택한 병사수가 나옴
        }

        //답: 전체 병사수 - 선택 병사
        System.out.println(N - total);
    }

    private static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        power = new int[N];
        for (int i = 0; i < N; i++) {
            power[i] = Integer.parseInt(st.nextToken());
        }
    }
}

