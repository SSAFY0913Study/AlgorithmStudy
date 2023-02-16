import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static int n;
    static int w;
    static int L;
    static Queue<Integer> bridge = new LinkedList<>();
    static Queue<Integer> truck = new LinkedList<>();
    static int sum;
    static int time;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        L = sc.nextInt();
        w = sc.nextInt();

        //다리길이만큼 0추가
        for (int i = 0; i < L; i++) {
            bridge.add(0);
        }

        for (int i = 0; i < n; i++) {
            truck.add(sc.nextInt());
        }
        
        while (!bridge.isEmpty()) {
            //빼기
            sum -= bridge.poll();
            time++;
          
            //더하기
            if(!truck.isEmpty()){
                //다리 무게 가능하면 트럭넣기
                if (sum + truck.peek() <= w) {
                    int weight = truck.poll();
                    sum += weight;
                    bridge.add(weight);
                } else {
                  //아니면 0
                    bridge.add(0);
                }
            }
        }
        
        System.out.println(time);
    }
}
