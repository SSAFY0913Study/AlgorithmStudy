import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        String pop = sc.next();
        Stack<Character> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();

        //스택에 하나씩 넣으면서 폭발 문자 같으면 다 팝시켜
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            stack.add(c);
            if (stack.size() >= pop.length()) {
                int idx = 0;
                for (int j = stack.size() - pop.length(); j < stack.size(); j++) {
                    if (stack.get(j) == pop.charAt(idx)) {
                        idx++;
                    } else {
                        break;
                    }
                    if (idx == pop.length()) {
                        for (int k = 0; k < pop.length(); k++) {
                            stack.pop();
                        }
                    }
                }
            }
        }

        if (stack.isEmpty()) {
            System.out.println("FRULA");
        } else {
            for(int i=0;i<stack.size();i++){
                sb.append(stack.get(i));
            }
            System.out.println(sb);
        }
    }
}
