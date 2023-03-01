import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class 수고르기 {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        //N개의 정수로 이루어진 수열
        //그 차이가 M 이상이면서 제일 작은 경우
        //두 정수 N, M
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
        }

        //불가능하면 오른쪽올려
        //가능하면 비교하고 왼쪽 올려
        Arrays.sort(arr);
        int min = Integer.MAX_VALUE;
        int L = 0;
        for (int R = 1; R < N; R++) {
            while (L < R) {
                if (arr[R] - arr[L] >= M) {
                    min = Math.min(min, arr[R] - arr[L]);
                    L++;
                }else {
                    break;
                }
            }
        }
        System.out.println(min);
    }
}
