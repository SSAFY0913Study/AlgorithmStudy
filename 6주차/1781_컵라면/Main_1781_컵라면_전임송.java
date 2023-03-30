import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

//1781 컵라면
/*
 * 현재 시간 기준으로 데드라인이 지난 문제는 풀지 못한다.
 * 데드라인이 넘지 않은 문제중에 가장 보상이 큰 문제를 푼다
 * 노는 시간이 없어야한다
 * 
 * 제일 데드라인이 늦은 것 부터 시작한다
 * 해당 데드라인에서 픽할수있는 모든 문제 pq에 삽입, 이미 픽한건 제외
 * 3
 * 1 3
 * 3 10
 * 3 5
 */

public class Main_1781_컵라면_전임송 {	
	static class Problem {
		int number;
		int deadline;
		int count;
		public Problem(int number, int deadline, int count) {
			super();
			this.number = number;
			this.deadline = deadline;
			this.count = count;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Problem [number=").append(number).append(", deadline=").append(deadline).append(", count=")
					.append(count).append("]");
			return builder.toString();
		}
	}
	static final int MAX_N = 200_001;
	static int N;
	static Problem[] problems;
	static int index;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		String[] line;
		int[] deadlineCount = new int[MAX_N]; 
		problems = new Problem[N + 1];
		int maxDeadline = 0;
		for (int i = 1; i <= N; i++) {
			line = br.readLine().split(" ");
			int deadline = Integer.parseInt(line[0]);
			maxDeadline = Math.max(maxDeadline, deadline);
			deadlineCount[deadline]++;
			int count = Integer.parseInt(line[1]);
			problems[i] = new Problem(i, deadline, count);
		}
		Arrays.sort(problems, 1, N + 1, (p1,p2) -> p2.deadline - p1.deadline); //데드라인 순으로 내림차순
		index = 1;
		int currentTime = maxDeadline;
		int total = 0;
		PriorityQueue<Problem> queue = new PriorityQueue<>((p1,p2) -> p2.count - p1.count);
		Problem currentProblem;
		while (currentTime > 0) {
			fillQueue(queue, currentTime);
			if (queue.isEmpty()) {
				currentTime--;
				continue;
			}
			currentProblem = queue.poll();
			total += currentProblem.count;
			currentTime--;
			
		} //특정 시간 이하 데드라인중 가장 큰 것 고르기
		System.out.println(total);
	}
	public static void fillQueue(PriorityQueue<Problem> queue, int deadline) {
		while (index <= N) {
			if (problems[index].deadline < deadline) {
				return;
			}
			queue.add(problems[index]);
			index++;
		}
	}
}
