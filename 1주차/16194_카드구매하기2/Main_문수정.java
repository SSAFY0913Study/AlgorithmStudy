import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
4카드 구매
카트팩1 카드3
카드팩2 카드2
카드팩3 카드1
카드팩4 카드0
 */
public class Main {

    static int[] money;
    static int[] sum;
    static int count;

    public static void main(String[] args) throws IOException {
        input();
        for (int i = 1; i <= count; i++) {
            for (int j = 1; j <= i; j++) {
                sum[i] = Math.min(sum[i], sum[i - j] + money[j]);
            }
        }
        System.out.println(sum[count]);
    }

    private static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        count = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        money = new int[count + 1];

        for (int i = 1; i <= count; i++) {
            money[i] = Integer.parseInt(st.nextToken());
        }

        sum = money.clone();
    }
}
