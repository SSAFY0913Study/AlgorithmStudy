import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

//부피가 A, B, C(1≤A, B, C≤200) 리터
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static boolean[][][] isVisited = new boolean[201][201][201];
    static Set<Integer> results = new TreeSet<>();
    static int A, B, C;

    public static void main(String[] args) throws IOException {
        //처음에는 앞의 두 물통은 비어 있고, 세 번째 물통은 가득(C 리터) 차 있다
        //a,b,c는 용량
        StringTokenizer st = new StringTokenizer(br.readLine());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());


        //완전 탐색
        //abc의 상태를 갖고 dfs
        dfs(new Water(0, 0, C));

        //오름차순으로 정렬
        for (int result : results) {
            sb.append(result).append(" ");
        }
        System.out.println(sb);
    }

    //첫 번째 물통(용량이 A인)이 비어 있을 때, 세 번째 물통(용량이 C인)에 담겨있을 수 있는 물의 양
    private static void dfs(Water water) {
        isVisited[water.a][water.b][water.c]=true;
        if (water.a == 0) {
            results.add(water.c);
        }

        //a->b
        int a = water.a;
        int b = water.b;
        int c = water.c;
        if (b + a > B) {
            a -= (B - b);
            b = B;
        } else {
            b += a;
            a = 0;
        }
        if(!isVisited[a][b][c]) dfs(new Water(a, b, c));

        //a->c
        a = water.a;
        b = water.b;
        c = water.c;
        if (c + a > C) {
            a -= (C - c);
            c = C;
        } else {
            c += a;
            a = 0;
        }
        if(!isVisited[a][b][c]) dfs(new Water(a, b, c));

        //b->a
        a = water.a;
        b = water.b;
        c = water.c;
        if (a + b > A) {
            b -= (A - a);
            a = A;
        } else {
            a += b;
            b = 0;
        }
        if(!isVisited[a][b][c]) dfs(new Water(a, b, c));

        //b->c
        a = water.a;
        b = water.b;
        c = water.c;
        if (c + b > C) {
            b -= (C - c);
            c = C;
        } else {
            c += b;
            b = 0;
        }
        if(!isVisited[a][b][c]) dfs(new Water(a, b, c));

        //c->a
        a = water.a;
        b = water.b;
        c = water.c;
        if (a + c > A) {
            c -= (A - a);
            a = A;
        } else {
            a += c;
            c = 0;
        }
        if(!isVisited[a][b][c]) dfs(new Water(a, b, c));
        //c->b
        a = water.a;
        b = water.b;
        c = water.c;
        if (b + c > B) {
            c -= (B - b);
            b = B;
        } else {
            b += c;
            c = 0;
        }
        if(!isVisited[a][b][c]) dfs(new Water(a, b, c));
    }
}

class Water {
    int a;
    int b;
    int c;

    public Water(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}
