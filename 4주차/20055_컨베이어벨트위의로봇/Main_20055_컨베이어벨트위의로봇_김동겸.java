import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

class Main {
	
	static class Conveyor {
		int durability;
		boolean isLoaded;
		public Conveyor(int durability) {
			this.durability = durability;
		}
		
		private void decrease() {
			if (durability == 0) return;
			
			if (durability == 1) {
				zeroCnt++;
			}
			
			durability--;
		}

		@Override
		public String toString() {
			return ""+durability+" "+ (isLoaded?"O":"X");
		}
		
	}
	
	static LinkedList<Conveyor> conveyorBelt = new LinkedList<>();
	
	static int N, K, L, zeroCnt;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		K = Integer.parseInt(input[1]);
		L = N<<1;
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < L; i++) {
			conveyorBelt.add(new Conveyor(Integer.parseInt(st.nextToken())));
		}
		
		int step = 0;
		while (zeroCnt < K) {
			step++;
//			System.out.println("step : " + step);
			rotate();
//			System.out.println("rotate");
//			System.out.println(conveyorBelt);
			robotMove();
//			System.out.println("robotMove");
//			System.out.println(conveyorBelt);
			load();
//			System.out.println("load");
//			System.out.println(conveyorBelt);
		}
		System.out.println(step);
	}

	private static void load() {
		Conveyor first = conveyorBelt.get(0);
		if (first.durability == 0) return;	// 첫 번째 컨베이어의 내구도가 0이면 로봇을 적재하지 않음
		
		first.isLoaded = true;
		first.decrease();
	}

	private static void rotate() {
		conveyorBelt.get(N-1).isLoaded = false;
		
		Conveyor last = conveyorBelt.remove(L-1);
		conveyorBelt.add(0, last);
	}
	
	private static void robotMove() {
		Conveyor next = conveyorBelt.get(N-1);
		next.isLoaded = false;
		for (int idx = N-2; idx > 0; idx--) {
			Conveyor current = conveyorBelt.get(idx);
			// 현재 칸에 로봇이 있고, 다음 칸에 로봇이 없고, 다음 칸의 내구도가 1 이상일 때
			if (current.isLoaded && !next.isLoaded && next.durability > 0) {
				next.isLoaded = true;
				current.isLoaded = false;
				next.decrease();
			}
			
			next = current;	// 현재 칸이 다음 칸이 됨
		}
		conveyorBelt.get(N-1).isLoaded = false;
	}
}

/**
 *	20055. 컨베이어 벨트 위의 로봇 
 */