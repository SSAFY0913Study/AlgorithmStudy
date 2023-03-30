import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//2263 트리의 순회
/*
 * 인오더와 포스트오더가 주어진다
 * - 인오더: L root R, ; 포스트오더 : L R root
 * - 프리오더: root L R
 *       1		in : 4, 2, 5,  1, 6, 3, 7  	루트 기준으로 양쪽이 subtree
 *     2   3	post : 4, 5, 2, 6, 7, 3, 1 	마지막이 루트, 루트 다음은 무조건 R루트, in에서 루트의 오른쪽전체를 빼면 그 다음 L루트
 *    4 5 6 7	pre : 1 2 4 5 3 6 7
 *    
 *    post에서 루트 찾고 post 루트기준 양쪽 재귀, 재귀 순서는 루트, 왼쪽, 오른쪽
 *    in: 2 1 3
 *    post: 2 3 1
 *    pre : 1 2 3
 *    
 *    in: 4 2 1 3
 *    post : 4 2 3 1
 *    1 2 4 3
 */

public class Main_2263_트리의순회_전임송 {	
	static int N;
	static int[] inOrder;
	static int[] inOrderPlace;
	static int[] postOrder;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		inOrder = new int[N];
		inOrderPlace = new int[N + 1]; //해당 숫자의 인덱스 값 저장
		postOrder = new int[N];
		String[] line;
		line = br.readLine().split(" ");
		for (int i = 0; i < N; i++) {
			int number = Integer.parseInt(line[i]);
			inOrder[i] = number;
			inOrderPlace[number] = i;
		}
		line = br.readLine().split(" ");
		for (int i = 0; i < N; i++) {
			int number = Integer.parseInt(line[i]);
			postOrder[i] = number;
		}
		getPreOrder(0, N - 1, N - 1);
		System.out.println(sb.toString());
	}
	
	public static void getPreOrder(int inStartIndex, int inEndIndex, int postEndIndex) {
		int root = postOrder[postEndIndex];
		int inRootIndex = inOrderPlace[root]; //루트 기준 양쪽 트리에 노드가 몇개씩 있는지 알수있다
		int lCount = inRootIndex - inStartIndex; //왼쪽 트리 노드 개수
		int rCount = inEndIndex - inRootIndex; //오른쪽 트리 노드 개수
		
		sb.append(root).append(" ");
		if (lCount == 1) {
			sb.append(Integer.toString(inOrder[inRootIndex - 1]))
			.append(" "); //root
		}
		if (lCount > 1) {
			getPreOrder(inStartIndex, inStartIndex + lCount - 1, postEndIndex - (rCount + 1)); // L tree
		}
		
		if (rCount == 1) {
			sb.append(Integer.toString(inOrder[inRootIndex + 1]))
			.append(" ");
		}
		if (rCount > 1) {
			getPreOrder(inRootIndex + 1, inRootIndex + rCount, postEndIndex - 1);
		}
	}
}
/*
 * 	in: 2 1 3
 *  post: 2 3 1
 *  pre : 1 2 3
 *  5
 *  1 2 3 4 5 
 *  5 4 3 2 1
 *  4
 *  4 3 2 1 
 *  4 3 2 1
 */