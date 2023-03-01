import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//이분탐색 LIS 알고리즘 사용; 오름차순 대신 내림차순
public class Main_18353_병사배치하기 {	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int n = Integer.parseInt(br.readLine());
		int number;
		int[] orderedNumbers = new int[n];
		int count = 0;
		String[] input = br.readLine().split(" ");
		orderedNumbers[count++] = Integer.parseInt(input[0]); //LIS 알고리즘을 위한 dp배열 첫 값 초기화
		for (int i = 1; i < n; i++)  {
			number = Integer.parseInt(input[i]);
			if (orderedNumbers[count - 1] > number) { 
				orderedNumbers[count] = number;
				count++;
				continue;
			}
			int index = lowerBound(orderedNumbers, number);
			orderedNumbers[index] = number;
		}
		System.out.println(n - count);
	}	
	
	//내림차순으로 정렬된 array에서 숫자가 value 보다 작은 제일 앞 위치를 리턴한다 {5,3,3,3,1} 4 = 1
	public static int lowerBound(int[] array, int value) { 
		int start = 0;
		int end = array.length;
		
		while (start < end) {
			int mid = start + (end - start) / 2;
			if (value < array[mid]) {
				start = mid + 1;
				continue;
			}
				end = mid;
		}
		return start;
	}
	//병사 배치하기 18353
}